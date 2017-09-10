package ai.elimu.web.content.multimedia.video;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.VideoDao;
import ai.elimu.dao.VideoRevisionEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.contributor.VideoRevisionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.VideoFormat;
import ai.elimu.model.enums.content.LiteracySkill;
import ai.elimu.model.enums.content.NumeracySkill;
import ai.elimu.util.ImageHelper;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
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
@RequestMapping("/content/multimedia/video/create")
public class VideoCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private VideoDao videoDao;
    
    @Autowired
    private VideoRevisionEventDao videoRevisionEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Video video = new Video();
        model.addAttribute("video", video);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());

        return "content/multimedia/video/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            /*@Valid*/ Video video,
            @RequestParam("bytes") MultipartFile multipartFile,
            @RequestParam("thumbnail") MultipartFile multipartFileThumbnail,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (StringUtils.isBlank(video.getTitle())) {
            result.rejectValue("title", "NotNull");
        } else {
            Video existingVideo = videoDao.read(video.getTitle(), video.getLocale());
            if (existingVideo != null) {
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
            
            byte[] bytesThumbnail = multipartFileThumbnail.getBytes();
            if (multipartFileThumbnail.isEmpty() || (bytesThumbnail == null) || (bytesThumbnail.length == 0)) {
                result.rejectValue("thumbnail", "NotNull");
            } else {
                String originalFileName = multipartFileThumbnail.getOriginalFilename();
                logger.info("originalFileName: " + originalFileName);
                if (!originalFileName.toLowerCase().endsWith(".png")) {
                    result.rejectValue("thumbnail", "typeMismatch");
                } else {
                    video.setThumbnail(bytesThumbnail);

                    int width = ImageHelper.getWidth(bytesThumbnail);
                    logger.info("width: " + width + "px");

                    if (width < ImageHelper.MINIMUM_WIDTH) {
                        result.rejectValue("thumbnail", "image.too.small");
                        video.setBytes(null);
                    } else {
                        if (width > ImageHelper.MINIMUM_WIDTH) {
                            bytesThumbnail = ImageHelper.scaleImage(bytesThumbnail, ImageHelper.MINIMUM_WIDTH);
                            video.setBytes(bytesThumbnail);
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
            
            return "content/multimedia/video/create";
        } else {
            video.setTitle(video.getTitle().toLowerCase());
            video.setTimeLastUpdate(Calendar.getInstance());
            videoDao.create(video);
            
            VideoRevisionEvent videoRevisionEvent = new VideoRevisionEvent();
            videoRevisionEvent.setContributor(contributor);
            videoRevisionEvent.setCalendar(Calendar.getInstance());
            videoRevisionEvent.setVideo(video);
            videoRevisionEvent.setTitle(video.getTitle());
            videoRevisionEventDao.create(videoRevisionEvent);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                     contributor.getFirstName() + " just added a new Video:\n" + 
                     "• Language: \"" + video.getLocale().getLanguage() + "\"\n" + 
                     "• Title: \"" + video.getTitle() + "\"\n" + 
                     "See ") + "http://elimu.ai/content/multimedia/video/edit/" + video.getId();
                 String iconUrl = contributor.getImageUrl();
                 String imageUrl = "http://elimu.ai/video/" + video.getId() + "/thumbnail.png";
                 SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.CONTENT_CREATION), text, iconUrl, imageUrl);
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
}
