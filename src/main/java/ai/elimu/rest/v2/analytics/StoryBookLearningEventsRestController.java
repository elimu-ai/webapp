package ai.elimu.rest.v2.analytics;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import ai.elimu.model.content.StoryBook;
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
@RequestMapping(value = "/rest/v2/analytics/storybook-learning-events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoryBookLearningEventsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookLearningEventDao storyBookLearningEventDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @RequestMapping(value = "/csv", method = RequestMethod.POST)
    public String handleUploadCsvRequest(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        logger.info("handleUploadCsvRequest");
        
        String name = multipartFile.getName();
        logger.info("name: " + name);
        
        // Expected format: "7161a85a0e4751cd_storybook-learning-events_2020-04-23.csv"
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("originalFilename: " + originalFilename);
        
        // TODO: Send notification to the #📊-data-collection channel in Discord
        // Hide parts of the Android ID, e.g. "7161***51cd_word-learning-events_2020-04-23.csv"
        String anonymizedOriginalFilename = originalFilename.substring(0, 3) + "***" + originalFilename.substring(12);
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
            File storyBookLearningEventsDir = new File(androidIdDir, "storybook-learning-events");
            storyBookLearningEventsDir.mkdirs();
            File csvFile = new File(storyBookLearningEventsDir, originalFilename);
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
                            "storybook_id",
                            "learning_event_type"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java
                
                StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
                
                long timeInMillis = Long.valueOf(csvRecord.get("time"));
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(timeInMillis);
                storyBookLearningEvent.setTime(time);
                
                String androidId = csvRecord.get("android_id");
                storyBookLearningEvent.setAndroidId(androidId);
                
                String packageName = csvRecord.get("package_name");
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
                storyBookLearningEvent.setApplication(application);
                
                Long storyBookId = Long.valueOf(csvRecord.get("storybook_id"));
                StoryBook storyBook = storyBookDao.read(storyBookId);
                logger.info("storyBook: " + storyBook);
                storyBookLearningEvent.setStoryBook(storyBook);
                if (storyBook == null) {
                    // Return error message saying that the StoryBook ID was not found
                    logger.warn("A StoryBook with ID " + storyBookId + " was not found");
                    
                    jsonObject.put("result", "error");
                    jsonObject.put("errorMessage", "A StoryBook with ID " + storyBookId + " was not found");
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    
                    break;
                }
                
                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                logger.info("learningEventType: " + learningEventType);
                storyBookLearningEvent.setLearningEventType(learningEventType);
                
                // Check if the event has already been stored in the database
                StoryBookLearningEvent existingStoryBookLearningEvent = storyBookLearningEventDao.read(time, androidId, application, storyBook);
                logger.info("existingStoryBookLearningEvent: " + existingStoryBookLearningEvent);
                if (existingStoryBookLearningEvent == null) {
                    // Store the event in the database
                    storyBookLearningEventDao.create(storyBookLearningEvent);
                    logger.info("Stored StoryBookLearningEvent in database with ID " + storyBookLearningEvent.getId());

                    jsonObject.put("result", "success");
                    jsonObject.put("successMessage", "The StoryBookLearningEvent was stored in the database");
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
