package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import java.util.Calendar;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link Word} which 
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class WordPeerReviewEvent extends BaseEntity {

    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private WordContributionEvent wordContributionEvent;
    
    /**
     * Whether or not the {@link #wordContributionEvent} was approved.
     */
    @NotNull
    private Boolean approved;
    
    /**
     * Any additional explanations. This field is mandatory only if the 
     * {@link #wordContributionEvent} was <i>not</i> approved.
     */
    @Column(length = 1000)
    private String comment;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public WordContributionEvent getWordContributionEvent() {
        return wordContributionEvent;
    }

    public void setWordContributionEvent(WordContributionEvent wordContributionEvent) {
        this.wordContributionEvent = wordContributionEvent;
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
}
