package ai.elimu.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.enums.Role;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth20Service;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * See https://console.developers.google.com/apis
 * <p />
 * 
 * Note that authentication for the same Google accounts are done in the Crowdsource 
 * app. See {@link ContributorRestController}.
 */
@Controller
public class SignOnControllerGoogle {
	
    private static final String PROTECTED_RESOURCE_URL = "https://www.googleapis.com/oauth2/v3/userinfo";
    
    private OAuth20Service oAuth20Service;
    
    private final String secretState = "secret_" + new Random().nextInt(999_999);

    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping("/sign-on/google")
    public String handleAuthorization(HttpServletRequest request) throws IOException {
        logger.info("handleAuthorization");
		
        String clientId = "108974530651-3g9r40r5s7it6p9vjh2e2eplgmm1to0d.apps.googleusercontent.com";
        String clientSecret = "mGlUmzxL2eP69HdVmPKtVLR7";
        String baseUrl = "http://localhost:8080/webapp";
        if (EnvironmentContextLoaderListener.env == Environment.TEST) {
            clientId = "108974530651-fskde869tac7imherk2k516shfuvij76.apps.googleusercontent.com";
            clientSecret = ConfigHelper.getProperty("google.api.secret");
            baseUrl = "https://" + request.getServerName();
        } else if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            clientId = "108974530651-k68pccps2jb88fllofpcf8ht356v08e4.apps.googleusercontent.com";
            clientSecret = ConfigHelper.getProperty("google.api.secret");
            baseUrl = "https://" + request.getServerName();
        }
        
        oAuth20Service = new ServiceBuilder(clientId)
                .apiSecret(clientSecret)
                .defaultScope("email profile") // See "OAuth Consent Screen" at https://console.developers.google.com/apis/
                .callback(baseUrl + "/sign-on/google/callback")
                .build(GoogleApi20.instance());

        logger.info("Fetching the Authorization URL...");
        String authorizationUrl = oAuth20Service.createAuthorizationUrlBuilder()
                .state(secretState)
                .build();
        logger.info("Got the Authorization URL!");

        return "redirect:" + authorizationUrl;
    }

    @RequestMapping(value="/sign-on/google/callback", method=RequestMethod.GET)
    public String handleCallback(HttpServletRequest request, Model model) {
        logger.info("handleCallback");
        
        String state = request.getParameter("state");
        logger.debug("state: " + state);
        if (!secretState.equals(state)) {
            return "redirect:/sign-on?error=state_mismatch";
        } else {
            String code = request.getParameter("code");
            logger.debug("code: " + code);
            
            String responseBody = null;
            
            logger.info("Trading the Authorization Code for an Access Token...");
            try {
                OAuth2AccessToken accessToken = oAuth20Service.getAccessToken(code);
                logger.debug("accessToken: " + accessToken);
                logger.info("Got the Access Token!");
                
                // Access the protected resource
                OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
                oAuth20Service.signRequest(accessToken, oAuthRequest);
                Response response = oAuth20Service.execute(oAuthRequest);
                responseBody = response.getBody();
                logger.info("response.getCode(): " + response.getCode());
                logger.info("response.getBody(): " +response.getBody());
            } catch (IOException | InterruptedException | ExecutionException ex) {
                logger.error(ex);
                return "redirect:/sign-on?error=" + ex.getMessage();
            }
            
            JSONObject jsonObject = new JSONObject(responseBody);
            logger.info("jsonObject: " + jsonObject);
            
            Contributor contributor = new Contributor();
            contributor.setEmail(jsonObject.getString("email"));
            contributor.setProviderIdGoogle(jsonObject.getString("sub"));
            contributor.setImageUrl(jsonObject.getString("picture"));
            if (jsonObject.has("given_name")) {
                contributor.setFirstName(jsonObject.getString("given_name"));
            }
            if (jsonObject.has("family_name")) {
                contributor.setLastName(jsonObject.getString("family_name"));
            }
            
            // Look for existing Contributor with matching e-mail address
            Contributor existingContributor = contributorDao.read(contributor.getEmail());
            logger.info("existingContributor: " + existingContributor);
            if (existingContributor == null) {
                // Store new Contributor in database
                contributor.setRegistrationTime(Calendar.getInstance());
                contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
                contributorDao.create(contributor);
                logger.info("Contributor " + contributor.getEmail() + " was created at " + request.getServerName());
            } else {
                // Contributor already exists in database
                // Update existing contributor with latest values fetched from provider
                if (StringUtils.isNotBlank(contributor.getProviderIdGoogle())) {
                    existingContributor.setProviderIdGoogle(contributor.getProviderIdGoogle());
                }
                if (StringUtils.isNotBlank(contributor.getImageUrl())) {
                    existingContributor.setImageUrl(contributor.getImageUrl());
                }
                if (StringUtils.isNotBlank(contributor.getFirstName())) {
                    existingContributor.setFirstName(contributor.getFirstName());
                }
                if (StringUtils.isNotBlank(contributor.getLastName())) {
                    existingContributor.setLastName(contributor.getLastName());
                }
                contributorDao.update(existingContributor);
                
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
