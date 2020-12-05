package ai.elimu.web.content.multimedia.audio;

import ai.elimu.dao.AudioContributionEventDao;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.content.AudioFormat;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
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
@RequestMapping("/content/multimedia/audio/create")
public class AudioCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @RequestParam(required = false) String autoFillTranscription) {
    	logger.info("handleRequest");
        
        Audio audio = new Audio();
        
        // Pre-fill the Audio's transcription (if the user arrived from /content/word/edit/{id}/)
        if (StringUtils.isNotBlank(autoFillTranscription)) {
            audio.setTranscription(autoFillTranscription);
        }
        
        model.addAttribute("audio", audio);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());
        
        model.addAttribute("timeStart", System.currentTimeMillis());

        return "content/multimedia/audio/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            /*@Valid*/ Audio audio,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isBlank(audio.getTranscription())) {
            result.rejectValue("transcription", "NotNull");
        } else {
            Audio existingAudio = audioDao.read(audio.getTranscription());
            if (existingAudio != null) {
                result.rejectValue("transcription", "NonUnique");
            }
        }
        
        try {
            byte[] bytes = multipartFile.getBytes();
            if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
                result.rejectValue("bytes", "NotNull");
            } else {
                String originalFileName = multipartFile.getOriginalFilename();
                logger.info("originalFileName: " + originalFileName);
                if (originalFileName.toLowerCase().endsWith(".mp3")) {
                    audio.setAudioFormat(AudioFormat.MP3);
                } else if (originalFileName.toLowerCase().endsWith(".ogg")) {
                    audio.setAudioFormat(AudioFormat.OGG);
                } else if (originalFileName.toLowerCase().endsWith(".wav")) {
                    audio.setAudioFormat(AudioFormat.WAV);
                } else {
                    result.rejectValue("bytes", "typeMismatch");
                }

                if (audio.getAudioFormat() != null) {
                    String contentType = multipartFile.getContentType();
                    logger.info("contentType: " + contentType);
                    audio.setContentType(contentType);

                    audio.setBytes(bytes);

                    // TODO: convert to a default audio format?
                }
            }
        } catch (IOException e) {
            logger.error(e);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("contentLicenses", ContentLicense.values());
            
            model.addAttribute("literacySkills", LiteracySkill.values());
            model.addAttribute("numeracySkills", NumeracySkill.values());
            
            model.addAttribute("timeStart", request.getParameter("timeStart"));
            
            return "content/multimedia/audio/create";
        } else {
            audio.setTranscription(audio.getTranscription().toLowerCase());
            audio.setTimeLastUpdate(Calendar.getInstance());
            audioDao.create(audio);
            
            AudioContributionEvent audioContributionEvent = new AudioContributionEvent();
            audioContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            audioContributionEvent.setTime(Calendar.getInstance());
            audioContributionEvent.setAudio(audio);
            audioContributionEvent.setRevisionNumber(audio.getRevisionNumber());
            audioContributionEvent.setComment(request.getParameter("contributionComment"));
            audioContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            audioContributionEventDao.create(audioContributionEvent);
            
            return "redirect:/content/multimedia/audio/list#" + audio.getId();
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
