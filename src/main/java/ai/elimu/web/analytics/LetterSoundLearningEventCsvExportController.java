package ai.elimu.web.analytics;

import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
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
@RequestMapping("/analytics/letter-sound-learning-event/list/letter-sound-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class LetterSoundLearningEventCsvExportController {

  private final LetterSoundLearningEventDao letterSoundLearningEventDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<LetterSoundLearningEvent> letterSoundLearningEvents = letterSoundLearningEventDao.readAll();
    log.info("letterSoundLearningEvents.size(): " + letterSoundLearningEvents.size());
    for (LetterSoundLearningEvent letterSoundLearningEvent : letterSoundLearningEvents) {
      letterSoundLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(letterSoundLearningEvent.getAndroidId()));
    }

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(
            "id", // The Room database ID
            "timestamp",
            "android_id",
            "package_name",
            "letter_sound_id",
            "letter_sound_letters",
            "letter_sound_sounds",
            "learning_event_type",
            "additional_data"
        )
        .build();

    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (LetterSoundLearningEvent letterSoundLearningEvent : letterSoundLearningEvents) {
      log.info("letterSoundLearningEvent.getId(): " + letterSoundLearningEvent.getId());

      csvPrinter.printRecord(
          letterSoundLearningEvent.getId(),
          letterSoundLearningEvent.getTimestamp().getTimeInMillis(),
          letterSoundLearningEvent.getAndroidId(),
          letterSoundLearningEvent.getPackageName(),
          letterSoundLearningEvent.getLetterSoundId(),
          new String[] {}, // TODO
          new String[] {}, // TODO
          letterSoundLearningEvent.getLearningEventType(),
          letterSoundLearningEvent.getAdditionalData()
      );
    }
    csvPrinter.flush();
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
