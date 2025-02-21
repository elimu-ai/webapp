package ai.elimu.web.download;

import ai.elimu.dao.VideoDao;
import ai.elimu.model.content.multimedia.Video;
import jakarta.servlet.http.HttpServletResponse;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

  private final Logger logger = LogManager.getLogger();

  private final VideoDao videoDao;

  @RequestMapping(value = "/{videoId}_r{revisionNumber}.{videoFormat}", method = RequestMethod.GET)
  public void handleRequest(
      Model model,
      @PathVariable Long videoId,
      @PathVariable Integer revisionNumber,
      @PathVariable String videoFormat,
      HttpServletResponse response,
      OutputStream outputStream) {
    logger.info("handleRequest");

    logger.info("videoId: " + videoId);
    logger.info("revisionNumber: " + revisionNumber);
    logger.info("videoFormat: " + videoFormat);

    Video video = videoDao.read(videoId);

    response.setContentType(video.getContentType());

    byte[] bytes = video.getBytes();
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

  @RequestMapping(value = "/{videoId}_r{revisionNumber}_thumbnail.png", method = RequestMethod.GET)
  public void handleThumbnailRequest(
      Model model,
      @PathVariable Long videoId,
      @PathVariable Integer revisionNumber,
      HttpServletResponse response,
      OutputStream outputStream) {
    logger.info("handleThumbnailRequest");

    logger.info("videoId: " + videoId);
    logger.info("revisionNumber: " + revisionNumber);

    Video video = videoDao.read(videoId);

    response.setContentType("image/png");

    byte[] bytes = video.getThumbnail();
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
