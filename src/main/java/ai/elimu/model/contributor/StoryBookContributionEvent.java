package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookParagraph;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class StoryBookContributionEvent extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    @NotNull
    @ManyToOne
    private StoryBook storyBook;
    
    @NotNull
    private Integer revisionNumber;

    @Column(length = 1000)
    private String comment;
    
    /**
     * This text will only be set if a {@link StoryBookParagraph}'s text is edited.
     */
    @Column(length = 1000)
    private String paragraphTextBefore;
    
    /**
     * This text will only be set if a {@link StoryBookParagraph}'s text is edited.
     */
    @Column(length = 1000)
    private String paragraphTextAfter;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;
    
    /**
     * The time passed during the creation/editing of the {@link #storyBook}.
     */
    private Long timeSpentMs;

    public String getComment() {
        return comment;
    }
    
    public String getParagraphTextBefore() {
        return paragraphTextBefore;
    }
    
    public void setParagraphTextBefore(String paragraphTextBefore) {
        this.paragraphTextBefore = paragraphTextBefore;
    }
    
    public String getParagraphTextAfter() {
        return paragraphTextAfter;
    }
    
    public void setParagraphTextAfter(String paragraphTextAfter) {
        this.paragraphTextAfter = paragraphTextAfter;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public Calendar getTime() {
        return time;
    }

    public StoryBook getStoryBook() {
        return storyBook;
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

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    public Long getTimeSpentMs() {
        return timeSpentMs;
    }

    public void setTimeSpentMs(Long timeSpentMs) {
        this.timeSpentMs = timeSpentMs;
    }
}
