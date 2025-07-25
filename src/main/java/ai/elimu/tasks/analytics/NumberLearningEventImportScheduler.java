package ai.elimu.tasks.analytics;

import ai.elimu.dao.StudentDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.entity.analytics.NumberLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.analytics.NumberLearningEventsRestController;
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
 * Extracts learning events from CSV files previously received by the {@link NumberLearningEventsRestController}, and imports them into the database.
 * <p/>
 * <p>
 * Expected folder structure:
 * <pre>
 * ├── lang-ENG
 * │   ├── analytics
 * │   │   ├── android-id-e387e38700000001
 * │   │   │   └── number-learning-events
 * │   │   │       ├── e387e38700000001_3004004_number-learning-events_2025-06-11.csv
 * │   │   │       ├── e387e38700000001_3004004_number-learning-events_2025-06-14.csv
 * │   │   │       ├── e387e38700000001_3004004_number-learning-events_2025-06-18.csv
 * │   │   │       └── e387e38700000001_3004004_number-learning-events_2025-06-20.csv
 * │   │   ├── android-id-e387e38700000002
 * │   │   │   └── number-learning-events
 * │   │   │       ├── e387e38700000002_3004004_number-learning-events_2025-06-10.csv
 * │   │   │       ├── e387e38700000002_3004004_number-learning-events_2025-06-11.csv
 * </pre>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NumberLearningEventImportScheduler {

  private final NumberLearningEventDao numberLearningEventDao;
  private final NumberDao numberDao;
  private final StudentDao studentDao;

  @Scheduled(cron = "00 30 * * * *") // 30 minutes past every hour
  public synchronized void execute() {
    log.info("execute");

    try {
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
            Long studentId = null;
            Integer eventImportCount = 0;
            if (androidIdDirFile.getName().equals("number-learning-events")) {
              File numberLearningEventsDir = new File(androidIdDir, androidIdDirFile.getName());
              for (File csvFile : numberLearningEventsDir.listFiles()) {
                log.info("csvFile: " + csvFile);

                // Convert from CSV to Java
                List<NumberLearningEvent> events = CsvAnalyticsExtractionHelper.extractNumberLearningEvents(csvFile);
                log.info("events.size(): " + events.size());

                // Store in database
                for (NumberLearningEvent event : events) {
                  // Check if the event has already been stored in the database
                  NumberLearningEvent existingNumberLearningEvent = numberLearningEventDao.read(event.getTimestamp(), event.getAndroidId(), event.getPackageName());
                  if (existingNumberLearningEvent != null) {
                    log.warn("The event has already been stored in the database. Skipping data import.");
                    continue;
                  }

                  // Generate Student ID
                  Student existingStudent = studentDao.read(event.getAndroidId());
                  if (existingStudent == null) {
                    Student student = new Student();
                    student.setAndroidId(event.getAndroidId());
                    studentDao.create(student);
                    log.info("Stored Student in database with ID " + student.getId());
                    studentId = student.getId();
                  } else {
                    studentId = existingStudent.getId();
                  }

                  // If content ID has been provided, look for match in the database
                  if (event.getNumberId() != null) {
                    event.setNumber(numberDao.read(event.getNumberId()));
                  }

                  // Store the event in the database
                  numberLearningEventDao.create(event);
                  log.info("Stored event in database with ID " + event.getId());
                  eventImportCount++;
                }
              }
            }
            if ((studentId != null) && (eventImportCount > 0)) {
              String contentUrl = DomainHelper.getBaseUrl() + "/analytics/students/" + studentId;
              DiscordHelper.postToChannel(Channel.ANALYTICS, "Imported " + eventImportCount + " number learning events: " + contentUrl);
            }
          }
        }
      }
    } catch (Exception e) {
      log.error("Error during data import:", e);
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Error during import of number learning events: `" + e.getClass() + ": " + e.getMessage() + "`");
    }

    log.info("execute complete");
  }
}
