package ai.elimu.web.analytics.students;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.students.Student;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/students/{studentId}/storybook-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class StoryBookLearningEventsCsvExportController {

  private final StudentDao studentDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  @GetMapping
  public void handleRequest(
      @PathVariable Long studentId,
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    Student student = studentDao.read(studentId);
    log.info("student.getAndroidId(): " + student.getAndroidId());

    List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAll(student.getAndroidId());
    log.info("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
    for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
      storyBookLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(storyBookLearningEvent.getAndroidId()));
    }

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(
            "id",
            "timestamp",
            "package_name",
            "storybook_title",
            "storybook_id",
            "learning_event_type",
            "additional_data"
        )
        .build();

    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
      log.info("storyBookLearningEvent.getId(): " + storyBookLearningEvent.getId());

      csvPrinter.printRecord(
          storyBookLearningEvent.getId(),
          storyBookLearningEvent.getTimestamp().getTimeInMillis(),
          storyBookLearningEvent.getPackageName(),
          storyBookLearningEvent.getStoryBookTitle(),
          storyBookLearningEvent.getStoryBookId(),
          storyBookLearningEvent.getLearningEventType(),
          storyBookLearningEvent.getAdditionalData()
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
