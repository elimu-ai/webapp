package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.util.Web3Helper;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
 * Note that authentication for the same Web3 accounts are done in the 
 * {@link SignOnControllerWeb3}.
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/contributors/web3", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ContributorsRestControllerWeb3 {
    
    /**
     * Must match the signature message used in the Crowdsource application.
     */
    private static final String SIGNATURE_MESSAGE = "I verify ownership of this account 👍";
    
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
        
        JSONObject jsonObject = new JSONObject();
        
        String providerIdWeb3 = contributorJSONObject.getString("providerIdWeb3");
        logger.info("providerIdWeb3: " + providerIdWeb3);
        if (StringUtils.isBlank(providerIdWeb3)) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "Missing providerIdWeb3");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            
            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }
        providerIdWeb3 = providerIdWeb3.toLowerCase();
        String providerIdWeb3Signature = contributorJSONObject.getString("providerIdWeb3Signature");
        logger.info("providerIdWeb3Signature: " + providerIdWeb3Signature);
        if (StringUtils.isBlank(providerIdWeb3Signature)) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "Missing providerIdWeb3Signature");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            
            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }
        
        // Check if the signature is valid
        if (!Web3Helper.isSignatureValid(providerIdWeb3, providerIdWeb3Signature, SIGNATURE_MESSAGE)) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "Invalid signature");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            
            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }
        logger.info("Valid signature ✍️");
        
        // Check if a Contributor with this ETH address already exists in the database
        Contributor existingContributor = contributorDao.readByProviderIdWeb3(providerIdWeb3);
        logger.info("existingContributor: " + existingContributor);
        if (existingContributor == null) {
            // Store new Contributor in database
            Contributor contributor = new Contributor();
            contributor.setProviderIdWeb3(providerIdWeb3);
            contributor.setEmail(providerIdWeb3 + "@ethmail.cc");
            contributor.setRegistrationTime(Calendar.getInstance());
            contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR)));
            contributorDao.create(contributor);
            logger.info("Contributor " + contributor.getProviderIdWeb3() + " was created in the database");
            
            jsonObject.put("result", "success");
            jsonObject.put("successMessage", "The Contributor was stored in the database");
        } else {
            jsonObject.put("result", "warning");
            jsonObject.put("warningMessage", "The Contributor has already been stored in the database");
        }
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
