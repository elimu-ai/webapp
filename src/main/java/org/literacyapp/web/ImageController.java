package org.literacyapp.web;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.Image;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/image")
public class ImageController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @RequestMapping(value="/{imageTitle}.{imageType}", method = RequestMethod.GET)
    public void handleRequest(
            Model model,
            @PathVariable String imageTitle,
            @PathVariable String imageType,
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        logger.info("imageTitle: " + imageTitle);
        logger.info("imageType: " + imageType);
        
        Image image = imageDao.read(imageTitle);
        model.addAttribute("image", image);
        
        response.setContentType(image.getContentType());
        
        byte[] imageBytes = image.getBytes();
        try {
            outputStream.write(imageBytes);
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
