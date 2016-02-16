package org.literacyapp.web;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Role;
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
		
        String apiKey = "771733326473-fpcd8jd0561ekbdi7ut6ej2df2ud2tua.apps.googleusercontent.com";
        String apiSecret = "_gYZBt0L-WC7_bQhRyKaz2EO";
        String baseUrl = "http://localhost:8080/literacyapp-webapp";
        if (EnvironmentContextLoaderListener.env != Environment.DEV) {
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
                contributor.setRegistrationTime(Calendar.getInstance());
                contributorDao.create(contributor);
                
                // TODO: send welcome e-mail
            }

            // Authenticate
            CustomAuthenticationManager.authenticateUser(contributor.getRole());

            // Add Contributor object to session
            request.getSession().setAttribute("contributor", contributor);
            
            // TODO: handle case where contributor details are missing

            return "redirect:/content";
        }
    }
}
