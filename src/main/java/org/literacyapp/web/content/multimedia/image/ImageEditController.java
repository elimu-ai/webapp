package org.literacyapp.web.content.multimedia.image;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.multimedia.Image;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.content.ImageType;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.ImageHelper;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/image/edit")
public class ImageEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Image image = imageDao.read(id);
        model.addAttribute("image", image);
        
        model.addAttribute("imageTypes", ImageType.values());

        return "content/multimedia/image/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isBlank(image.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Image existingImage = imageDao.read(image.getTitle(), image.getLocale());
            if ((existingImage != null) && !existingImage.getId().equals(image.getId())) {
                result.rejectValue("title", "NonUnique");
            }
        }
        
        try {
            byte[] bytes = multipartFile.getBytes();
            if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
                result.rejectValue("bytes", "NotNull");
            } else {
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
            }
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("image", image);
            return "content/multimedia/image/edit";
        } else {
            image.setTimeLastUpdate(Calendar.getInstance());
            image.setRevisionNumber(Integer.MIN_VALUE);
            imageDao.update(image);
            
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            
            ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
            contentCreationEvent.setContributor(contributor);
            contentCreationEvent.setContent(image);
            contentCreationEvent.setCalendar(Calendar.getInstance());
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just edited an Image:\n" + 
                        "• Language: \"" + image.getLocale().getLanguage() + "\"\n" + 
                        "• Title: \"" + image.getTitle() + "\"\n" + 
                        "• Image type: \"" + image.getImageType() + "\"\n" + 
                        "See ") + "http://literacyapp.org/content/image/list";
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, "http://literacyapp.org/image/" + image.getId() + "." + image.getImageType().toString().toLowerCase());
            }
            
            return "redirect:/content/multimedia/image/list";
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
