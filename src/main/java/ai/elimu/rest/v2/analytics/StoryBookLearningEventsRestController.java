package ai.elimu.rest.v2.analytics;

import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.AnalyticsHelper;
import ai.elimu.util.ConfigHelper;
import java.io.File;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST API endpoint for receiving storybook learning events from the 
 * <a href="https://github.com/elimu-ai/analytics">Analytics</a> application.
 */
@RestController
@RequestMapping(value = "/rest/v2/analytics/storybook-learning-events/csv", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class StoryBookLearningEventsRestController {
    
    @PostMapping
    public String handleUploadCsvRequest(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        log.info("handleUploadCsvRequest");
        
        JSONObject jsonResponseObject = new JSONObject();
        try {
            String contentType = multipartFile.getContentType();
            log.info("contentType: " + contentType);

            long size = multipartFile.getSize();
            log.info("size: " + size);
            if (size == 0) {
                throw new IllegalArgumentException("Empty file");
            }

            // Expected format: "7161a85a0e4751cd_3001012_storybook-learning-events_2020-04-23.csv"
            String originalFilename = multipartFile.getOriginalFilename();
            log.info("originalFilename: " + originalFilename);
            if (originalFilename.length() != "7161a85a0e4751cd_3001012_storybook-learning-events_2020-04-23.csv".length()) {
                throw new IllegalArgumentException("Unexpected filename");
            }

            String androidIdExtractedFromFilename = AnalyticsHelper.extractAndroidIdFromCsvFilename(originalFilename);
            log.info("androidIdExtractedFromFilename: \"" + androidIdExtractedFromFilename + "\"");
            
            Integer versionCodeExtractedFromFilename = AnalyticsHelper.extractVersionCodeFromCsvFilename(originalFilename);
            log.info("versionCodeExtractedFromFilename: " + versionCodeExtractedFromFilename);
            
            byte[] bytes = multipartFile.getBytes();
            log.info("bytes.length: " + bytes.length);
            
            // Store the original CSV file on the filesystem
            File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
            File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
            File analyticsDir = new File(languageDir, "analytics");
            File androidIdDir = new File(analyticsDir, "android-id-" + androidIdExtractedFromFilename);
            File versionCodeDir = new File(androidIdDir, "version-code-" + versionCodeExtractedFromFilename);
            File storyBookLearningEventsDir = new File(versionCodeDir, "storybook-learning-events");
            storyBookLearningEventsDir.mkdirs();
            File csvFile = new File(storyBookLearningEventsDir, originalFilename);
            log.info("Storing CSV file at " + csvFile);
            multipartFile.transferTo(csvFile);
            log.info("csvFile.exists(): " + csvFile.exists());
            
            jsonResponseObject.put("result", "success");
            jsonResponseObject.put("successMessage", "The CSV file was uploaded");
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            
            jsonResponseObject.put("result", "error");
            jsonResponseObject.put("errorMessage", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        String jsonResponse = jsonResponseObject.toString();
        log.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
