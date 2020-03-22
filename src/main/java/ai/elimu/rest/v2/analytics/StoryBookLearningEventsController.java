package ai.elimu.rest.v2.analytics;

import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.analytics.LearningEventType;
import ai.elimu.model.gson.analytics.StoryBookLearningEventGson;
import ai.elimu.util.ConfigHelper;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
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
            
            // Iterate each row in the CSV file
            Path csvFilePath = Paths.get(csvFile.toURI());
            logger.info("csvFilePath: " + csvFilePath);
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
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
                
                // Convert from CSV to GSON
                
                StoryBookLearningEventGson storyBookLearningEventGson = new StoryBookLearningEventGson();
                
                long timeInMillis = Long.valueOf(csvRecord.get("time"));
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(timeInMillis);
                storyBookLearningEventGson.setTime(time);
                
                String androidId = csvRecord.get("android_id");
                storyBookLearningEventGson.setAndroidId(androidId);
                
                String packageName = csvRecord.get("package_name");
                storyBookLearningEventGson.setPackageName(packageName);
                
                Long storyBookId = Long.valueOf(csvRecord.get("storybook_id"));
                storyBookLearningEventGson.setStoryBookId(storyBookId);
                
                if (StringUtils.isNotBlank(csvRecord.get("learning_event_type"))) {
                    LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                    storyBookLearningEventGson.setLearningEventType(learningEventType);
                }
                
                
                // Convert from GSON to JPA
                // TODO
                
                
                // Store the event in the database
                // TODO
            }
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
