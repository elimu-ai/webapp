package ai.elimu.web.content.multimedia.audio;

import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/content/audio/list/audios.csv")
@RequiredArgsConstructor
@Slf4j
public class AudioCsvExportController {

  private final AudioDao audioDao;

  @GetMapping
  public void handleRequest(
      HttpServletResponse response,
      OutputStream outputStream) {
    log.info("handleRequest");

    // Generate CSV file
    String csvFileContent = "id,content_type,content_license,attribution_url,word_id,title,transcription,download_url,audio_format" + "\n";
    List<Audio> audios = audioDao.readAll();
    log.info("audios.size(): " + audios.size());
    for (Audio audio : audios) {
      String downloadUrl = "/audio/" + audio.getId() + "." + audio.getAudioFormat().toString().toLowerCase();
      csvFileContent += audio.getId() + ","
          + audio.getContentType() + ","
          + audio.getContentLicense() + ","
          + "\"" + audio.getAttributionUrl() + "\","
          + ((audio.getWord() != null) ? audio.getWord().getId() : "") + ","
          + "\"" + audio.getTitle() + "\","
          + "\"" + audio.getTranscription() + "\","
          + "\"" + downloadUrl + "\","
          + audio.getAudioFormat() + "\n";
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
