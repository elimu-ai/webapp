package ai.elimu.rest.v2.crowdsource.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import ai.elimu.util.Mailer;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import javax.servlet.http.HttpServletResponse;
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
        
        // Check if the Contributor has already been stored in the database
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
            
            jsonObject.put("result", "success");
            jsonObject.put("successMessage", "The Contributor was stored in the database");
            
            try {
                // Send welcome e-mail
                String to = contributor.getEmail();
                String from = "elimu.ai <info@elimu.ai>";
                String subject = "Welcome to the elimu.ai Community";
                String title = "Welcome!";
                String htmlText = "<p>Hi, " + contributor.getFirstName() + "</p>";
                htmlText += "<p>Thank you very much for registering as a contributor to the elimu.ai Community. We are glad to see you join us!</p>";
                htmlText += "<h2>Purpose</h2>";
                htmlText += "<p>The purpose of elimu.ai is to provide <i>every child</i> with access to quality basic education.</p>";
                htmlText += "<h2>Why?</h2>";
                htmlText += "<p>The word \"elimu\" is Swahili for \"education\". We believe that a quality basic education is the right of every child no matter her social or geographical background.</p>";
                htmlText += "<h2>How?</h2>";
                htmlText += "<p>With your help, this is what we aim to achieve:</p>";
                htmlText += "<p><blockquote>\"The elimu.ai Community develops free and open source software for teaching children the basics of reading, writing and arithmetic.\"</blockquote></p>";
                htmlText += "<p><img src=\"https://gallery.mailchimp.com/1a69583fdeec7d1888db043c0/images/72b31d67-58fd-443e-a6be-3ef2095cfe3b.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
                htmlText += "<h2>Chat</h2>";
                htmlText += "<p>In Slack you can chat with the other elimu.ai Community members:</p>";
                Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Open chat", "https://join.slack.com/t/elimu-ai/shared_invite/zt-eoc921ow-0cfjATlIF2X~zHhSgSyaAw");
            } catch(Exception e) {
                logger.error("Sending welcome e-mail failed: ", e);
            }
        } else {
            // Return error message saying that the Contributor has already been created
            logger.warn("The Contributor has already been stored in the database");
            
            // Update existing contributor with latest account details fetched from provider
            // TODO

            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "The Contributor has already been stored in the database");
//            response.setStatus(HttpStatus.CONFLICT.value());
        }
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
