package ai.elimu.tasks.analytics;

import ai.elimu.dao.StudentDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.analytics.LetterSoundLearningEventsRestController;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.csv.CsvAnalyticsExtractionHelper;
import java.io.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Extracts learning events from CSV files previously received by the {@link LetterSoundLearningEventsRestController}, and imports them into the database.
 * <p/>
 * <p>
 * Expected folder structure:
 * <pre>
 * ├── lang-ENG
 * │   ├── analytics
 * │   │   ├── android-id-e387e38700000001
 * │   │   │   └── version-code-3001018
 * │   │   │       └── letter-sound-learning-events
 * │   │   │           ├── e387e38700000001_3001018_letter-sound-learning-events_2024-10-09.csv
 * │   │   │           ├── e387e38700000001_3001018_letter-sound-learning-events_2024-10-10.csv
 * │   │   │           ├── e387e38700000001_3001018_letter-sound-learning-events_2024-10-11.csv
 * │   │   │           ├── e387e38700000001_3001018_letter-sound-learning-events_2024-10-14.csv
 * │   │   │           ├── e387e38700000001_3001018_letter-sound-learning-events_2024-10-18.csv
 * │   │   │           └── e387e38700000001_3001018_letter-sound-learning-events_2024-10-20.csv
 * │   │   ├── android-id-e387e38700000002
 * │   │   │   └── version-code-3001018
 * │   │   │       └── letter-sound-learning-events
 * │   │   │           ├── e387e38700000002_3001018_letter-sound-learning-events_2024-10-09.csv
 * │   │   │           ├── e387e38700000002_3001018_letter-sound-learning-events_2024-10-10.csv
 * │   │   │           ├── e387e38700000002_3001018_letter-sound-learning-events_2024-10-11.csv
 * </pre>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LetterSoundLearningEventImportScheduler {

  private final LetterSoundLearningEventDao letterSoundLearningEventDao;

  private final StudentDao studentDao;

  @Scheduled(cron = "00 20 * * * *") // 20 minutes past every hour
  public synchronized void execute() {
    log.info("execute");

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
          if (androidIdDirFile.getName().startsWith("version-code-")) {
            File versionCodeDir = new File(androidIdDir, androidIdDirFile.getName());
            for (File versionCodeDirFile : versionCodeDir.listFiles()) {
              if (versionCodeDirFile.getName().equals("letter-sound-learning-events")) {
                File letterSoundLearningEventsDir = new File(versionCodeDir, versionCodeDirFile.getName());
                for (File csvFile : letterSoundLearningEventsDir.listFiles()) {
                  log.info("csvFile: " + csvFile);

                  // Convert from CSV to Java
                  List<LetterSoundLearningEvent> events = CsvAnalyticsExtractionHelper.extractLetterSoundLearningEvents(csvFile);
                  log.info("events.size(): " + events.size());

                  // Store in database
                  for (LetterSoundLearningEvent event : events) {
                    // Check if the event has already been stored in the database
                    LetterSoundLearningEvent existingLetterSoundLearningEvent = letterSoundLearningEventDao.read(event.getTimestamp(), event.getAndroidId(), event.getPackageName());
                    if (existingLetterSoundLearningEvent != null) {
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
                    }

                    // Store the event in the database
                    letterSoundLearningEventDao.create(event);
                    log.info("Stored event in database with ID " + event.getId());
                  }
                }
              }
            }
          }
        }
      }
    }

    log.info("execute complete");
  }
}
