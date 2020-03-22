package ai.elimu.rest.v2.analytics;

import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/rest/v2/analytics/storybook-learning-events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoryBookLearningEventsController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @RequestMapping(value = "/csv", method = RequestMethod.POST)
    public String handleUploadCsvRequest(@RequestParam("file") MultipartFile multipartFile) {
        logger.info("handleUploadCsvRequest");
        
        String name = multipartFile.getName();
        logger.info("name: " + name);
        
        String originalFileName = multipartFile.getOriginalFilename();
        logger.info("originalFileName: " + originalFileName);
        
        String contentType = multipartFile.getContentType();
        logger.info("contentType: " + contentType);
        
        try {
            byte[] bytes = multipartFile.getBytes();
            logger.info("bytes.length: " + bytes.length);
            
            // Store a backup of the original CSV file on the filesystem (in case it will be needed for debugging)
            File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
            File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")).toString().toLowerCase());
            File analyticsDir = new File(languageDir, "analytics");
            File storyBookLearningEventsDir = new File(analyticsDir, "storybook-learning-events");
            storyBookLearningEventsDir.mkdirs();
            File csvFile = new File(storyBookLearningEventsDir, originalFileName);
            logger.info("Storing CSV file at " + csvFile);
            multipartFile.transferTo(csvFile);
            
            // Convert the events from CSV to Java
            // TODO
            
            // Store the events in the database
            // TODO
        } catch (IOException ex) {
            logger.error(null, ex);
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
