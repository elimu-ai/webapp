package ai.elimu.web.analytics;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.util.AnalyticsHelper;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/word-learning-event/list/word-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class WordLearningEventCsvExportController {

  private final WordLearningEventDao wordLearningEventDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.readAll();
    log.info("wordLearningEvents.size(): " + wordLearningEvents.size());
    for (WordLearningEvent wordLearningEvent : wordLearningEvents) {
      wordLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(wordLearningEvent.getAndroidId()));
    }

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(
            "id", // The Room database ID
            "timestamp",
            "android_id",
            "package_name",
            "word_id",
            "word_text",
            "learning_event_type",
            "additional_data"
        )
        .build();

    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (WordLearningEvent wordLearningEvent : wordLearningEvents) {
      log.info("wordLearningEvent.getId(): " + wordLearningEvent.getId());

      csvPrinter.printRecord(
          wordLearningEvent.getId(),
          wordLearningEvent.getTimestamp().getTimeInMillis(),
          wordLearningEvent.getAndroidId(),
          wordLearningEvent.getPackageName(),
          (wordLearningEvent.getWord() == null) ? null : wordLearningEvent.getWord().getId(),
          wordLearningEvent.getWordText(),
          wordLearningEvent.getLearningEventType(),
          wordLearningEvent.getAdditionalData()
      );
      csvPrinter.flush();
    }
    csvPrinter.close();

    String csvFileContent = stringWriter.toString();

    response.setContentType("text/csv");
    byte[] bytes = csvFileContent.getBytes();
    response.setContentLength(bytes.length);
    try {
      outputStream.write(bytes);
      outputStream.flush();
      outputStream.close();
    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
  }
}
