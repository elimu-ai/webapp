package org.literacyapp.web.content.multimedia.image;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ImageDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.content.multimedia.Image;
import org.literacyapp.model.enums.ContentLicense;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.model.enums.content.ImageFormat;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;
import org.literacyapp.util.ImageColorHelper;
import org.literacyapp.util.ImageHelper;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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
@RequestMapping("/content/multimedia/image/create")
public class ImageCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Image image = new Image();
        model.addAttribute("image", image);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());

        return "content/multimedia/image/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            /*@Valid*/ Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (StringUtils.isBlank(image.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Image existingImage = imageDao.read(image.getTitle(), image.getLocale());
            if (existingImage != null) {
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
                    image.setImageFormat(ImageFormat.PNG);
                } else if (originalFileName.toLowerCase().endsWith(".jpg") || originalFileName.toLowerCase().endsWith(".jpeg")) {
                    image.setImageFormat(ImageFormat.JPG);
                } else if (originalFileName.toLowerCase().endsWith(".gif")) {
                    image.setImageFormat(ImageFormat.GIF);
                } else {
                    result.rejectValue("bytes", "typeMismatch");
                }

                if (image.getImageFormat() != null) {
                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    image.setContentType(contentType);

                    image.setBytes(bytes);

                    if (image.getImageFormat() != ImageFormat.GIF) {
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
            }
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("contentLicenses", ContentLicense.values());
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            return "content/multimedia/image/create";
        } else {
            image.setTitle(image.getTitle().toLowerCase());
            int[] dominantColor = ImageColorHelper.getDominantColor(image.getBytes());
            image.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
            image.setTimeLastUpdate(Calendar.getInstance());
            imageDao.create(image);
            
            // TODO: store RevisionEvent
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                    contributor.getFirstName() + " just added a new Image:\n" + 
                    "• Language: \"" + image.getLocale().getLanguage() + "\"\n" + 
                    "• Title: \"" + image.getTitle() + "\"\n" +
                    "See ") + "http://literacyapp.org/content/multimedia/image/edit/" + image.getId();
                String iconUrl = contributor.getImageUrl();
                String imageUrl = "http://literacyapp.org/image/" + image.getId() + "." + image.getImageFormat().toString().toLowerCase();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, imageUrl);
            }
            
            // Label Image with Word of matching title
            Word matchingWord = wordDao.readByText(contributor.getLocale(), image.getTitle());
            if (matchingWord != null) {
                Set<Word> labeledWords = new HashSet<>();
                if (!labeledWords.contains(matchingWord)) {
                    labeledWords.add(matchingWord);
                    image.setWords(labeledWords);
                    imageDao.update(image);
                }
            }
            
            return "redirect:/content/multimedia/image/list#" + image.getId();
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
