package ai.elimu.web.download;

import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import jakarta.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/audio")
@RequiredArgsConstructor
@Slf4j
public class AudioController {

  private final AudioDao audioDao;

  @RequestMapping(value = "/{audioId}_r{revisionNumber}.{audioFormat}", method = RequestMethod.GET)
  public void handleRequest(
      Model model,
      @PathVariable Long audioId,
      @PathVariable Integer revisionNumber,
      @PathVariable String audioFormat,
      HttpServletResponse response,
      OutputStream outputStream) {
    log.info("handleRequest");

    log.info("audioId: " + audioId);
    log.info("revisionNumber: " + revisionNumber);
    log.info("audioFormat: " + audioFormat);

    Audio audio = audioDao.read(audioId);

    response.setContentType(audio.getContentType());

    byte[] bytes = audio.getBytes();
    response.setContentLength(bytes.length);
    try {
      outputStream.write(bytes);
    } catch (EOFException ex) {
      // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
      log.warn(ex.getMessage());
    } catch (IOException ex) {
      log.error(ex.getMessage());
    } finally {
      try {
        try {
          outputStream.flush();
          outputStream.close();
        } catch (EOFException ex) {
          // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
          log.warn(ex.getMessage());
        }
      } catch (IOException ex) {
        log.error(ex.getMessage());
      }
    }
  }
}
