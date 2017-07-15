package ai.elimu.model.contributor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.content.multimedia.Video;

@Entity
public class VideoRevisionEvent extends ContributorEvent {
    
    @NotNull
    @ManyToOne
    private Video video;
    
    @NotNull
    private String title;
    
    private String comment;

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
