package ai.elimu.web.analytics.students;

import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.WordAssessmentEvent;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/students/{studentId}/word-assessment-events.csv")
@RequiredArgsConstructor
@Slf4j
public class WordAssessmentEventsCsvExportController {

  private final StudentDao studentDao;

  private final WordAssessmentEventDao wordAssessmentEventDao;

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

      List<WordAssessmentEvent> wordAssessmentEvents = wordAssessmentEventDao.readAll(student.getAndroidId());
      log.info("wordAssessmentEvents.size(): " + wordAssessmentEvents.size());

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
              "word_text",
              "word_id"
          ).build();
      StringWriter stringWriter = new StringWriter();
      CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
      for (WordAssessmentEvent wordAssessmentEvent : wordAssessmentEvents) {
        log.info("wordAssessmentEvent.getId(): " + wordAssessmentEvent.getId());
        csvPrinter.printRecord(
            wordAssessmentEvent.getId(),
            wordAssessmentEvent.getTimestamp().getTimeInMillis() / 1_000,
            wordAssessmentEvent.getPackageName(),
            wordAssessmentEvent.getMasteryScore(),
            wordAssessmentEvent.getTimeSpentMs(),
            wordAssessmentEvent.getAdditionalData(),
            wordAssessmentEvent.getResearchExperiment().ordinal(),
            wordAssessmentEvent.getExperimentGroup().ordinal(),
            wordAssessmentEvent.getWordText(),
            wordAssessmentEvent.getWordId()
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
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Error during CSV export of word assessment events: `" + ex.getClass() + ": " + ex.getMessage() + "`");
    }
  }
}
