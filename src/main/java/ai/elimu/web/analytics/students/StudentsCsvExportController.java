package ai.elimu.web.analytics.students;

import ai.elimu.dao.StudentDao;
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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/students/students.csv")
@RequiredArgsConstructor
@Slf4j
public class StudentsCsvExportController {

  private final StudentDao studentDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<Student> students = studentDao.readAll();
    log.info("students.size(): " + students.size());
    for (Student student : students) {
      student.setAndroidId(AnalyticsHelper.redactAndroidId(student.getAndroidId()));
    }

    CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
        .setHeader(
            "id",
            "android_id"
        )
        .build();

    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Student student : students) {
      log.info("student.getId(): " + student.getId());

      csvPrinter.printRecord(
          student.getId(),
          student.getAndroidId()
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
