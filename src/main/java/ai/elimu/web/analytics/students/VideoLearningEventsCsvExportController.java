package ai.elimu.web.analytics.students;

import ai.elimu.dao.StudentDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.util.AnalyticsHelper;
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
@RequestMapping("/analytics/students/{studentId}/video-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class VideoLearningEventsCsvExportController {

  private final StudentDao studentDao;

  private final VideoLearningEventDao videoLearningEventDao;

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

      List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAll(student.getAndroidId());
      log.info("videoLearningEvents.size(): " + videoLearningEvents.size());
      for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
        videoLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(videoLearningEvent.getAndroidId()));
      }

      CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
          .setHeader(
              "id",
              "timestamp",
              "package_name",
              "learning_event_type",
              "additional_data",
              "research_experiment",
              "experiment_group",
              "video_title",
              "video_id"
          ).build();
      StringWriter stringWriter = new StringWriter();
      CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
      for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
        log.info("videoLearningEvent.getId(): " + videoLearningEvent.getId());
        csvPrinter.printRecord(
            videoLearningEvent.getId(),
            videoLearningEvent.getTimestamp().getTimeInMillis() / 1_000,
            videoLearningEvent.getPackageName(),
            videoLearningEvent.getLearningEventType(),
            videoLearningEvent.getAdditionalData(),
            videoLearningEvent.getResearchExperiment().ordinal(),
            videoLearningEvent.getExperimentGroup().ordinal(),
            videoLearningEvent.getVideoTitle(),
            videoLearningEvent.getVideoId()
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
      DiscordHelper.postToChannel(Channel.ANALYTICS, "Error during CSV export of video learning events: `" + ex.getClass() + ": " + ex.getMessage() + "`");
    }
  }
}
