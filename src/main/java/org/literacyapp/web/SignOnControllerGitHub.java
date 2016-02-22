package org.literacyapp.web;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.scribejava.apis.GitHubApi;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Token;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.model.Verifier;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.util.Random;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.util.ConfigHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * See https://github.com/organizations/literacyapp-org/settings/applications
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

    /**
     * https://developer.github.com/v3/oauth/#1-redirect-users-to-request-github-access
     */
    @RequestMapping("/sign-on/github")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
        
        String apiKey = "75ab65504795daf525f5";
        String apiSecret = "4f6eba014e102f0ed48334de77dffc12c4d1f1d6";
        String baseUrl = "http://localhost:8080/literacyapp-webapp";
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
            String verifierParam = request.getParameter("code");
            logger.debug("verifierParam: " + verifierParam);
            Verifier verifier = new Verifier(verifierParam);
            
            Token accessToken = oAuth20Service.getAccessToken(verifier);
            logger.debug("accessToken: " + accessToken);
            
            OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL, oAuth20Service);
            oAuth20Service.signRequest(accessToken, oAuthRequest);
            Response response = oAuthRequest.send();
            String responseBody = response.getBody();
            logger.info("response.getCode(): " + response.getCode());
            logger.info("response.getBody(): " + responseBody);
            
            Contributor contributor = new Contributor();
            try {
                JSONObject jsonObject = new JSONObject(responseBody);
                logger.info("jsonObject: " + jsonObject);

                if (jsonObject.has("email")) {
                    if (!jsonObject.isNull("email")) {
                        // TODO: validate e-mail
                        contributor.setEmail(jsonObject.getString("email"));
                    }
                }
                if (jsonObject.has("id")) {
                    contributor.setProviderIdGitHub(jsonObject.getLong("id"));
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
            if (existingContributor != null) {
                // Contributor registered previously
                contributor = existingContributor;
            } else {
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
