package ai.elimu.rest.v2.analytics;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.WordLearningEvent;
import ai.elimu.model.content.Word;
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
@RequestMapping(value = "/rest/v2/analytics/word-learning-events", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WordLearningEventsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordLearningEventDao wordLearningEventDao;
    
    @Autowired
    private ApplicationDao applicationDao;
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping(value = "/csv", method = RequestMethod.POST)
    public String handleUploadCsvRequest(
            @RequestParam("file") MultipartFile multipartFile,
            HttpServletResponse response
    ) {
        logger.info("handleUploadCsvRequest");
        
        String name = multipartFile.getName();
        logger.info("name: " + name);
        
        // Expected format: "7161a85a0e4751cd_word-learning-events_2020-04-23.csv"
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("originalFilename: " + originalFilename);
        
        // TODO: Send notification to the #📊-data-collection channel in Discord
        // Hide parts of the Android ID, e.g. "7161***51cd_word-learning-events_2020-04-23.csv"
        String anonymizedOriginalFilename = originalFilename.substring(0, 3) + "***" + originalFilename.substring(12);
        DiscordHelper.sendChannelMessage("Received dataset: _" + anonymizedOriginalFilename + "_", null, null, null, null);
        
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
            File wordLearningEventsDir = new File(androidIdDir, "word-learning-events");
            wordLearningEventsDir.mkdirs();
            File csvFile = new File(wordLearningEventsDir, originalFilename);
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
                            "word_id",
                            "word_text",
                            "learning_event_type"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java
                
                WordLearningEvent wordLearningEvent = new WordLearningEvent();
                
                long timeInMillis = Long.valueOf(csvRecord.get("time"));
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(timeInMillis);
                wordLearningEvent.setTime(time);
                
                String androidId = csvRecord.get("android_id");
                wordLearningEvent.setAndroidId(androidId);
                
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
                wordLearningEvent.setApplication(application);
                
                Long wordId = Long.valueOf(csvRecord.get("word_id"));
                Word word = wordDao.read(wordId);
                logger.info("word: " + word);
                wordLearningEvent.setWord(word);
                if (word == null) {
                    // Return error message saying that the Word ID was not found
                    logger.warn("A Word with ID " + wordId + " was not found");
                    
                    jsonObject.put("result", "error");
                    jsonObject.put("errorMessage", "A Word with ID " + wordId + " was not found");
                    response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    
                    break;
                }
                
                String wordText = csvRecord.get("word_text");
                wordLearningEvent.setWordText(wordText);
                
                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                wordLearningEvent.setLearningEventType(learningEventType);
                
                // Check if the event has already been stored in the database
                WordLearningEvent existingWordLearningEvent = wordLearningEventDao.read(time, androidId, application, word);
                logger.info("existingWordLearningEvent: " + existingWordLearningEvent);
                if (existingWordLearningEvent == null) {
                    // Store the event in the database
                    wordLearningEventDao.create(wordLearningEvent);
                    logger.info("Stored WordLearningEvent in database with ID " + wordLearningEvent.getId());

                    jsonObject.put("result", "success");
                    jsonObject.put("successMessage", "The WordLearningEvent was stored in the database");
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
