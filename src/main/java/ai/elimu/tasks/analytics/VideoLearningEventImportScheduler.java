package ai.elimu.tasks.analytics;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.model.analytics.VideoLearningEvent;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.analytics.VideoLearningEventsRestController;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.csv.CsvAnalyticsExtractionHelper;

/**
 * Extracts learning events from CSV files previously received by the 
 * {@link VideoLearningEventsRestController}, and imports them into the database.
 * <p />
 * 
 * Expected folder structure:
 * <pre>
 * ├── lang-ENG
 * │   ├── analytics
 * │   │   ├── android-id-e387e38700000001
 * │   │   │   └── version-code-3001018
 * │   │   │       └── video-learning-events
 * │   │   │           ├── e387e38700000001_3001018_video-learning-events_2024-10-09.csv
 * │   │   │           ├── e387e38700000001_3001018_video-learning-events_2024-10-10.csv
 * │   │   │           ├── e387e38700000001_3001018_video-learning-events_2024-10-11.csv
 * │   │   │           ├── e387e38700000001_3001018_video-learning-events_2024-10-14.csv
 * │   │   │           ├── e387e38700000001_3001018_video-learning-events_2024-10-18.csv
 * │   │   │           └── e387e38700000001_3001018_video-learning-events_2024-10-20.csv
 * │   │   ├── android-id-e387e38700000002
 * │   │   │   └── version-code-3001018
 * │   │   │       └── video-learning-events
 * │   │   │           ├── e387e38700000002_3001018_video-learning-events_2024-10-09.csv
 * │   │   │           ├── e387e38700000002_3001018_video-learning-events_2024-10-10.csv
 * │   │   │           ├── e387e38700000002_3001018_video-learning-events_2024-10-11.csv
 * </pre>
 */
@Service
public class VideoLearningEventImportScheduler {

    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private VideoLearningEventDao videoLearningEventDao;

    @Scheduled(cron="00 30 * * * *") // Half past every hour
    public synchronized void execute() {
        logger.info("execute");

        // Lookup CSV files stored on the filesystem
        File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
        File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
        File analyticsDir = new File(languageDir, "analytics");
        logger.info("analyticsDir: " + analyticsDir);
        logger.info("analyticsDir.exists(): " + analyticsDir.exists());
        for (File analyticsDirFile : analyticsDir.listFiles()) {
            if (analyticsDirFile.getName().startsWith("android-id-")) {
                File androidIdDir = new File(analyticsDir, analyticsDirFile.getName());
                for (File androidIdDirFile : androidIdDir.listFiles()) {
                    if (androidIdDirFile.getName().startsWith("version-code-")) {
                        File versionCodeDir = new File(androidIdDir, androidIdDirFile.getName());
                        for (File versionCodeDirFile : versionCodeDir.listFiles()) {
                            if (versionCodeDirFile.getName().equals("video-learning-events")) {
                                File videoLearningEventsDir = new File(versionCodeDir, versionCodeDirFile.getName());
                                for (File csvFile : videoLearningEventsDir.listFiles()) {
                                    logger.info("csvFile: " + csvFile);

                                    // Convert from CSV to Java
                                    List<VideoLearningEvent> events = CsvAnalyticsExtractionHelper.extractVideoLearningEvents(csvFile);
                                    logger.info("events.size(): " + events.size());

                                    // Store in database
                                    for (VideoLearningEvent event : events) {
                                        // Check if the event has already been stored in the database
                                        VideoLearningEvent existingVideoLearningEvent = videoLearningEventDao.read(event.getTimestamp(), event.getAndroidId(), event.getPackageName(), event.getVideoTitle());
                                        if (existingVideoLearningEvent != null) {
                                            logger.warn("The event has already been stored in the database. Skipping data import.");
                                            continue;
                                        }

                                        // Store the event in the database
                                        videoLearningEventDao.create(event);
                                        logger.info("Stored event in database with ID " + event.getId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        logger.info("execute complete");
    }
}
