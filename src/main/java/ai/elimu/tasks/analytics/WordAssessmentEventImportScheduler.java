package ai.elimu.tasks.analytics;

import ai.elimu.dao.StudentDao;
import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.entity.analytics.WordAssessmentEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.analytics.WordAssessmentEventsRestController;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import ai.elimu.util.csv.CsvAnalyticsExtractionHelper;
import java.io.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Extracts assessment events from CSV files previously received by the {@link WordAssessmentEventsRestController}, and imports them into the database.
 * <p/>
 * <p>
 * Expected folder structure:
 * <pre>
 * ├── lang-ENG
 * │   ├── analytics
 * │   │   ├── android-id-e387e38700000001
 * │   │   │   └── word-assessment-events
 * │   │   │       ├── e387e38700000001_3001018_word-assessment-events_2024-10-09.csv
 * │   │   │       ├── e387e38700000001_3001018_word-assessment-events_2024-10-10.csv
 * │   │   │       ├── e387e38700000001_3001018_word-assessment-events_2024-10-11.csv
 * │   │   │       ├── e387e38700000001_3001018_word-assessment-events_2024-10-14.csv
 * │   │   │       ├── e387e38700000001_3001018_word-assessment-events_2024-10-18.csv
 * │   │   │       └── e387e38700000001_3001018_word-assessment-events_2024-10-20.csv
 * │   │   ├── android-id-e387e38700000002
 * │   │   │   └── word-assessment-events
 * │   │   │       ├── e387e38700000002_3001018_word-assessment-events_2024-10-09.csv
 * │   │   │       ├── e387e38700000002_3001018_word-assessment-events_2024-10-10.csv
 * │   │   │       ├── e387e38700000002_3001018_word-assessment-events_2024-10-11.csv
 * </pre>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class WordAssessmentEventImportScheduler {

  private final WordAssessmentEventDao wordAssessmentEventDao;

  private final StudentDao studentDao;

  @Scheduled(cron = "00 35 * * * *") // 25 minutes past every hour
  public synchronized void execute() {
    log.info("execute");

    Long studentId = null;
    Integer eventImportCount = 0;

    // Lookup CSV files stored on the filesystem
    File elimuAiDir = new File(System.getProperty("user.home"), ".elimu-ai");
    File languageDir = new File(elimuAiDir, "lang-" + Language.valueOf(ConfigHelper.getProperty("content.language")));
    File analyticsDir = new File(languageDir, "analytics");
    log.info("analyticsDir: " + analyticsDir);
    analyticsDir.mkdirs();
    for (File analyticsDirFile : analyticsDir.listFiles()) {
      if (analyticsDirFile.getName().startsWith("android-id-")) {
        File androidIdDir = new File(analyticsDir, analyticsDirFile.getName());
        for (File androidIdDirFile : androidIdDir.listFiles()) {
          if (androidIdDirFile.getName().equals("word-assessment-events")) {
            File wordAssessmentEventsDir = new File(androidIdDir, androidIdDirFile.getName());
            for (File csvFile : wordAssessmentEventsDir.listFiles()) {
              log.info("csvFile: " + csvFile);

              // Convert from CSV to Java
              List<WordAssessmentEvent> events = CsvAnalyticsExtractionHelper.extractWordAssessmentEvents(csvFile);
              log.info("events.size(): " + events.size());

              // Store in database
              for (WordAssessmentEvent event : events) {
                // Check if the event has already been stored in the database
                WordAssessmentEvent existingWordAssessmentEvent = wordAssessmentEventDao.read(event.getTimestamp(), event.getAndroidId(), event.getPackageName());
                if (existingWordAssessmentEvent != null) {
                  log.warn("The event has already been stored in the database. Skipping data import.");
                  continue;
                }

                // Generate Student ID
                Student existingStudent = studentDao.read(event.getAndroidId());
                studentId = existingStudent.getId();
                if (existingStudent == null) {
                  Student student = new Student();
                  student.setAndroidId(event.getAndroidId());
                  studentDao.create(student);
                  log.info("Stored Student in database with ID " + student.getId());
                  studentId = student.getId();
                }

                // Store the event in the database
                wordAssessmentEventDao.create(event);
                log.info("Stored event in database with ID " + event.getId());
                eventImportCount++;
              }
            }
          }
        }
      }
    }

    if ((studentId != null) && (eventImportCount > 0)) {
      String contentUrl = DomainHelper.getBaseUrl() + "/analytics/students/" + studentId;
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Imported " + eventImportCount + " word assessment events: " + contentUrl);
    }

    log.info("execute complete");
  }
}
