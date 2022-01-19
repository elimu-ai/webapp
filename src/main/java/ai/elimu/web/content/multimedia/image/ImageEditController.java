package ai.elimu.web.content.multimedia.image;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageContributionEventDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.ImageContributionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.ImageHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

@Controller
@RequestMapping("/content/multimedia/image/edit")
public class ImageEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private ImageContributionEventDao imageContributionEventDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private AudioDao audioDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Image image = imageDao.read(id);
        model.addAttribute("image", image);
        model.addAttribute("contentLicenses", ContentLicense.values());
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        model.addAttribute("imageContributionEvents", imageContributionEventDao.readAll(image));
        
        model.addAttribute("letters", letterDao.readAllOrdered());
        model.addAttribute("numbers", numberDao.readAllOrdered());
        model.addAttribute("words", wordDao.readAllOrdered());
        model.addAttribute("emojisByWordId", getEmojisByWordId());
        
        Audio audio = audioDao.readByTranscription(image.getTitle());
        model.addAttribute("audio", audio);

        return "content/multimedia/image/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            Image image,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isBlank(image.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Image existingImage = imageDao.read(image.getTitle());
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
            model.addAttribute("image", image);
            model.addAttribute("contentLicenses", ContentLicense.values());
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            model.addAttribute("imageContributionEvents", imageContributionEventDao.readAll(image));
            
            model.addAttribute("letters", letterDao.readAllOrdered());
            model.addAttribute("numbers", numberDao.readAllOrdered());
            model.addAttribute("words", wordDao.readAllOrdered());
            model.addAttribute("emojisByWordId", getEmojisByWordId());
            
            Audio audio = audioDao.readByTranscription(image.getTitle());
            model.addAttribute("audio", audio);
            
            return "content/multimedia/image/edit";
        } else {
            image.setTitle(image.getTitle().toLowerCase());
            image.setTimeLastUpdate(Calendar.getInstance());
            image.setRevisionNumber(image.getRevisionNumber() + 1);
            imageDao.update(image);
            
            ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
            imageContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            imageContributionEvent.setTime(Calendar.getInstance());
            imageContributionEvent.setImage(image);
            imageContributionEvent.setRevisionNumber(image.getRevisionNumber());
            imageContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            imageContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            imageContributionEvent.setPlatform(Platform.WEBAPP);
            imageContributionEventDao.create(imageContributionEvent);
            
            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/image/edit/" + image.getId();
            String embedThumbnailUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + image.getId() + "_r" + image.getRevisionNumber() + "." + image.getImageFormat().toString().toLowerCase();
            DiscordHelper.sendChannelMessage(
                    "Image edited: " + contentUrl, 
                    "\"" + image.getTitle() + "\"",
                    "Comment: \"" + imageContributionEvent.getComment() + "\"",
                    null,
                    embedThumbnailUrl
            );
            
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
    
    @RequestMapping(value = "/{id}/add-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleAddContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
    	logger.info("handleAddContentLabelRequest");
        
        logger.info("id: " + id);
        Image image = imageDao.read(id);
        
        String letterIdParameter = request.getParameter("letterId");
        logger.info("letterIdParameter: " + letterIdParameter);
        if (StringUtils.isNotBlank(letterIdParameter)) {
            Long letterId = Long.valueOf(letterIdParameter);
            Letter letter = letterDao.read(letterId);
            Set<Letter> letters = image.getLetters();
            if (!letters.contains(letter)) {
                letters.add(letter);
                image.setRevisionNumber(image.getRevisionNumber() + 1);
                imageDao.update(image);
            }
        }
        
        String numberIdParameter = request.getParameter("numberId");
        logger.info("numberIdParameter: " + numberIdParameter);
        if (StringUtils.isNotBlank(numberIdParameter)) {
            Long numberId = Long.valueOf(numberIdParameter);
            Number number = numberDao.read(numberId);
            Set<Number> numbers = image.getNumbers();
            if (!numbers.contains(number)) {
                numbers.add(number);
                image.setRevisionNumber(image.getRevisionNumber() + 1);
                imageDao.update(image);
            }
        }
        
        String wordIdParameter = request.getParameter("wordId");
        logger.info("wordIdParameter: " + wordIdParameter);
        if (StringUtils.isNotBlank(wordIdParameter)) {
            Long wordId = Long.valueOf(wordIdParameter);
            Word word = wordDao.read(wordId);
            Set<Word> words = image.getWords();
            if (!words.contains(word)) {
                words.add(word);
                image.setRevisionNumber(image.getRevisionNumber() + 1);
                imageDao.update(image);
            }
        }
        
        return "success";
    }
    
    @RequestMapping(value = "/{id}/remove-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleRemoveContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
    	logger.info("handleRemoveContentLabelRequest");
        
        logger.info("id: " + id);
        Image image = imageDao.read(id);
        
        String letterIdParameter = request.getParameter("letterId");
        logger.info("letterIdParameter: " + letterIdParameter);
        if (StringUtils.isNotBlank(letterIdParameter)) {
            Long letterId = Long.valueOf(letterIdParameter);
            Letter letter = letterDao.read(letterId);
            Set<Letter> letters = image.getLetters();
            Iterator<Letter> iterator = letters.iterator();
            while (iterator.hasNext()) {
                Letter existingLetter = iterator.next();
                if (existingLetter.getId().equals(letter.getId())) {
                    iterator.remove();
                }
            }
            image.setRevisionNumber(image.getRevisionNumber() + 1);
            imageDao.update(image);
        }
        
        String numberIdParameter = request.getParameter("numberId");
        logger.info("numberIdParameter: " + numberIdParameter);
        if (StringUtils.isNotBlank(numberIdParameter)) {
            Long numberId = Long.valueOf(numberIdParameter);
            Number number = numberDao.read(numberId);
            Set<Number> numbers = image.getNumbers();
            Iterator<Number> iterator = numbers.iterator();
            while (iterator.hasNext()) {
                Number existingNumber = iterator.next();
                if (existingNumber.getId().equals(number.getId())) {
                    iterator.remove();
                }
            }
            image.setRevisionNumber(image.getRevisionNumber() + 1);
            imageDao.update(image);
        }
        
        String wordIdParameter = request.getParameter("wordId");
        logger.info("wordIdParameter: " + wordIdParameter);
        if (StringUtils.isNotBlank(wordIdParameter)) {
            Long wordId = Long.valueOf(wordIdParameter);
            Word word = wordDao.read(wordId);
            Set<Word> words = image.getWords();
            Iterator<Word> iterator = words.iterator();
            while (iterator.hasNext()) {
                Word existingWord = iterator.next();
                if (existingWord.getId().equals(word.getId())) {
                    iterator.remove();
                }
            }
            image.setRevisionNumber(image.getRevisionNumber() + 1);
            imageDao.update(image);
        }
        
        return "success";
    }
    
    private Map<Long, String> getEmojisByWordId() {
        logger.info("getEmojisByWordId");
        
        Map<Long, String> emojisByWordId = new HashMap<>();
        
        for (Word word : wordDao.readAll()) {
            String emojiGlyphs = "";
            
            List<Emoji> emojis = emojiDao.readAllLabeled(word);
            for (Emoji emoji : emojis) {
                emojiGlyphs += emoji.getGlyph();
            }
            
            if (StringUtils.isNotBlank(emojiGlyphs)) {
                emojisByWordId.put(word.getId(), emojiGlyphs);
            }
        }
        
        return emojisByWordId;
    }
}
