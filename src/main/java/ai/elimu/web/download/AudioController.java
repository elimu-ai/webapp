package ai.elimu.web.download;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import org.apache.logging.log4j.LogManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/audio")
public class AudioController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private AudioDao audioDao;
    
    @RequestMapping(value="/{audioId}_r{revisionNumber}.{audioFormat}", method = RequestMethod.GET)
    public void handleRequest(
            Model model,
            @PathVariable Long audioId,
            @PathVariable Integer revisionNumber,
            @PathVariable String audioFormat,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("audioId: " + audioId);
        logger.info("revisionNumber: " + revisionNumber);
        logger.info("audioFormat: " + audioFormat);
        
        Audio audio = audioDao.read(audioId);
        
        response.setContentType(audio.getContentType());
        
        byte[] bytes = audio.getBytes();
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
