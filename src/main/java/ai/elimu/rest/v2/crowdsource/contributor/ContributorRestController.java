package ai.elimu.rest.v2.crowdsource.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
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
 * Stores a Contributor in the database.
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/contributor", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContributorRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleGetRequest(@RequestBody String requestBody) {
        logger.info("handlePostRequest");
        
        logger.info("requestBody: " + requestBody);
        
        // Store the Contributor in the database
        Contributor contributor = new Contributor();
        // TODO
//        contributorDao.create(contributor);
        
        JSONObject contributorJSONObject = new JSONObject();
        contributorJSONObject.put("providerIdGoogle", contributor.getProviderIdGoogle());
        contributorJSONObject.put("email", contributor.getEmail());
        
        String jsonResponse = contributorJSONObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
