package org.literacyapp.web.content.multimedia.audio;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.dao.AudioDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.multimedia.Audio;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.ContentLicense;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.content.AudioFormat;
import org.literacyapp.model.enums.Team;
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
@RequestMapping("/content/multimedia/audio/edit")
public class AudioEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private ContentCreationEventDao contentCreationEventDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Audio audio = audioDao.read(id);
        model.addAttribute("audio", audio);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        model.addAttribute("contentCreationEvents", contentCreationEventDao.readAll(audio));

        return "content/multimedia/audio/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            Audio audio,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (StringUtils.isBlank(audio.getTranscription())) {
            result.rejectValue("transcription", "NotNull");
        } else {
            Audio existingAudio = audioDao.read(audio.getTranscription(), audio.getLocale());
            if ((existingAudio != null) && !existingAudio.getId().equals(audio.getId())) {
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
            model.addAttribute("audio", audio);
            model.addAttribute("contentLicenses", ContentLicense.values());
            model.addAttribute("contentCreationEvents", contentCreationEventDao.readAll(audio));
            return "content/multimedia/audio/edit";
        } else {
            audio.setTranscription(audio.getTranscription().toLowerCase());
            audio.setTimeLastUpdate(Calendar.getInstance());
            audio.setRevisionNumber(Integer.MIN_VALUE);
            audioDao.update(audio);
            
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            
            ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
            contentCreationEvent.setContributor(contributor);
            contentCreationEvent.setContent(audio);
            contentCreationEvent.setCalendar(Calendar.getInstance());
            contentCreationEventDao.update(contentCreationEvent);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just edited an Audio:\n" + 
                        "• Language: \"" + audio.getLocale().getLanguage() + "\"\n" + 
                        "• Transcription: \"" + audio.getTranscription() + "\"\n" + 
                        "• Audio format: \"" + audio.getAudioFormat() + "\"\n" + 
                        "See ") + "http://literacyapp.org/content/multimedia/audio/list";
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, "http://literacyapp.org/audio/" + audio.getId() + "." + audio.getAudioFormat().toString().toLowerCase());
            }
            
            return "redirect:/content/multimedia/audio/list";
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
