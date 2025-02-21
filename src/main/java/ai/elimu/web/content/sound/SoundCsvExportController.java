package ai.elimu.web.content.sound;

import ai.elimu.dao.SoundDao;
import ai.elimu.model.content.Sound;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/sound/list")
@RequiredArgsConstructor
public class SoundCsvExportController {

  private final Logger logger = LogManager.getLogger();

  private final SoundDao soundDao;

  @GetMapping(value = "/sounds.csv")
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream
  ) throws IOException {
    logger.info("handleRequest");

    List<Sound> sounds = soundDao.readAllOrderedById();
    logger.info("sounds.size(): " + sounds.size());

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
      logger.error(ex);
    }
  }
}
