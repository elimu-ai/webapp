package ai.elimu.rest.v2.analytics;

import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.AnalyticsHelper;
import ai.elimu.util.ConfigHelper;
import java.io.File;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/rest/v2/analytics/video-learning-events", produces = MediaType.APPLICATION_JSON_VALUE)
public class VideoLearningEventsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @RequestMapping(value = "/csv", method = RequestMethod.POST)
    public String handleUploadCsvRequest(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        logger.info("handleUploadCsvRequest");
        
        JSONObject jsonResponseObject = new JSONObject();
        try {
            String contentType = multipartFile.getContentType();
            logger.info("contentType: " + contentType);

            long size = multipartFile.getSize();
            logger.info("size: " + size);
            if (size == 0) {
                throw new IllegalArgumentException("Empty file");
            }

            // Expected format: "7161a85a0e4751cd_3001012_video-learning-events_2020-04-23.csv"
            String originalFilename = multipartFile.getOriginalFilename();
            logger.info("originalFilename: " + originalFilename);
            if (originalFilename.length() != 61) {
                throw new IllegalArgumentException("Unexpected filename");
            }

            String androidIdExtractedFromFilename = AnalyticsHelper.extractAndroidIdFromCsvFilename(originalFilename);
            logger.info("androidIdExtractedFromFilename: \"" + androidIdExtractedFromFilename + "\"");
            
            Integer versionCodeExtractedFromFilename = AnalyticsHelper.extractVersionCodeFromCsvFilename(originalFilename);
            logger.info("versionCodeExtractedFromFilename: " + versionCodeExtractedFromFilename);
            
            byte[] bytes = multipartFile.getBytes();
            logger.info("bytes.length: " + bytes.length);
            
            // Store the original CSV file on the filesystem
            File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
            File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
            File analyticsDir = new File(languageDir, "analytics");
            File androidIdDir = new File(analyticsDir, "android-id-" + androidIdExtractedFromFilename);
            File versionCodeDir = new File(androidIdDir, "version-code-" + versionCodeExtractedFromFilename);
            File videoLearningEventsDir = new File(versionCodeDir, "video-learning-events");
            videoLearningEventsDir.mkdirs();
            File csvFile = new File(videoLearningEventsDir, originalFilename);
            logger.info("Storing CSV file at " + csvFile);
            multipartFile.transferTo(csvFile);
            
            jsonResponseObject.put("result", "success");
            jsonResponseObject.put("successMessage", "The CSV file was uploaded");
            response.setStatus(HttpStatus.OK.value());
        } catch (Exception ex) {
            logger.error(ex);
            
            jsonResponseObject.put("result", "error");
            jsonResponseObject.put("errorMessage", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        String jsonResponse = jsonResponseObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
