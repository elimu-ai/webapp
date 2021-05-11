package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.enums.Platform;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class AudioContributionEvent extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    @NotNull
    @ManyToOne
    private Audio audio;
    
    @NotNull
    private Integer revisionNumber;

    private String comment;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;
    
    /**
     * The time passed during the creation/editing of the {@link #audio}.
     */
    @NotNull
    private Long timeSpentMs;
    
    /**
     * The platform that the {@link Contributor} used.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Platform platform;

    public String getComment() {
        return comment;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public Calendar getTime() {
        return time;
    }

    public Audio getAudio() {
        return audio;
    }
    
    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Long getTimeSpentMs() {
        return timeSpentMs;
    }

    public void setTimeSpentMs(Long timeSpentMs) {
        this.timeSpentMs = timeSpentMs;
    }
    
    public Platform getPlatform() {
        return platform;
    }
    
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
