package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageContributionEventDao;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.ImageContributionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.ImageColorHelper;
import ai.elimu.util.ImageHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Arrays;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
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
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private ImageContributionEventDao imageContributionEventDao;
    
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
        model.addAttribute("timeStart", System.currentTimeMillis());

        return "content/multimedia/image/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            /*@Valid*/ Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isBlank(image.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Image existingImage = imageDao.read(image.getTitle());
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
                
                byte[] headerBytes = Arrays.copyOfRange(bytes, 0, 6);
                byte[] gifHeader87a = {71, 73, 70, 56, 55, 97}; // "GIF87a"
                byte[] gifHeader89a = {71, 73, 70, 56, 57, 97}; // "GIF89a"
                if (Arrays.equals(gifHeader87a, headerBytes) || Arrays.equals(gifHeader89a, headerBytes)) {
                    image.setImageFormat(ImageFormat.GIF);
                } else if (originalFileName.toLowerCase().endsWith(".png")) {
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
            model.addAttribute("timeStart", System.currentTimeMillis());
            return "content/multimedia/image/create";
        } else {
            image.setTitle(image.getTitle().toLowerCase());
            try {
                int[] dominantColor = ImageColorHelper.getDominantColor(image.getBytes());
                image.setDominantColor("rgb(" + dominantColor[0] + "," + dominantColor[1] + "," + dominantColor[2] + ")");
            } catch (NullPointerException ex) {
                // javax.imageio.IIOException: Unsupported Image Type
            }
            image.setTimeLastUpdate(Calendar.getInstance());
            imageDao.create(image);
            
            // Label Image with Word of matching title
            Word matchingWord = wordDao.readByText(image.getTitle());
            if (matchingWord != null) {
                Set<Word> labeledWords = new HashSet<>();
                if (!labeledWords.contains(matchingWord)) {
                    labeledWords.add(matchingWord);
                    image.setWords(labeledWords);
                    imageDao.update(image);
                }
            }
            
            ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
            imageContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            imageContributionEvent.setTimestamp(Calendar.getInstance());
            imageContributionEvent.setImage(image);
            imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
            imageContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            imageContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            imageContributionEventDao.create(imageContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/image/edit/" + image.getId();
                String embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + image.getId() + "_r" + image.getRevisionNumber() + "." + image.getImageFormat().toString().toLowerCase();
                DiscordHelper.sendChannelMessage(
                        "Image created: " + contentUrl, 
                        "\"" + image.getTitle() + "\"",
                        "Comment: \"" + imageContributionEvent.getComment() + "\"",
                        null,
                        embedThumbnailUrl
                );
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
