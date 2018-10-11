package ai.elimu.web.content.multimedia.audio;

import java.io.IOException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Locale;
import ai.elimu.web.content.multimedia.AbstractMultimediaEditController;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.AudioFormat;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

import java.net.URLEncoder;

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
@RequestMapping("/content/multimedia/audio/edit")
public class AudioEditController extends AbstractMultimediaEditController<Audio> {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private AudioDao audioDao;

    @Autowired
    private LetterDao letterDao;

    @Autowired
    private NumberDao numberDao;

    @Autowired
    private WordDao wordDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model,
            @PathVariable Long id) {
        logger.info("handleRequest");

        Contributor contributor = (Contributor) session.getAttribute("contributor");

        Audio audio = audioDao.read(id);
        return setModel(model, audio, contributor.getLocale());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            Audio audio,
            @RequestParam("bytes") MultipartFile multipartFile,
            BindingResult result,
            Model model) {
        logger.info("handleSubmit");

        Contributor contributor = (Contributor) session.getAttribute("contributor");

        if (StringUtils.isBlank(audio.getTranscription())) {
            result.rejectValue("transcription", "NotNull");
        } else {
            Audio existingAudio = audioDao.read(audio.getTranscription(), audio.getLocale());
            if ((existingAudio != null) && !existingAudio.getId().equals(audio.getId())) {
                result.rejectValue("transcription", "NonUnique");
            }
        }

        try {
            setFormat(audio,multipartFile,result);
        } catch (IOException e) {
            logger.error(e);
        }

        if (result.hasErrors()) {
            return setModel(model, audio, contributor.getLocale());
        } else {
            audio.setTranscription(audio.getTranscription().toLowerCase());
            audio.setTimeLastUpdate(Calendar.getInstance());
            audio.setRevisionNumber(audio.getRevisionNumber() + 1);
            audioDao.update(audio);

            // TODO: store RevisionEvent

            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just edited an Audio:\n" +
                                "• Language: \"" + audio.getLocale().getLanguage() + "\"\n" +
                                "• Transcription: \"" + audio.getTranscription() + "\"\n" +
                                "• Revision number: #" + audio.getRevisionNumber() + "\n" +
                                "See ") + "http://elimu.ai/content/multimedia/audio/edit/" + audio.getId();
                postMessageOnSlack(text, contributor.getImageUrl(), null);
            }

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

    protected String setModel(Model model, Audio audio, Locale locale) {
        model.addAttribute("audio", audio);

        addContentLicensesLiteracySkillsNumeracySkills(model);
        addLettersNumbersWords(model, locale);
        return "content/multimedia/audio/edit";
    }
}
