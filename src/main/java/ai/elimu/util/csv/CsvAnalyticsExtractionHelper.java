package ai.elimu.util.csv;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import ai.elimu.rest.v2.analytics.StoryBookLearningEventsRestController;
import ai.elimu.web.analytics.StoryBookLearningEventCsvExportController;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvAnalyticsExtractionHelper {
    
    private static final Logger logger = LogManager.getLogger();
    
    /**
     * For information on how the CSV files were generated, see {@link StoryBookLearningEventCsvExportController#handleRequest}.
     * <p />
     * Also see {@link StoryBookLearningEventsRestController#handleUploadCsvRequest}
     */
    public static List<StoryBookLearningEvent> getStoryBookLearningEventsFromCsvBackup(File csvFile, ApplicationDao applicationDao, StoryBookDao storyBookDao) {
        logger.info("getStoryBookLearningEventsFromCsvBackup");
        
        List<StoryBookLearningEvent> storyBookLearningEvents = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id", // The Room database ID
                            "time",
                            "android_id",
                            "package_name",
                            "storybook_id",
                            "storybook_title",
                            "learning_event_type"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java (see similar code in StoryBookLearningEventsRestController)
                
                StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
                
                long timeInMillis = Long.valueOf(csvRecord.get("time"));
                Calendar time = Calendar.getInstance();
                time.setTimeInMillis(timeInMillis);
                storyBookLearningEvent.setTime(time);
                
                String androidId = csvRecord.get("android_id");
                storyBookLearningEvent.setAndroidId(androidId);
                
                String packageName = csvRecord.get("package_name");
                storyBookLearningEvent.setPackageName(packageName);
                
                Application application = applicationDao.readByPackageName(packageName);
                logger.info("application: " + application);
                storyBookLearningEvent.setApplication(application);
                
                Long storyBookId = Long.valueOf(csvRecord.get("storybook_id"));
                logger.info("storyBookId: " + storyBookId);
                storyBookLearningEvent.setStoryBookId(storyBookId);
                
                String storyBookTitle = csvRecord.get("storybook_title");
                logger.info("storyBookTitle: \"" + storyBookTitle + "\"");
                storyBookLearningEvent.setStoryBookTitle(storyBookTitle);
                
                StoryBook storyBook = storyBookDao.readByTitle(storyBookTitle);
                logger.info("storyBook: " + storyBook);
                storyBookLearningEvent.setStoryBook(storyBook);
                
                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                logger.info("learningEventType: " + learningEventType);
                storyBookLearningEvent.setLearningEventType(learningEventType);
                
                storyBookLearningEvents.add(storyBookLearningEvent);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return storyBookLearningEvents;
    }
}
