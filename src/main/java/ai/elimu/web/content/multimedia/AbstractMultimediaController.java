package ai.elimu.web.content.multimedia;

import ai.elimu.model.Contributor;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.content.multimedia.Multimedia;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.contributor.VideoRevisionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.*;
import ai.elimu.util.ImageHelper;
import ai.elimu.util.SlackApiHelper;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;

public abstract class AbstractMultimediaController {
    private final Logger logger = Logger.getLogger(getClass());

    protected void addContentLicensesLiteracySkillsNumeracySkills(Model model) {
        model.addAttribute("contentLicenses", ContentLicense.values());
        model.addAttribute("literacySkills", LiteracySkill.values());
        model.addAttribute("numeracySkills", NumeracySkill.values());
    }

    protected void postMessageOnSlack(String text, String iconUrl, String imageUrl) {
        SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.CONTENT_CREATION), text, iconUrl, imageUrl);
    }

    protected void setFormat(Multimedia multimedia, MultipartFile multipartFile, BindingResult result) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        if (multipartFile.isEmpty() || (bytes == null) || (bytes.length == 0)) {
            result.rejectValue("bytes", "NotNull");
        } else {
            String originalFileName = multipartFile.getOriginalFilename();
            logger.info("originalFileName: " + originalFileName);
            String fileExtension = FilenameUtils.getExtension(originalFileName);
            if(multimedia instanceof  Audio){
                setAudioFormat((Audio) multimedia,multipartFile,result,fileExtension,bytes);
            }else if(multimedia instanceof  Image){
                setImageFormat((Image) multimedia,multipartFile,result,fileExtension,bytes);
            }else if(multimedia instanceof Video){
                setVideoFormat((Video) multimedia,multipartFile,result,fileExtension,bytes);
            }
        }
    }
    private void setAudioFormat(Audio audio, MultipartFile multipartFile, BindingResult result,String fileExtension,byte[] bytes ){
        switch (fileExtension) {
            case "mp3":
                audio.setAudioFormat(AudioFormat.MP3);
                break;
            case "ogg":
                audio.setAudioFormat(AudioFormat.OGG);
                break;
            case "wav":
                audio.setAudioFormat(AudioFormat.WAV);
                break;
            default:
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
    private void setImageFormat(Image image, MultipartFile multipartFile, BindingResult result, String fileExtension,byte[] bytes ){
        switch (fileExtension) {
            case "png":
                image.setImageFormat(ImageFormat.PNG);
                break;
            case "jpg":
                image.setImageFormat(ImageFormat.JPG);
                break;
            case "gif":
                image.setImageFormat(ImageFormat.GIF);
                break;
            default:
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
    private void setVideoFormat(Video video, MultipartFile multipartFile, BindingResult result, String fileExtension,byte[] bytes ){
        switch (fileExtension) {
            case "m4v":
                video.setVideoFormat(VideoFormat.M4V);
                break;
            case "mp4":
                video.setVideoFormat(VideoFormat.MP4);
                break;
            default:
                result.rejectValue("bytes", "t    video.setVideoFormat(VideoFormat.MP4);ypeMismatch");
        }
        if (video.getVideoFormat() != null) {
            String contentType = multipartFile.getContentType();
            logger.info("contentType: " + contentType);
            video.setContentType(contentType);

            video.setBytes(bytes);

            // TODO: convert to a default video format?
        }
    }
    protected VideoRevisionEvent createeVideoRevisionEvent(Video video, Contributor contributor){
        VideoRevisionEvent videoRevisionEvent = new VideoRevisionEvent();
        videoRevisionEvent.setContributor(contributor);
        videoRevisionEvent.setCalendar(Calendar.getInstance());
        videoRevisionEvent.setVideo(video);
        videoRevisionEvent.setTitle(video.getTitle());
        return videoRevisionEvent;
    }

}
