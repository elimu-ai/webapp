package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 * <p />
 * 
 * Stores a {@link Contributor} in the database.
 * <p />
 * 
 * Note that authentication for the same Google accounts are done in the 
 * {@link SignOnControllerGoogle}.
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/contributors", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContributorsRestControllerGoogle {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleGetRequest(
            @RequestBody String requestBody,
            HttpServletResponse response
    ) {
        logger.info("handlePostRequest");
        
        logger.info("requestBody: " + requestBody);
        
        JSONObject contributorJSONObject = new JSONObject(requestBody);
        logger.info("contributorJSONObject: " + contributorJSONObject);
        
        String providerIdGoogle = contributorJSONObject.getString("providerIdGoogle");
        String email = contributorJSONObject.getString("email");
        String firstName = contributorJSONObject.getString("firstName");
        String lastName = contributorJSONObject.getString("lastName");
        String imageUrl = contributorJSONObject.getString("imageUrl");
        
        JSONObject jsonObject = new JSONObject();
        
        // Look for existing Contributor with matching e-mail address
        Contributor existingContributor = contributorDao.read(email);
        logger.info("existingContributor: " + existingContributor);
        if (existingContributor == null) {
            // Store the Contributor in the database
            Contributor contributor = new Contributor();
            contributor.setRegistrationTime(Calendar.getInstance());
            contributor.setProviderIdGoogle(providerIdGoogle);
            contributor.setEmail(email);
            contributor.setFirstName(firstName);
            contributor.setLastName(lastName);
            contributor.setImageUrl(imageUrl);
            contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
            contributorDao.create(contributor);
            
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/contributor/" + contributor.getId();
            String embedThumbnailUrl = null;
            if (StringUtils.isNotBlank(contributor.getImageUrl())) {
                embedThumbnailUrl = contributor.getImageUrl();
            }
            DiscordHelper.sendChannelMessage(
                    "Contributor joined via the Crowdsource app: " + contentUrl,
                    contributor.getFirstName() + " " + contributor.getLastName(),
                    null,
                    null,
                    embedThumbnailUrl
            );
            
            jsonObject.put("result", "success");
            jsonObject.put("successMessage", "The Contributor was stored in the database");
        } else {
            // Return error message saying that the Contributor has already been created
            logger.warn("The Contributor has already been stored in the database");
            
            // Update existing contributor with latest values fetched from provider
            if (StringUtils.isNotBlank(providerIdGoogle)) {
                existingContributor.setProviderIdGoogle(providerIdGoogle);
            }
            if (StringUtils.isNotBlank(imageUrl)) {
                existingContributor.setImageUrl(imageUrl);
            }
            if (StringUtils.isNotBlank(firstName)) {
                existingContributor.setFirstName(firstName);
            }
            if (StringUtils.isNotBlank(lastName)) {
                existingContributor.setLastName(lastName);
            }
            contributorDao.update(existingContributor);

            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "The Contributor has already been stored in the database");
//            response.setStatus(HttpStatus.CONFLICT.value());
        }
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
