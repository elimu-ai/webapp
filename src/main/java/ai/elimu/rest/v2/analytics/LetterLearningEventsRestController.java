package ai.elimu.rest.v2.analytics;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.LetterLearningEvent;
import ai.elimu.model.content.Letter;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import ai.elimu.util.AnalyticsHelper;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
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
@RequestMapping(value = "/rest/v2/analytics/letter-learning-events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LetterLearningEventsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterLearningEventDao letterLearningEventDao;
    
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
        
        // Expected format: "7161a85a0e4751cd_3001012_letter-learning-events_2020-04-23.csv"
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("originalFilename: " + originalFilename);
        
        // TODO: Send notification to the #📊-data-collection channel in Discord
        // Hide parts of the Android ID, e.g. "7161***51cd_3001012_word-learning-events_2020-04-23.csv"
        String anonymizedOriginalFilename = originalFilename.substring(0, 4) + "***" + originalFilename.substring(12);
        DiscordHelper.sendChannelMessage("Received dataset: `" + anonymizedOriginalFilename + "`", null, null, null, null);
        
        String androidIdExtractedFromFilename = AnalyticsHelper.extractAndroidIdFromCsvFilename(originalFilename);
        logger.info("androidIdExtractedFromFilename: \"" + androidIdExtractedFromFilename + "\"");
        
        Integer versionCodeExtractedFromFilename = AnalyticsHelper.extractVersionCodeFromCsvFilename(originalFilename);
        logger.info("versionCodeExtractedFromFilename: " + versionCodeExtractedFromFilename);
        
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
            File androidIdDir = new File(analyticsDir, "android-id-" + androidIdExtractedFromFilename);
            File versionCodeDir = new File(androidIdDir, "version-code-" + versionCodeExtractedFromFilename);
            File letterLearningEventsDir = new File(versionCodeDir, "letter-learning-events");
            letterLearningEventsDir.mkdirs();
            File csvFile = new File(letterLearningEventsDir, originalFilename);
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
                            "learning_event_type"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java
                
                LetterLearningEvent letterLearningEvent = new LetterLearningEvent();
                
                long timeInMillis = Long.valueOf(csvRecord.get("time"));
                Calendar timestamp = Calendar.getInstance();
                timestamp.setTimeInMillis(timeInMillis);
                letterLearningEvent.setTimestamp(timestamp);
                
                String androidId = csvRecord.get("android_id");
                letterLearningEvent.setAndroidId(androidId);
                
                String packageName = csvRecord.get("package_name");
                letterLearningEvent.setPackageName(packageName);
                
                Application application = applicationDao.readByPackageName(packageName);
                logger.info("application: " + application);
                if (application == null) {
                    // Return error message saying that the reporting Application has not yet been added
                    logger.warn("An Application with package name " + packageName + " was not found");
                    
                    jsonObject.put("result", "error");
                    jsonObject.put("errorMessage", "An Application with package name " + packageName + " was not found");
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    
                    break;
                }
                letterLearningEvent.setApplication(application);
                
                Long letterId = Long.valueOf(csvRecord.get("letter_id"));
                Letter letter = letterDao.read(letterId);
                logger.info("letter: " + letter);
                letterLearningEvent.setLetter(letter);
                if (letter == null) {
                    // Return error message saying that the Letter ID was not found
                    logger.warn("A Letter with ID " + letterId + " was not found");
                    
                    jsonObject.put("result", "error");
                    jsonObject.put("errorMessage", "A Letter with ID " + letterId + " was not found");
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    
                    break;
                }
                
                String letterText = csvRecord.get("letter_text");
                letterLearningEvent.setLetterText(letterText);
                
                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                letterLearningEvent.setLearningEventType(learningEventType);
                
                // Check if the event has already been stored in the database
                LetterLearningEvent existingLetterLearningEvent = letterLearningEventDao.read(timestamp, androidId, application, letter);
                logger.info("existingLetterLearningEvent: " + existingLetterLearningEvent);
                if (existingLetterLearningEvent == null) {
                    // Store the event in the database
                    letterLearningEventDao.create(letterLearningEvent);
                    logger.info("Stored LetterLearningEvent in database with ID " + letterLearningEvent.getId());

                    jsonObject.put("result", "success");
                    jsonObject.put("successMessage", "The LetterLearningEvent was stored in the database");
                } else {
                    // Return error message saying that the event has already been uploaded
                    logger.warn("The event has already been stored in the database");
                    
                    jsonObject.put("result", "error");
                    jsonObject.put("errorMessage", "The event has already been stored in the database");
                    response.setStatus(HttpStatus.CONFLICT.value());
                }
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
