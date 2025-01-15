package ai.elimu.model.analytics;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import ai.elimu.model.content.multimedia.Video;

@Entity
public class VideoLearningEvent extends LearningEvent {

    @NotNull
    private String videoTitle;

    /**
     * This field might not be included, e.g. if the videos were opened in a 3rd-party 
     * app that did not load the videos from the elimu.ai Content Provider. In this 
     * case, the {@link #videoId} will be {@code null}.
     */
    private Long videoId;

    /**
     * This field will only be populated if a corresponding {@link Video} can be 
     * found in the database for a given {@link #videoId}.
     */
    @ManyToOne
    private Video video;

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public Long getVideoId() {
        return videoId;
    }

    public void setVideoId(Long videoId) {
        this.videoId = videoId;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
