package ai.elimu.entity.contributor;

import ai.elimu.entity.BaseEntity;
import ai.elimu.entity.enums.Platform;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link ContributionEvent} of another {@link Contributor}.
 */
@MappedSuperclass
public class PeerReviewEvent extends BaseEntity {

    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    /**
     * Whether or not the {@link ContributionEvent} was approved.
     */
    @NotNull
    private Boolean approved;
    
    /**
     * Any additional explanations. This field is mandatory only if the 
     * {@link ContributionEvent} was <i>not</i> approved.
     */
    @Column(length = 1000)
    private String comment;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;
    
    /**
     * The platform that the {@link Contributor} used when performing the peer-review.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Platform platform;

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public Boolean isApproved() {
        return approved;
    }

    public void setApproved(Boolean isApproved) {
        this.approved = isApproved;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
    
    public Platform getPlatform() {
        return platform;
    }
    
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
}
