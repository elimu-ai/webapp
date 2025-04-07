package ai.elimu.web.analytics;

import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.enums.OrderDirection;
import ai.elimu.entity.analytics.VideoLearningEvent;
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
@RequestMapping("/analytics/video-learning-event/list/video-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class VideoLearningEventCsvExportController {

  private final VideoLearningEventDao videoLearningEventDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAllOrderedByTimestamp(OrderDirection.ASC);
    log.info("videoLearningEvents.size(): " + videoLearningEvents.size());
    for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
      videoLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(videoLearningEvent.getAndroidId()));
    }

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(
            "id", // The Room database ID
            "timestamp",
            "android_id",
            "package_name",
            "video_id",
            "video_title",
            "learning_event_type",
            "additional_data"
        )
        .build();

    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
      log.info("videoLearningEvent.getId(): " + videoLearningEvent.getId());

      videoLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(videoLearningEvent.getAndroidId()));

      csvPrinter.printRecord(
          videoLearningEvent.getId(),
          videoLearningEvent.getTimestamp().getTimeInMillis(),
          videoLearningEvent.getAndroidId(),
          videoLearningEvent.getPackageName(),
          videoLearningEvent.getVideoId(),
          videoLearningEvent.getVideoTitle(),
          videoLearningEvent.getLearningEventType(),
          videoLearningEvent.getAdditionalData()
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
