package ai.elimu.web.download;

import ai.elimu.dao.ImageDao;
import ai.elimu.model.content.multimedia.Image;
import jakarta.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class ImageController {

  private final Logger logger = LogManager.getLogger();

  private final ImageDao imageDao;

  @GetMapping
  public void handleRequest(
      Model model,
      @PathVariable Long imageId,
      @PathVariable Integer revisionNumber,
      @PathVariable String imageFormat,
      HttpServletResponse response,
      OutputStream outputStream) {
    logger.info("handleRequest");

    logger.info("imageId: " + imageId);
    logger.info("revisionNumber: " + revisionNumber);
    logger.info("imageFormat: " + imageFormat);

    Image image = imageDao.read(imageId);

    response.setContentType(image.getContentType());

    byte[] bytes = image.getBytes();
    response.setContentLength(bytes.length);
    try {
      outputStream.write(bytes);
    } catch (EOFException ex) {
      // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
      logger.warn(ex);
    } catch (IOException ex) {
      logger.error(ex);
    } finally {
      try {
        try {
          outputStream.flush();
          outputStream.close();
        } catch (EOFException ex) {
          // org.eclipse.jetty.io.EofException (occurs when download is aborted before completion)
          logger.warn(ex);
        }
      } catch (IOException ex) {
        logger.error(ex);
      }
    }
  }
}
