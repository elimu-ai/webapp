package ai.elimu.web.analytics.students;

import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.entity.analytics.NumberLearningEvent;
import ai.elimu.entity.analytics.students.Student;
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
@RequestMapping("/analytics/students/{studentId}/number-learning-events.csv")
@RequiredArgsConstructor
@Slf4j
public class NumberLearningEventsCsvExportController {

  private final StudentDao studentDao;

  private final NumberLearningEventDao numberLearningEventDao;

  @GetMapping
  public void handleRequest(
      @PathVariable Long studentId,
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    Student student = studentDao.read(studentId);
    log.info("student.getAndroidId(): " + student.getAndroidId());

    List<NumberLearningEvent> numberLearningEvents = numberLearningEventDao.readAll(student.getAndroidId());
    log.info("numberLearningEvents.size(): " + numberLearningEvents.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(
            "id",
            "timestamp",
            "package_name",
            "additional_data",
            "number_value",
            "number_symbol",
            "number_id",
            "learning_event_type"
        )
        .build();

    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (NumberLearningEvent numberLearningEvent : numberLearningEvents) {
      log.info("numberLearningEvent.getId(): " + numberLearningEvent.getId());

      csvPrinter.printRecord(
          numberLearningEvent.getId(),
          numberLearningEvent.getTimestamp().getTimeInMillis() / 1_000,
          numberLearningEvent.getPackageName(),
          numberLearningEvent.getAdditionalData(),
          numberLearningEvent.getNumberValue(),
          numberLearningEvent.getNumberSymbol(),
          numberLearningEvent.getNumberId(),
          numberLearningEvent.getLearningEventType()
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
