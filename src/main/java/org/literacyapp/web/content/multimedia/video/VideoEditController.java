package org.literacyapp.web.content.multimedia.video;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.dao.VideoDao;
import org.literacyapp.dao.VideoRevisionEventDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.content.Number;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.content.multimedia.Video;
import org.literacyapp.model.enums.ContentLicense;
import org.literacyapp.model.enums.content.VideoFormat;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;
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
@RequestMapping("/content/multimedia/video/edit")
public class VideoEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private VideoDao videoDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private VideoRevisionEventDao videoRevisionEventDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Video video = videoDao.read(id);
        model.addAttribute("video", video);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());
        
        model.addAttribute("videoRevisionEvents", videoRevisionEventDao.readAll(video));
        
        model.addAttribute("letters", letterDao.readAllOrdered(contributor.getLocale()));
        model.addAttribute("numbers", numberDao.readAllOrdered(contributor.getLocale()));
        model.addAttribute("words", wordDao.readAllOrdered(contributor.getLocale()));

        return "content/multimedia/video/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            Video video,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (StringUtils.isBlank(video.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Video existingVideo = videoDao.read(video.getTitle(), video.getLocale());
            if ((existingVideo != null) && !existingVideo.getId().equals(video.getId())) {
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
                if (originalFileName.toLowerCase().endsWith(".m4v")) {
                    video.setVideoFormat(VideoFormat.M4V);
                } else if (originalFileName.toLowerCase().endsWith(".mp4")) {
                    video.setVideoFormat(VideoFormat.MP4);
                } else {
                    result.rejectValue("bytes", "typeMismatch");
                }

                if (video.getVideoFormat() != null) {
                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    video.setContentType(contentType);

                    video.setBytes(bytes);

                    // TODO: convert to a default video format?
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("video", video);
            model.addAttribute("contentLicenses", ContentLicense.values());
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            model.addAttribute("videoRevisionEvents", videoRevisionEventDao.readAll(video));
            model.addAttribute("letters", letterDao.readAllOrdered(contributor.getLocale()));
            model.addAttribute("numbers", numberDao.readAllOrdered(contributor.getLocale()));
            model.addAttribute("words", wordDao.readAllOrdered(contributor.getLocale()));
            return "content/multimedia/video/edit";
        } else {
            video.setTitle(video.getTitle().toLowerCase());
            video.setTimeLastUpdate(Calendar.getInstance());
            video.setRevisionNumber(video.getRevisionNumber() + 1);
            videoDao.update(video);
            
            return "redirect:/content/multimedia/video/list#" + video.getId();
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
        Video video = videoDao.read(id);
        
//        String letterIdParameter = request.getParameter("letterId");
//        logger.info("letterIdParameter: " + letterIdParameter);
//        if (StringUtils.isNotBlank(letterIdParameter)) {
//            Long letterId = Long.valueOf(letterIdParameter);
//            Letter letter = letterDao.read(letterId);
//            List<Letter> letters = new ArrayList<>();
//            boolean isLetterAlreadyAdded = false;
//            if ((video.getLetters() != null) && !video.getLetters().isEmpty()) {
//                for (Letter existingLetter : video.getLetters()) {
//                    letters.add(existingLetter);
//                    if (letterId.equals(existingLetter.getId())) {
//                        isLetterAlreadyAdded = true;
//                    }
//                }
//            }
//            if (!isLetterAlreadyAdded) {
//                letters.add(letter);
//            }
//            video.setLetters(letters);
//            videoDao.update(video);
//        }
//        
//        String numberIdParameter = request.getParameter("numberId");
//        logger.info("numberIdParameter: " + numberIdParameter);
//        if (StringUtils.isNotBlank(numberIdParameter)) {
//            Long numberId = Long.valueOf(numberIdParameter);
//            org.literacyapp.model.content.Number number = numberDao.read(numberId);
//            List<org.literacyapp.model.content.Number> numbers = new ArrayList<>();
//            boolean isNumberAlreadyAdded = false;
//            if ((video.getNumbers() != null) && !video.getNumbers().isEmpty()) {
//                for (org.literacyapp.model.content.Number existingNumber : video.getNumbers()) {
//                    numbers.add(existingNumber);
//                    if (numberId.equals(existingNumber.getId())) {
//                        isNumberAlreadyAdded = true;
//                    }
//                }
//            }
//            if (!isNumberAlreadyAdded) {
//                numbers.add(number);
//            }
//            video.setNumbers(numbers);
//            videoDao.update(video);
//        }
//        
//        String wordIdParameter = request.getParameter("wordId");
//        logger.info("wordIdParameter: " + wordIdParameter);
//        if (StringUtils.isNotBlank(wordIdParameter)) {
//            Long wordId = Long.valueOf(wordIdParameter);
//            Word word = wordDao.read(wordId);
//            List<Word> words = new ArrayList<>();
//            boolean isWordAlreadyAdded = false;
//            if ((video.getWords() != null) && !video.getWords().isEmpty()) {
//                for (Word existingWord : video.getWords()) {
//                    words.add(existingWord);
//                    if (wordId.equals(existingWord.getId())) {
//                        isWordAlreadyAdded = true;
//                    }
//                }
//            }
//            if (!isWordAlreadyAdded) {
//                words.add(word);
//            }
//            video.setWords(words);
//            videoDao.update(video);
//        }
        
        return "success";
    }
    
    @RequestMapping(value = "/{id}/remove-content-label", method = RequestMethod.POST)
    @ResponseBody
    public String handleRemoveContentLabelRequest(
            HttpServletRequest request,
            @PathVariable Long id) {
    	logger.info("handleRemoveContentLabelRequest");
        
        logger.info("id: " + id);
        Video video = videoDao.read(id);
        
//        String letterIdParameter = request.getParameter("letterId");
//        logger.info("letterIdParameter: " + letterIdParameter);
//        if (StringUtils.isNotBlank(letterIdParameter)) {
//            List<Letter> letters = null;
//            if ((video.getLetters() != null) && !video.getLetters().isEmpty()) {
//                if (video.getLetters().size() > 1) {
//                    letters = new ArrayList<>();
//                    Long letterId = Long.valueOf(letterIdParameter);
//                    for (Letter existingLetter : video.getLetters()) {
//                        if (!letterId.equals(existingLetter.getId())) {
//                            letters.add(existingLetter);
//                        }
//                    }
//                }
//            }
//            
//            video.setLetters(letters);
//            videoDao.update(video);
//        }
//        
//        String numberIdParameter = request.getParameter("numberId");
//        logger.info("numberIdParameter: " + numberIdParameter);
//        if (StringUtils.isNotBlank(numberIdParameter)) {
//            List<Number> numbers = null;
//            if ((video.getNumbers() != null) && !video.getNumbers().isEmpty()) {
//                if (video.getNumbers().size() > 1) {
//                    numbers = new ArrayList<>();
//                    Long numberId = Long.valueOf(numberIdParameter);
//                    for (Number existingNumber : video.getNumbers()) {
//                        if (!numberId.equals(existingNumber.getId())) {
//                            numbers.add(existingNumber);
//                        }
//                    }
//                }
//            }
//            
//            video.setNumbers(numbers);
//            videoDao.update(video);
//        }
//        
//        String wordIdParameter = request.getParameter("wordId");
//        logger.info("wordIdParameter: " + wordIdParameter);
//        if (StringUtils.isNotBlank(wordIdParameter)) {
//            List<Word> words = null;
//            if ((video.getWords() != null) && !video.getWords().isEmpty()) {
//                if (video.getWords().size() > 1) {
//                    words = new ArrayList<>();
//                    Long wordId = Long.valueOf(wordIdParameter);
//                    for (Word existingWord : video.getWords()) {
//                        if (!wordId.equals(existingWord.getId())) {
//                            words.add(existingWord);
//                        }
//                    }
//                }
//            }
//            
//            video.setWords(words);
//            videoDao.update(video);
//        }
        
        return "success";
    }
}
