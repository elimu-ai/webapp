package ai.elimu.rest.v2.analytics;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.analytics.LearningEventType;
import ai.elimu.util.ConfigHelper;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
    
    private Logger logger = Logger.getLogger(getClass());
    
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
        
        String originalFileName = multipartFile.getOriginalFilename();
        logger.info("originalFileName: " + originalFileName);
        
        String contentType = multipartFile.getContentType();
        logger.info("contentType: " + contentType);
        
        JSONObject jsonObject = new JSONObject();
        
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
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                Application application = applicationDao.readByPackageName(language, packageName);
                if (application == null) {
                    // Return error message saying that the reporting Application has not yet been added
                    logger.warn("The application " + packageName + " has not been added");
                    
                    jsonObject.put("result", "error");
                    jsonObject.put("errorMessage", "The application " + packageName + " has not been added");
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                }
                storyBookLearningEvent.setApplication(application);
                
                Long storyBookId = Long.valueOf(csvRecord.get("storybook_id"));
                StoryBook storyBook = storyBookDao.read(storyBookId);
                storyBookLearningEvent.setStoryBook(storyBook);
                
                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
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
            logger.error(null, ex);
            
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
