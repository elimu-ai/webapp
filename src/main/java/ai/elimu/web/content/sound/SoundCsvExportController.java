package ai.elimu.web.content.sound;

import ai.elimu.dao.SoundDao;
import ai.elimu.model.content.Sound;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/sound/list")
@RequiredArgsConstructor
@Slf4j
public class SoundCsvExportController {

  private final SoundDao soundDao;

  @RequestMapping(value = "/sounds.csv", method = RequestMethod.GET)
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    log.info("handleRequest");

    List<Sound> sounds = soundDao.readAllOrderedById();
    log.info("sounds.size(): " + sounds.size());

    CSVFormat csvFormat = CSVFormat.DEFAULT
        .withHeader(
            "id",
            "value_ipa",
            "value_sampa",
            "audio_id",
            "diacritic",
            "sound_type",
            "usage_count"
        );
    StringWriter stringWriter = new StringWriter();
    CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);

    for (Sound sound : sounds) {
      Long audioId = null;
      if (sound.getAudio() != null) {
        audioId = sound.getAudio().getId();
      }

      csvPrinter.printRecord(sound.getId(),
          sound.getValueIpa(),
          sound.getValueSampa(),
          audioId,
          sound.isDiacritic(),
          sound.getSoundType(),
          sound.getUsageCount()
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
