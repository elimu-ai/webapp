package ai.elimu.web.content.syllable;

import ai.elimu.dao.SyllableDao;
import ai.elimu.entity.content.Sound;
import ai.elimu.entity.content.Syllable;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
      OutputStream outputStream) {
    log.info("handleRequest");

    // Generate CSV file
    String csvFileContent = "id,text,sound_ids,usage_count" + "\n";
    List<Syllable> syllables = syllableDao.readAllOrderedByUsage();
    log.info("syllables.size(): " + syllables.size());
    for (Syllable syllable : syllables) {
      long[] soundIdsArray = new long[syllable.getSounds().size()];
      int index = 0;
      for (Sound sound : syllable.getSounds()) {
        soundIdsArray[index] = sound.getId();
        index++;
      }
      csvFileContent += syllable.getId() + ","
          + "\"" + syllable.getText() + "\","
          + Arrays.toString(soundIdsArray) + ","
          + syllable.getUsageCount() + "\n";
    }

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
