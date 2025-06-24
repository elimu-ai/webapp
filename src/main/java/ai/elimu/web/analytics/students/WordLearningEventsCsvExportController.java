package ai.elimu.web.analytics.students;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.WordLearningEvent;
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
@RequestMapping("/analytics/students/{studentId}/word-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class WordLearningEventsCsvExportController {

  private final StudentDao studentDao;

  private final WordLearningEventDao wordLearningEventDao;

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

      List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.readAll(student.getAndroidId());
      log.info("wordLearningEvents.size(): " + wordLearningEvents.size());

      CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
          .setHeader(
              "id",
              "timestamp",
              "package_name",
              "learning_event_type",
              "additional_data",
              "research_experiment",
              "experiment_group",
              "word_text",
              "word_id"
          ).build();
      StringWriter stringWriter = new StringWriter();
      CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
      for (WordLearningEvent wordLearningEvent : wordLearningEvents) {
        log.info("wordLearningEvent.getId(): " + wordLearningEvent.getId());
        csvPrinter.printRecord(
            wordLearningEvent.getId(),
            wordLearningEvent.getTimestamp().getTimeInMillis() / 1_000,
            wordLearningEvent.getPackageName(),
            wordLearningEvent.getLearningEventType(),
            wordLearningEvent.getAdditionalData(),
            wordLearningEvent.getResearchExperiment().ordinal(),
            wordLearningEvent.getExperimentGroup().ordinal(),
            wordLearningEvent.getWordText(),
            (wordLearningEvent.getWord() == null) ? null : wordLearningEvent.getWord().getId()
            // wordLearningEvent.getWordId(), https://github.com/elimu-ai/webapp/issues/2113
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
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Error during CSV export of word learning events: `" + ex.getClass() + ": " + ex.getMessage() + "`");
    }
  }
}
