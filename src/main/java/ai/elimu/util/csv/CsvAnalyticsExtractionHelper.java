package ai.elimu.util.csv;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.admin.Application;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import ai.elimu.rest.v2.analytics.StoryBookLearningEventsRestController;
import ai.elimu.web.analytics.StoryBookLearningEventCsvExportController;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Slf4j
public class CsvAnalyticsExtractionHelper {
    
    /**
     * For information on how the CSV files were generated, see {@link StoryBookLearningEventCsvExportController#handleRequest}.
     * <p />
     * Also see {@link StoryBookLearningEventsRestController#handleUploadCsvRequest}
     */
    public static List<StoryBookLearningEvent> getStoryBookLearningEventsFromCsvBackup(File csvFile, ApplicationDao applicationDao, StoryBookDao storyBookDao) {
        log.info("getStoryBookLearningEventsFromCsvBackup");
        
        List<StoryBookLearningEvent> storyBookLearningEvents = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        log.info("csvFilePath: " + csvFilePath);
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
                log.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java (see similar code in StoryBookLearningEventsRestController)
                
                StoryBookLearningEvent storyBookLearningEvent = new StoryBookLearningEvent();
                
                long timeInMillis = Long.valueOf(csvRecord.get("time"));
                Calendar timestamp = Calendar.getInstance();
                timestamp.setTimeInMillis(timeInMillis);
                storyBookLearningEvent.setTimestamp(timestamp);
                
                String androidId = csvRecord.get("android_id");
                storyBookLearningEvent.setAndroidId(androidId);
                
                String packageName = csvRecord.get("package_name");
                storyBookLearningEvent.setPackageName(packageName);
                
                Application application = applicationDao.readByPackageName(packageName);
                log.debug("application: " + application);
                storyBookLearningEvent.setApplication(application);
                
                Long storyBookId = Long.valueOf(csvRecord.get("storybook_id"));
                log.debug("storyBookId: " + storyBookId);
                storyBookLearningEvent.setStoryBookId(storyBookId);
                
                String storyBookTitle = csvRecord.get("storybook_title");
                log.debug("storyBookTitle: \"" + storyBookTitle + "\"");
                storyBookLearningEvent.setStoryBookTitle(storyBookTitle);
                
                StoryBook storyBook = storyBookDao.readByTitle(storyBookTitle);
                log.debug("storyBook: " + storyBook);
                storyBookLearningEvent.setStoryBook(storyBook);
                
                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                log.debug("learningEventType: " + learningEventType);
                storyBookLearningEvent.setLearningEventType(learningEventType);
                
                storyBookLearningEvents.add(storyBookLearningEvent);
            }
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        
        return storyBookLearningEvents;
    }

    public static List<VideoLearningEvent> extractVideoLearningEvents(File csvFile) {
        log.info("extractVideoLearningEvents");

        List<VideoLearningEvent> videoLearningEvents = new ArrayList<>();

        // Iterate each row in the CSV file
        Path csvFilePath = Paths.get(csvFile.toURI());
        log.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id", // The Android database ID
                            "timestamp",
                            "android_id",
                            "package_name",
                            "video_id",
                            "video_title",
                            "learning_event_type",
                            "additional_data"
                    )
                    .withSkipHeaderRecord();
            log.info("header: " + Arrays.toString(csvFormat.getHeader()));
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                log.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to Java

                VideoLearningEvent videoLearningEvent = new VideoLearningEvent();
                
                long timestampInMillis = Long.valueOf(csvRecord.get("timestamp"));
                Calendar timestamp = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                timestamp.setTimeInMillis(timestampInMillis);
                videoLearningEvent.setTimestamp(timestamp);
                
                String androidId = csvRecord.get("android_id");
                videoLearningEvent.setAndroidId(androidId);
                
                String packageName = csvRecord.get("package_name");
                videoLearningEvent.setPackageName(packageName);

                Long videoId = Long.valueOf(csvRecord.get("video_id"));
                videoLearningEvent.setVideoId(videoId);

                String videoTitle = csvRecord.get("video_title");
                videoLearningEvent.setVideoTitle(videoTitle);

                LearningEventType learningEventType = LearningEventType.valueOf(csvRecord.get("learning_event_type"));
                videoLearningEvent.setLearningEventType(learningEventType);

                String additionalData = csvRecord.get("additional_data");
                videoLearningEvent.setAdditionalData(additionalData);

                videoLearningEvents.add(videoLearningEvent);
            }
            csvParser.close();
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }

        return videoLearningEvents;
    }
}
