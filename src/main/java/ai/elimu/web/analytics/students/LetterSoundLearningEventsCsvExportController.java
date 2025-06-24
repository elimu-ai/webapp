package ai.elimu.web.analytics.students;

import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/students/{studentId}/letter-sound-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class LetterSoundLearningEventsCsvExportController {

  private final StudentDao studentDao;

  private final LetterSoundLearningEventDao letterSoundLearningEventDao;

  @GetMapping
  public void handleRequest(
      @PathVariable Long studentId,
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    try {
      Student student = studentDao.read(studentId);
      log.info("student.getAndroidId(): " + student.getAndroidId());

      List<LetterSoundLearningEvent> letterSoundLearningEvents = letterSoundLearningEventDao.readAll(student.getAndroidId());
      log.info("letterSoundLearningEvents.size(): " + letterSoundLearningEvents.size());

      CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
          .setHeader(
              "id",
              "timestamp",
              "package_name",
              "additional_data",
              "research_experiment",
              "experiment_group",
              // "letter_sound_letters",
              // "letter_sound_sounds",
              "letter_sound_id"
          ).build();
      StringWriter stringWriter = new StringWriter();
      CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
      for (LetterSoundLearningEvent letterSoundLearningEvent : letterSoundLearningEvents) {
        log.info("letterSoundLearningEvent.getId(): " + letterSoundLearningEvent.getId());
        csvPrinter.printRecord(
            letterSoundLearningEvent.getId(),
            letterSoundLearningEvent.getTimestamp().getTimeInMillis() / 1_000,
            letterSoundLearningEvent.getPackageName(),
            letterSoundLearningEvent.getAdditionalData(),
            letterSoundLearningEvent.getResearchExperiment().ordinal(),
            letterSoundLearningEvent.getExperimentGroup().ordinal(),
            // letterSoundLearningEvent.getLetterSoundLetters(),
            // letterSoundLearningEvent.getLetterSoundSounds(),
            letterSoundLearningEvent.getLetterSoundId()
        );
      }
      csvPrinter.flush();
      csvPrinter.close();

      String csvFileContent = stringWriter.toString();
      response.setContentType("text/csv");
      byte[] bytes = csvFileContent.getBytes();
      response.setContentLength(bytes.length);

      outputStream.write(bytes);
      outputStream.flush();
      outputStream.close();
    } catch (Exception ex) {
      log.error(ex.getMessage());
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Error during CSV export of letter-sound learning events: `" + ex.getClass() + ": " + ex.getMessage() + "`");
    }
  }
}
