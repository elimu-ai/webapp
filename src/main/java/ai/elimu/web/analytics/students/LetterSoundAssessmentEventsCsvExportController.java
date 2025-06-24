package ai.elimu.web.analytics.students;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;
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
@RequestMapping("/analytics/students/{studentId}/letter-sound-assessment-events.csv")
@RequiredArgsConstructor
@Slf4j
public class LetterSoundAssessmentEventsCsvExportController {

  private final StudentDao studentDao;

  private final LetterSoundAssessmentEventDao letterSoundAssessmentEventDao;

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

      List<LetterSoundAssessmentEvent> letterSoundAssessmentEvents = letterSoundAssessmentEventDao.readAll(student.getAndroidId());
      log.info("letterSoundAssessmentEvents.size(): " + letterSoundAssessmentEvents.size());

      CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
          .setHeader(
              "id",
              "timestamp",
              "package_name",
              "mastery_score",
              "time_spent_ms",
              "additional_data",
              "research_experiment",
              "experiment_group",
              "letter_sound_letters",
              "letter_sound_sounds",
              "letter_sound_id"
          ).build();
      StringWriter stringWriter = new StringWriter();
      CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
      for (LetterSoundAssessmentEvent event : letterSoundAssessmentEvents) {
        log.info("event.getId(): " + event.getId());
        csvPrinter.printRecord(
            event.getId(),
            event.getTimestamp().getTimeInMillis() / 1_000,
            event.getPackageName(),
            event.getMasteryScore(),
            event.getTimeSpentMs(),
            event.getAdditionalData(),
            (event.getResearchExperiment() != null) ? event.getResearchExperiment().ordinal() : null,
            (event.getExperimentGroup() != null) ? event.getExperimentGroup().ordinal() : null,
            event.getLetterSoundLetters(),
            event.getLetterSoundSounds(),
            event.getLetterSoundId()
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
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Error during CSV export of letter-sound assessment events: `" + ex.getClass() + ": " + ex.getMessage() + "`");
    }
  }
}
