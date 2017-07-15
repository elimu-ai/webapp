package ai.elimu.web;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import ai.elimu.dao.VideoDao;
import ai.elimu.model.content.multimedia.Video;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/video")
public class VideoController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private VideoDao videoDao;
    
    @RequestMapping(value="/{videoId}.{videoFormat}", method = RequestMethod.GET)
    public void handleRequest(
            Model model,
            @PathVariable Long videoId,
            @PathVariable String videoFormat,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("videoId: " + videoId);
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
            logger.error(null, ex);
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
                logger.error(null, ex);
            }
        }
    }
    
    @RequestMapping(value="/{videoId}/thumbnail.png", method = RequestMethod.GET)
    public void handleThumbnailRequest(
            Model model,
            @PathVariable Long videoId,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleThumbnailRequest");
        
        logger.info("videoId: " + videoId);
        
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
            logger.error(null, ex);
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
                logger.error(null, ex);
            }
        }
    }
}
