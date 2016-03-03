package org.literacyapp.web.content.image;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.Image;
import org.literacyapp.model.enums.ImageType;
import org.literacyapp.model.enums.Language;
import org.literacyapp.util.ImageColorHelper;
import org.literacyapp.util.ImageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/image/create")
public class ImageCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Image image = new Image();
        model.addAttribute("image", image);
        
        model.addAttribute("languages", Language.values());
        model.addAttribute("imageTypes", ImageType.values());

        return "content/image/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (!multipartFile.isEmpty()) {
            try {
                byte[] bytes = multipartFile.getBytes();
                if (image.getBytes() != null) {
                    String originalFileName = multipartFile.getOriginalFilename();
                    logger.info("originalFileName: " + originalFileName);
                    if (originalFileName.toLowerCase().endsWith(".png")) {
                        image.setImageType(ImageType.PNG);
                    } else if (originalFileName.toLowerCase().endsWith(".jpg") || originalFileName.toLowerCase().endsWith(".jpeg")) {
                        image.setImageType(ImageType.JPG);
                    } else if (originalFileName.toLowerCase().endsWith(".gif")) {
                        image.setImageType(ImageType.GIF);
                    }

                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    image.setContentType(contentType);
                    
                    image.setBytes(bytes);
                    
                    if (image.getImageType() != ImageType.GIF) {
                        int width = ImageHelper.getWidth(bytes);
                        logger.info("width: " + width + "px");

                        if (width < ImageHelper.MINIMUM_WIDTH) {
                            result.rejectValue("bytes", "image.too.small");
                            image.setBytes(null);
                        } else {
                            if (width > ImageHelper.MINIMUM_WIDTH) {
                                bytes = ImageHelper.scaleImage(bytes, ImageHelper.MINIMUM_WIDTH);
                                image.setBytes(bytes);
                            }
                        }
                    }
                } else {
                    result.rejectValue("bytes", "required");
                }
            } catch (IOException e) {
                logger.error(e);
            }
    	}
        
        if (result.hasErrors()) {
            model.addAttribute("languages", Language.values());
            model.addAttribute("imageTypes", ImageType.values());
            return "content/image/create";
        } else {
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            image.setContributor(contributor);
            image.setCalendar(Calendar.getInstance());
            int[] dominantColor = ImageColorHelper.getDominantColor(image.getBytes());
            image.setDominantColor(dominantColor);
            imageDao.create(image);
            
            // TODO: store event
            
            return "redirect:/content";
        }
    }
    
    /**
     * See http://www.mkyong.com/spring-mvc/spring-mvc-failed-to-convert-property-value-in-file-upload-form/
     * <p></p>
     * Fixes this error message:
     * "Cannot convert value of type [org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile] to required type [byte] for property 'bytes[0]'"
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws ServletException {
    	logger.info("initBinder");
    	binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }
}
