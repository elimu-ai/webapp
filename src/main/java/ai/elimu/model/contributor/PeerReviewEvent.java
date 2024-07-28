package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;

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
    private Calendar timestamp;

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

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp){
        this.timestamp = timestamp;
    }
}
