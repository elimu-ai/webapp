package org.literacyapp.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Role;
import org.literacyapp.util.ConfigHelper;
import org.literacyapp.util.Mailer;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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

    @RequestMapping("/sign-on/google")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
		
        String apiKey = "771733326473-ntau0rkb03ca30n8vueps8hdpp38rr1b.apps.googleusercontent.com";
        String apiSecret = "gkwT-kW54bD59t4X0DQEDleC";
        String baseUrl = "http://localhost:8080/literacyapp-webapp";
        if (EnvironmentContextLoaderListener.env == Environment.TEST) {
            apiKey = "771733326473-rptbjt86j8t68upo2po2itgrlu8r9mn4.apps.googleusercontent.com";
            apiSecret = ConfigHelper.getProperty("google.api.secret");
            baseUrl = "http://" + request.getServerName();
        } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            apiKey = "771733326473-fpcd8jd0561ekbdi7ut6ej2df2ud2tua.apps.googleusercontent.com";
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
                contributor.setRole(Role.CONTRIBUTOR);
                if (contributor.getEmail().endsWith("@literacyapp.org")) {
//                    contributor.setRoles(new HashSet<>(Arrays.asList(Role.ADMIN, Role.ANALYST, Role.CONTRIBUTOR)));
                } else {
                    contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
                }
                contributor.setRegistrationTime(Calendar.getInstance());
                contributorDao.create(contributor);
                
                // Send welcome e-mail
                String to = contributor.getEmail();
                String from = "LiteracyApp <info@literacyapp.org>";
                String subject = "Welcome to the community";
                String title = "Welcome!";
                String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
                String htmlText = "<p>Hi, " + firstName + "</p>";
                htmlText += "<p>Thank you very much for registering as a contributor to the LiteracyApp community. We are glad to see you join us!</p>";
                htmlText += "<p>With your help, this is what we aim to achieve:</p>";
                htmlText += "<p><blockquote>\"The mission of the LiteracyApp project is to build software that will enable children without access to school to learn how to read and write <i>on their own</i>.\"</blockquote></p>";
                htmlText += "<p><img src=\"http://literacyapp.org/img/banner-en.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
                htmlText += "<h2>Chat</h2>";
                htmlText += "<p>Within the next hour, we will send you an invite to join our Slack channel (to " + contributor.getEmail() + "). There you can chat with the other community members.</p>";
                htmlText += "<h2>Feedback</h2>";
                htmlText += "<p>If you have any questions or suggestions, please contact us by replying to this e-mail or messaging us in Slack.</p>";
                Mailer.sendHtml(to, null, from, subject, title, htmlText);
                
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
                    String text = URLEncoder.encode("A new contributor " + name + " just joined the community: ") + "http://literacyapp.org/content/community/contributors";
                    String iconUrl = contributor.getImageUrl();
                    SlackApiHelper.postMessage(null, text, iconUrl);
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
                contributorDao.update(existingContributor);
                
                // Contributor registered previously
                contributor = existingContributor;
            }

            // Authenticate
            CustomAuthenticationManager.authenticateUser(contributor.getRole());

            // Add Contributor object to session
            request.getSession().setAttribute("contributor", contributor);

            return "redirect:/content";
        }
    }
}
