package ai.elimu.rest.v2.analytics;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.AnalyticsHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/rest/v2/analytics/letter-assessment-events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LetterAssessmentEventsRestController {
    
    private Logger logger = LogManager.getLogger();
    
//    @Autowired
//    private LetterAssessmentEventDao letterAssessmentEventDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @RequestMapping(value = "/csv", method = RequestMethod.POST)
    public String handleUploadCsvRequest(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        logger.info("handleUploadCsvRequest");
        
        String name = multipartFile.getName();
        logger.info("name: " + name);
        
        // Expected format: "7161a85a0e4751cd_letter-assessment-events_2020-04-23.csv"
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("originalFilename: " + originalFilename);
        
        // TODO: Send notification to analytics channel in Discord
        String anonymizedOriginalFilename = "****************" + originalFilename.substring(16);
        DiscordHelper.sendChannelMessage("Received dataset: " + anonymizedOriginalFilename, null, null, null, null);
        
        String androidIdExtractedFromFilename = AnalyticsHelper.extractAndroidIdFromCsvFilename(originalFilename);
        logger.info("androidIdExtractedFromFilename: \"" + androidIdExtractedFromFilename + "\"");
        
        String contentType = multipartFile.getContentType();
        logger.info("contentType: " + contentType);
        
        JSONObject jsonObject = new JSONObject();
        
        try {
            byte[] bytes = multipartFile.getBytes();
            logger.info("bytes.length: " + bytes.length);
            
            // Store a backup of the original CSV file on the filesystem (in case it will be needed for debugging)
            File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
            File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
            File analyticsDir = new File(languageDir, "analytics");
            File androidIdDir = new File(analyticsDir, "android-id_" + androidIdExtractedFromFilename);
            File letterAssessmentEventsDir = new File(androidIdDir, "letter-assessment-events");
            letterAssessmentEventsDir.mkdirs();
            File csvFile = new File(letterAssessmentEventsDir, originalFilename);
            logger.info("Storing CSV file at " + csvFile);
            multipartFile.transferTo(csvFile);
            
            // Iterate each row in the CSV file
            Path csvFilePath = Paths.get(csvFile.toURI());
            logger.info("csvFilePath: " + csvFilePath);
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id", // The Room database ID
                            "time",
                            "android_id",
                            "package_name",
                            "letter_id",
                            "letter_text",
                            "mastery_score",
                            "time_spent_ms"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java
                
                // TODO
            }
        } catch (Exception ex) {
            logger.error(ex);
            
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
