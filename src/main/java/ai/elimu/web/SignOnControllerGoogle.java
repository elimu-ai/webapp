package ai.elimu.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.contributor.SignOnEvent;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Provider;
import ai.elimu.model.enums.Role;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.CookieHelper;
import ai.elimu.util.Mailer;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Google2Api;
import org.scribe.exceptions.OAuthException;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * See https://console.developers.google.com/apis
 */
@Controller
public class SignOnControllerGoogle {
	
    private OAuthService oAuthService;

    private Token requestToken;

    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private SignOnEventDao signOnEventDao;

    @RequestMapping("/sign-on/google")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
		
        String apiKey = "108974530651-3g9r40r5s7it6p9vjh2e2eplgmm1to0d.apps.googleusercontent.com";
        String apiSecret = "mGlUmzxL2eP69HdVmPKtVLR7";
        String baseUrl = "http://localhost:8080/webapp";
        if (EnvironmentContextLoaderListener.env == Environment.TEST) {
            apiKey = "108974530651-fskde869tac7imherk2k516shfuvij76.apps.googleusercontent.com";
            apiSecret = ConfigHelper.getProperty("google.api.secret");
            baseUrl = "http://" + request.getServerName();
        } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            apiKey = "108974530651-k68pccps2jb88fllofpcf8ht356v08e4.apps.googleusercontent.com";
            apiSecret = ConfigHelper.getProperty("google.api.secret");
            baseUrl = "http://" + request.getServerName();
        }

        oAuthService = new ServiceBuilder()
                .provider(Google2Api.class) // See https://gist.github.com/2465453
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(baseUrl + "/sign-on/google/callback")
                .scope("email https://www.googleapis.com/auth/plus.login") // https://developers.google.com/+/web/api/rest/oauth#login-scopes
                .build();

        logger.info("Fetching the Authorization URL...");
        String authorizationUrl = oAuthService.getAuthorizationUrl(requestToken);
        logger.info("Got the Authorization URL!");

        return "redirect:" + authorizationUrl;
    }

    @RequestMapping(value="/sign-on/google/callback", method=RequestMethod.GET)
    public String handleCallback(HttpServletRequest request, Model model) {
        logger.info("handleCallback");

        if (request.getParameter("error") != null) {
            return "redirect:/sign-on?error=" + request.getParameter("error");
        } else {
            String verifierParam = request.getParameter("code");
            logger.debug("verifierParam: " + verifierParam);
            Verifier verifier = new Verifier(verifierParam);
            String responseBody = null;
            try {
                Token accessToken = oAuthService.getAccessToken(requestToken, verifier);
                logger.debug("accessToken: " + accessToken);

                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "https://www.googleapis.com/oauth2/v1/userinfo");
                oAuthService.signRequest(accessToken, oAuthRequest);
                Response response = oAuthRequest.send();
                responseBody = response.getBody();
                logger.info("response.getCode(): " + response.getCode());
                logger.info("response.getBody(): " + responseBody);
            } catch (OAuthException e) {
                logger.error(null, e);
                return "redirect:/sign-on?login_error=" + e.getMessage();
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
                    // TODO: validate e-mail
                    contributor.setEmail(jsonObject.getString("email"));
                }
                if (jsonObject.has("id")) {
                    contributor.setProviderIdGoogle(jsonObject.getString("id"));
                }
                if (jsonObject.has("picture")) {
                    contributor.setImageUrl(jsonObject.getString("picture"));
                }
                if (jsonObject.has("given_name")) {
                    contributor.setFirstName(jsonObject.getString("given_name"));
                }
                if (jsonObject.has("family_name")) {
                    contributor.setLastName(jsonObject.getString("family_name"));
                }
            } catch (JSONException e) {
                logger.error(null, e);
            }

            Contributor existingContributor = contributorDao.read(contributor.getEmail());
            if (existingContributor == null) {
                // Store new Contributor in database
                contributor.setRegistrationTime(Calendar.getInstance());
                if (contributor.getEmail().endsWith("@elimu.ai")) {
                    contributor.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR)));
                } else {
                    contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
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
                if (StringUtils.isNotBlank(contributor.getProviderIdGoogle())) {
                    existingContributor.setProviderIdGoogle(contributor.getProviderIdGoogle());
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
            signOnEvent.setProvider(Provider.GOOGLE);
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
