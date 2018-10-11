package ai.elimu.web.content.multimedia.video;

import ai.elimu.dao.*;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.contributor.VideoRevisionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.model.enums.content.VideoFormat;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.content.multimedia.AbstractMultimediaEditController;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Set;

@Controller
@RequestMapping("/content/multimedia/video/edit")
public class VideoEditController extends AbstractMultimediaEditController<Video> {

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
        return setModel(model, video, contributor.getLocale());
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
            setFormat(video, multipartFile, result);
        } catch (IOException e) {
            logger.error(e);
        }

        if (result.hasErrors()) {
            return setModel(model, video, contributor.getLocale());
        } else {
            video.setTitle(video.getTitle().toLowerCase());
            video.setTimeLastUpdate(Calendar.getInstance());
            video.setRevisionNumber(video.getRevisionNumber() + 1);
            videoDao.update(video);

            videoRevisionEventDao.create(createeVideoRevisionEvent(video,contributor));

            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just updated a Video:\n" +
                                "• Language: \"" + video.getLocale().getLanguage() + "\"\n" +
                                "• Title: \"" + video.getTitle() + "\"\n" +
                                "• Revision number: #" + video.getRevisionNumber() + "\n" +
                                "See ") + "http://elimu.ai/content/multimedia/video/edit/" + video.getId();
                String imageUrl = "http://elimu.ai/video/" + video.getId() + "/thumbnail.png";
                postMessageOnSlack(text, contributor.getImageUrl(), imageUrl);
            }

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

    protected String setModel(Model model, Video video, Locale locale) {
        model.addAttribute("video", video);
        addContentLicensesLiteracySkillsNumeracySkills(model);
        model.addAttribute("videoRevisionEvents", videoRevisionEventDao.readAll(video));
        addLettersNumbersWords(model, locale);
        return "content/multimedia/video/edit";
    }

}
