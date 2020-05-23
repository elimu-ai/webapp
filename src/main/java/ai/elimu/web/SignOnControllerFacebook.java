package ai.elimu.web;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Role;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.Mailer;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * See https://developers.facebook.com/apps
 */
@Controller
public class SignOnControllerFacebook {
	
    private OAuth20Service oAuth20Service;

    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping("/sign-on/facebook")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
		
        String apiKey = "1130171497015759";
        String apiSecret = "d8b49268dacd1e29eca82de8edd88c1c";
        String baseUrl = "http://localhost:8080/webapp";
        if (EnvironmentContextLoaderListener.env == Environment.TEST) {
            apiKey = "1130170237015885";
            apiSecret = ConfigHelper.getProperty("facebook.api.secret");
            baseUrl = "http://" + request.getServerName();
        } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            apiKey = "1130160227016886";
            apiSecret = ConfigHelper.getProperty("facebook.api.secret");
            baseUrl = "http://" + request.getServerName();
        }

        oAuth20Service = new ServiceBuilder()
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(baseUrl + "/sign-on/facebook/callback")
                .scope("email,user_about_me") // https://developers.facebook.com/docs/facebook-login/permissions
                .build(FacebookApi.instance());

        logger.info("Fetching the Authorization URL...");
        String authorizationUrl = oAuth20Service.getAuthorizationUrl();
        logger.info("Got the Authorization URL!");

        return "redirect:" + authorizationUrl;
    }

    @RequestMapping(value="/sign-on/facebook/callback", method=RequestMethod.GET)
    public String handleCallback(HttpServletRequest request, Model model) {
        logger.info("handleCallback");

        if (request.getParameter("param_denied") != null) {
            return "redirect:/sign-on?error=" + request.getParameter("param_denied");
        } else if (request.getParameter("error") != null) {
            return "redirect:/sign-on?error=" + request.getParameter("error");
        } else {
            String code = request.getParameter("code");
            logger.debug("code: " + code);
            
            String responseBody = null;
            try {
                OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
                logger.debug("accessToken: " + accessToken);

                String fields = "?fields=id,email,name,first_name,last_name,gender,link,picture,birthday,age_range,education,work,timezone,hometown,location,friends";
                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "https://graph.facebook.com/v2.8/me" + fields);
                oAuth20Service.signRequest(accessToken, oAuthRequest);
                Response response = oAuth20Service.execute(oAuthRequest);
                responseBody = response.getBody();
                logger.info("response.getCode(): " + response.getCode());
                logger.info("response.getBody(): " + responseBody);
            } catch (InterruptedException | ExecutionException | IOException e) {
                logger.error(null, e);
                return "redirect:/sign-on?login_error=" + e.getMessage();
            }

            Contributor contributor = new Contributor();
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                logger.info("jsonObject: " + jsonObject);

                if (jsonObject.has("email")) {
                    // TODO: validate e-mail
                    contributor.setEmail(jsonObject.getString("email"));
                }
                if (jsonObject.has("id")) {
                    contributor.setProviderIdFacebook(jsonObject.getString("id"));
                }
                if (jsonObject.has("picture")) {
                    JSONObject picture = jsonObject.getJSONObject("picture");
                    JSONObject pictureData = picture.getJSONObject("data");
                    contributor.setImageUrl(pictureData.getString("url"));
                }
                if (jsonObject.has("first_name")) {
                    contributor.setFirstName(jsonObject.getString("first_name"));
                }
                if (jsonObject.has("last_name")) {
                    contributor.setLastName(jsonObject.getString("last_name"));
                }
            } catch (JSONException e) {
                logger.error(null, e);
            }

            Contributor existingContributor = contributorDao.read(contributor.getEmail());
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
                String subject = "Welcome to the elimu.ai Community";
                String title = "Welcome!";
                String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
                String htmlText = "<p>Hi, " + firstName + "</p>";
                htmlText += "<p>Thank you very much for registering as a contributor to the elimu.ai Community. We are glad to see you join us!</p>";
                htmlText += "<h2>Purpose</h2>";
                htmlText += "<p>The purpose of elimu.ai is to provide <i>every child</i> with access to quality basic education.</p>";
                htmlText += "<h2>Why?</h2>";
                htmlText += "<p>The word \"elimu\" is Swahili for \"education\". We believe that a quality basic education is the right of every child no matter her social or geographical background.</p>";
                htmlText += "<h2>How?</h2>";
                htmlText += "<p>With your help, this is what we aim to achieve:</p>";
                htmlText += "<p><blockquote>\"The elimu.ai Community develops open source software for teaching children the basics of reading, writing and arithmetic.\"</blockquote></p>";
                htmlText += "<p><img src=\"https://gallery.mailchimp.com/1a69583fdeec7d1888db043c0/images/72b31d67-58fd-443e-a6be-3ef2095cfe3b.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
                htmlText += "<h2>Chat</h2>";
                htmlText += "<p>In Slack you can chat with the other elimu.ai Community members:</p>";
                Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Open chat", "https://join.slack.com/t/elimu-ai/shared_invite/zt-eoc921ow-0cfjATlIF2X~zHhSgSyaAw");
            } else {
                // Contributor already exists in database
                
                // Update existing contributor with latest values fetched from provider
                if (StringUtils.isNotBlank(contributor.getProviderIdFacebook())) {
                    existingContributor.setProviderIdFacebook(contributor.getProviderIdFacebook());
                }
                if (StringUtils.isNotBlank(contributor.getImageUrl())) {
                    existingContributor.setImageUrl(contributor.getImageUrl());
                }
                // TODO: firstName/lastName
                contributorDao.update(existingContributor);
                
                // Contributor registered previously
                contributor = existingContributor;
            }

            // Authenticate
            new CustomAuthenticationManager().authenticateUser(contributor);

            // Add Contributor object to session
            request.getSession().setAttribute("contributor", contributor);

            return "redirect:/content";
        }
    }
}
