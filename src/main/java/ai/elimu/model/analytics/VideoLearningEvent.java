package ai.elimu.model.analytics;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.content.multimedia.Video;

@Entity
public class VideoLearningEvent extends LearningEvent {
    
    @NotNull
    @ManyToOne
    private Video video;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
