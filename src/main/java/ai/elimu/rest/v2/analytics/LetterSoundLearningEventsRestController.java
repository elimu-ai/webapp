package ai.elimu.rest.v2.analytics;

import java.io.File;
import java.io.IOException;
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

import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.AnalyticsHelper;
import ai.elimu.util.ConfigHelper;

/**
 * REST API endpoint for receiving letter-sound learning events from the 
 * <a href="https://github.com/elimu-ai/analytics">Analytics</a> application.
 */
@RestController
@RequestMapping(value = "/rest/v2/analytics/letter-sound-learning-events/csv", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class LetterSoundLearningEventsRestController {

    @PostMapping
    public String handleUploadCsvRequest(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        log.info("handleUploadCsvRequest");

        // Expected format: "7161a85a0e4751cd_3001017_letter-sound-learning-events_2023-10-27.csv"
        String originalFilename = multipartFile.getOriginalFilename();
        log.info("originalFilename: " + originalFilename);

        String androidIdExtractedFromFilename = AnalyticsHelper.extractAndroidIdFromCsvFilename(originalFilename);
        log.info("androidIdExtractedFromFilename: \"" + androidIdExtractedFromFilename + "\"");

        Integer versionCodeExtractedFromFilename = AnalyticsHelper.extractVersionCodeFromCsvFilename(originalFilename);
        log.info("versionCodeExtractedFromFilename: " + versionCodeExtractedFromFilename);

        // Store the original CSV file on the filesystem, e.g.
        // ~/.elimuai/lang-HIN/analytics/android-id-7161a85a0e4751cd/version-code-3001017/letter-sound-learning-events/7161a85a0e4751cd_3001017_letter-sound-learning-events_2023-10-27.csv"
        File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
        File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
        File analyticsDir = new File(languageDir, "analytics");
        File androidIdDir = new File(analyticsDir, "android-id-" + androidIdExtractedFromFilename);
        File versionCodeDir = new File(androidIdDir, "version-code-" + versionCodeExtractedFromFilename);
        File letterSoundLearningEventsDir = new File(versionCodeDir, "letter-sound-learning-events");
        letterSoundLearningEventsDir.mkdirs();
        File csvFile = new File(letterSoundLearningEventsDir, originalFilename);
        log.info("Storing CSV file at " + csvFile);
        
        JSONObject jsonObject = new JSONObject();
        try {
            multipartFile.transferTo(csvFile);
            jsonObject.put("result", "success");
            jsonObject.put("successMessage", "The CSV file was successfully uploaded");
        } catch (IllegalStateException | IOException e) {
            log.error(e.getMessage());
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        String jsonResponse = jsonObject.toString();
        log.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
