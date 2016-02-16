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
import org.scribe.builder.api.FacebookApi;
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
 * See https://developers.facebook.com/apps
 */
@Controller
public class SignOnControllerFacebook {
	
    private OAuthService oAuthService;

    private Token requestToken;

    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping("/sign-on/facebook")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
		
        String apiKey = "1130160227016886";
        String apiSecret = "e6b47e0310ac33edd404e434fabed0c4";
        String baseUrl = "http://" + request.getServerName();
        if (EnvironmentContextLoaderListener.env == Environment.TEST) {
            apiKey = "1130170237015885";
            apiSecret = "b62a3d943dff072a64b9473197cd78dd";
        } else if (EnvironmentContextLoaderListener.env == Environment.DEV) {
            apiKey = "1130171497015759";
            apiSecret = "d8b49268dacd1e29eca82de8edd88c1c";
            baseUrl = "http://localhost:8080/literacyapp-webapp";
        }

        oAuthService = new ServiceBuilder()
                .provider(FacebookApi.class)
                .apiKey(apiKey)
                .apiSecret(apiSecret)
                .callback(baseUrl + "/sign-on/facebook/callback")
                .scope("email,user_about_me") // https://developers.facebook.com/docs/facebook-login/permissions
                .build();

        logger.info("Fetching the Authorization URL...");
        String authorizationUrl = oAuthService.getAuthorizationUrl(requestToken);
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
            String verifierParam = request.getParameter("code");
            logger.debug("verifierParam: " + verifierParam);
            Verifier verifier = new Verifier(verifierParam);
            String responseBody = null;
            try {
                Token accessToken = oAuthService.getAccessToken(requestToken, verifier);
                logger.debug("accessToken: " + accessToken);

                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, "https://graph.facebook.com/me?fields=id,email,name,first_name,last_name,gender,link,picture,birthday,age_range,bio,education,work,timezone,hometown,location,friends");
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
                
                contributor.setRole(Role.CONTRIBUTOR);
                contributor.setRegistrationTime(Calendar.getInstance());
                contributorDao.create(contributor);
                
                // TODO: send welcome e-mail
            } else {
                // Contributor registered previously
                
                contributor = existingContributor;
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
