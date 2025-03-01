package ai.elimu.web.download;

import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import jakarta.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import org.apache.logging.log4j.LogManager;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/image/{imageId}_r{revisionNumber}.{imageFormat}")
@RequiredArgsConstructor
@Slf4j
public class ImageController {

  private final ImageDao imageDao;

  @GetMapping
  public void handleRequest(
      Model model,
      @PathVariable Long imageId,
      @PathVariable Integer revisionNumber,
      @PathVariable String imageFormat,
      HttpServletResponse response,
      OutputStream outputStream) {
    log.info("handleRequest");

    log.info("imageId: " + imageId);
    log.info("revisionNumber: " + revisionNumber);
    log.info("imageFormat: " + imageFormat);

    Image image = imageDao.read(imageId);

    response.setContentType(image.getContentType());

    byte[] bytes = image.getBytes();
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
