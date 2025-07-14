package ai.elimu.web.content.syllable;

import ai.elimu.dao.SyllableDao;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.content.Syllable;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/syllable/list/syllables.csv")
@RequiredArgsConstructor
@Slf4j
public class SyllableCsvExportController {

  private final SyllableDao syllableDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<Syllable> syllables = syllableDao.readAllOrderedByUsage();
    log.info("syllables.size(): " + syllables.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "text",
            "sound_ids",
            "usage_count"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
    
    for (Syllable syllable : syllables) {
      long[] soundIdsArray = new long[syllable.getSounds().size()];
      int index = 0;
      for (Sound sound : syllable.getSounds()) {
        soundIdsArray[index] = sound.getId();
        index++;
      }
      
      csvPrinter.printRecord(
          syllable.getId(),
          syllable.getText(),
          Arrays.toString(soundIdsArray),
          syllable.getUsageCount()
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
