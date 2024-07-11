package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import java.util.Calendar;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Parent class for various types of content contribution events.
 */
@MappedSuperclass
public class ContributionEvent extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    /**
     * The content's revision number (after being created/edited).
     */
    @NotNull
    private Integer revisionNumber;

    /**
     * A comment explaining the contribution.
     */
    @Column(length = 1000)
    private String comment;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;
    
    /**
     * The time passed during the creation/editing.
     */
    @NotNull
    private Long timeSpentMs;

    public String getComment() {
        return comment;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public Calendar getTime() {
        return time;
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

    public Long getTimeSpentMs() {
        return timeSpentMs;
    }

    public void setTimeSpentMs(Long timeSpentMs) {
        this.timeSpentMs = timeSpentMs;
    }
}
