package ai.elimu.web;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.contributor.SignOnEvent;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Provider;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.CookieHelper;
import ai.elimu.util.Mailer;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * See https://github.com/organizations/elimu-ai/settings/applications
 * See https://developer.github.com/v3/oauth/#web-application-flow
 */
@Controller
public class SignOnControllerGitHub {
    
    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

    private OAuth20Service oAuth20Service;
    
    private String secretState;
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private SignOnEventDao signOnEventDao;

    /**
     * https://developer.github.com/v3/oauth/#1-redirect-users-to-request-github-access
     */
    @RequestMapping("/sign-on/github")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
        
        String apiKey = "75ab65504795daf525f5";
        String apiSecret = "4f6eba014e102f0ed48334de77dffc12c4d1f1d6";
        String baseUrl = "http://localhost:8080/webapp";
        if (EnvironmentContextLoaderListener.env == Environment.TEST) {
            apiKey = "57aad0f85f09ef18d8e6";
            apiSecret = ConfigHelper.getProperty("github.api.secret");
            baseUrl = "http://" + request.getServerName();
        } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            apiKey = "7018e4e57438eb0191a7";
            apiSecret = ConfigHelper.getProperty("github.api.secret");
            baseUrl = "http://" + request.getServerName();
        }
        
        secretState = "secret_" + new Random().nextInt(999_999);

        oAuth20Service = new ServiceBuilder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .state(secretState)
                .callback(baseUrl + "/sign-on/github/callback")
                .scope("user:email") // https://developer.github.com/v3/oauth/#scopes
                .build(GitHubApi.instance());

        logger.info("Fetching the Authorization URL...");
        String authorizationUrl = oAuth20Service.getAuthorizationUrl();
        logger.info("Got the Authorization URL!");
        logger.info("authorizationUrl: " + authorizationUrl);
		
        return "redirect:" + authorizationUrl;
    }

    /**
     * See https://developer.github.com/v3/oauth/#2-github-redirects-back-to-your-site
     */
    @RequestMapping(value="/sign-on/github/callback", method=RequestMethod.GET)
    public String handleCallback(HttpServletRequest request, Model model) {
        logger.info("handleCallback");
        
        String state = request.getParameter("state");
        logger.debug("state: " + state);
        if (!secretState.equals(state)) {
            return "redirect:/sign-on?error=state_mismatch";
        } else {
            String code = request.getParameter("code");
            logger.debug("verifierParam: " + code);
            
            String responseBody = null;
            try {
                OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
                logger.debug("accessToken: " + accessToken);

                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
                oAuth20Service.signRequest(accessToken, oAuthRequest);
                Response response = oAuth20Service.execute(oAuthRequest);
                responseBody = response.getBody();
                logger.info("response.getCode(): " + response.getCode());
                logger.info("response.getBody(): " + responseBody);
            } catch (IOException | InterruptedException | ExecutionException ex) {
                logger.error(null, ex);
                return "redirect:/sign-on?login_error=" + ex.getMessage();
            }
            
            Contributor contributor = new Contributor();
            contributor.setReferrer(CookieHelper.getReferrer(request));
            contributor.setUtmSource(CookieHelper.getUtmSource(request));
            contributor.setUtmMedium(CookieHelper.getUtmMedium(request));
            contributor.setUtmCampaign(CookieHelper.getUtmCampaign(request));
            contributor.setUtmTerm(CookieHelper.getUtmTerm(request));
            contributor.setReferralId(CookieHelper.getReferralId(request));
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                logger.info("jsonObject: " + jsonObject);

                if (jsonObject.has("email")) {
                    if (!jsonObject.isNull("email")) {
                        // TODO: validate e-mail
                        contributor.setEmail(jsonObject.getString("email"));
                    }
                }
                if (jsonObject.has("login")) {
                    contributor.setUsernameGitHub(jsonObject.getString("login"));
                }
                if (jsonObject.has("id")) {
                    Long idAsLong = jsonObject.getLong("id");
                    String id = String.valueOf(idAsLong);
                    contributor.setProviderIdGitHub(id);
                }
                if (jsonObject.has("avatar_url")) {
                    contributor.setImageUrl(jsonObject.getString("avatar_url"));
                }
                if (jsonObject.has("name")) {
                    if (!jsonObject.isNull("name")) {
                        String name = jsonObject.getString("name");
                        String[] nameParts = name.split(" ");
                        String firstName = nameParts[0];
                        logger.info("firstName: " + firstName);
                        contributor.setFirstName(firstName);
                        if (nameParts.length > 1) {
                            String lastName = nameParts[nameParts.length - 1];
                            logger.info("lastName: " + lastName);
                            contributor.setLastName(lastName);
                        }
                    }
                }
            } catch (JSONException e) {
                logger.error(null, e);
            }

            Contributor existingContributor = contributorDao.read(contributor.getEmail());
            if (existingContributor == null) {
                // Look for existing Contributor with matching GitHub id
                existingContributor = contributorDao.readByProviderIdGitHub(contributor.getProviderIdGitHub());
            }
            if (existingContributor == null) {
                // Store new Contributor in database
                contributor.setRegistrationTime(Calendar.getInstance());
                if (StringUtils.isNotBlank(contributor.getEmail()) && contributor.getEmail().endsWith("@elimu.ai")) {
                    contributor.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR)));
                } else {
                    contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
                }
                if (contributor.getEmail() == null) {
                    request.getSession().setAttribute("contributor", contributor);
                    new CustomAuthenticationManager().authenticateUser(contributor);
                    return "redirect:/content/contributor/add-email";
                }
                contributorDao.create(contributor);
                
                // Send welcome e-mail
                String to = contributor.getEmail();
                String from = "elimu.ai <info@elimu.ai>";
                String subject = "Welcome to the community";
                String title = "Welcome!";
                String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
                String htmlText = "<p>Hi, " + firstName + "</p>";
                htmlText += "<p>Thank you very much for registering as a contributor to the elimu.ai community. We are glad to see you join us!</p>";
                htmlText += "<h2>Purpose</h2>";
                htmlText += "<p>The purpose of elimu.ai is to provide <i>every child</i> with access to quality basic education.</p>";
                htmlText += "<h2>Why?</h2>";
                htmlText += "<p>The word \"elimu\" is Swahili for \"education\". We believe that a free quality education is the right of every child no matter her social or geographical background.</p>";
                htmlText += "<h2>How?</h2>";
                htmlText += "<p>With your help, this is what we aim to achieve:</p>";
                htmlText += "<p><blockquote>\"We build tablet-based software that teaches a child to read, write and calculate fully autonomously, without guidance from qualified teachers.\"</blockquote></p>";
                htmlText += "<p><img src=\"http://elimu.ai/static/img/banner-en.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
                htmlText += "<h2>Chat</h2>";
                htmlText += "<p>At http://slack.elimu.ai you can chat with the other community members.</p>";
                Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Open chat", "http://slack.elimu.ai");
                
                if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                     // Post notification in Slack
                     String name = "";
                     if (StringUtils.isNotBlank(contributor.getFirstName())) {
                         name += "(";
                         name += contributor.getFirstName();
                         if (StringUtils.isNotBlank(contributor.getLastName())) {
                             name += " " + contributor.getLastName();
                         }
                         name += ")";
                     }
                     String text = URLEncoder.encode("A new contributor " + name + " just joined the community: ") + "http://elimu.ai/content/community/contributors";
                     String iconUrl = contributor.getImageUrl();
                     SlackApiHelper.postMessage(null, text, iconUrl, null);
                 }
            } else {
                // Contributor already exists in database
                
                // Update existing contributor with latest values fetched from provider
                if (StringUtils.isNotBlank(contributor.getUsernameGitHub())) {
                    existingContributor.setUsernameGitHub(contributor.getUsernameGitHub());
                }
                if (StringUtils.isNotBlank(contributor.getProviderIdGitHub())) {
                    existingContributor.setProviderIdGitHub(contributor.getProviderIdGitHub());
                }
                if (StringUtils.isNotBlank(contributor.getImageUrl())) {
                    existingContributor.setImageUrl(contributor.getImageUrl());
                }
                // TODO: firstName/lastName
                if (StringUtils.isBlank(existingContributor.getReferrer())) {
                    existingContributor.setReferrer(contributor.getReferrer());
                }
                if (StringUtils.isBlank(existingContributor.getUtmSource())) {
                    existingContributor.setUtmSource(contributor.getUtmSource());
                }
                if (StringUtils.isBlank(existingContributor.getUtmMedium())) {
                    existingContributor.setUtmMedium(contributor.getUtmMedium());
                }
                if (StringUtils.isBlank(existingContributor.getUtmCampaign())) {
                    existingContributor.setUtmCampaign(contributor.getUtmCampaign());
                }
                if (StringUtils.isBlank(existingContributor.getUtmTerm())) {
                    existingContributor.setUtmTerm(contributor.getUtmTerm());
                }
                if (existingContributor.getReferralId() == null) {
                    existingContributor.setReferralId(contributor.getReferralId());
                }
                contributorDao.update(existingContributor);
                
                // Contributor registered previously
                contributor = existingContributor;
            }

            // Authenticate
            new CustomAuthenticationManager().authenticateUser(contributor);

            // Add Contributor object to session
            request.getSession().setAttribute("contributor", contributor);
            
            SignOnEvent signOnEvent = new SignOnEvent();
            signOnEvent.setContributor(contributor);
            signOnEvent.setCalendar(Calendar.getInstance());
            signOnEvent.setServerName(request.getServerName());
            signOnEvent.setProvider(Provider.GITHUB);
            signOnEvent.setRemoteAddress(request.getRemoteAddr());
            signOnEvent.setUserAgent(StringUtils.abbreviate(request.getHeader("User-Agent"), 1000));
            signOnEvent.setReferrer(CookieHelper.getReferrer(request));
            signOnEvent.setUtmSource(CookieHelper.getUtmSource(request));
            signOnEvent.setUtmMedium(CookieHelper.getUtmMedium(request));
            signOnEvent.setUtmCampaign(CookieHelper.getUtmCampaign(request));
            signOnEvent.setUtmTerm(CookieHelper.getUtmTerm(request));
            signOnEvent.setReferralId(CookieHelper.getReferralId(request));
            signOnEventDao.create(signOnEvent);
            
            return "redirect:/content";
        }
    }
}
