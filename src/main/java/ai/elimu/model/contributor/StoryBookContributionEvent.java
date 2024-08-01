package ai.elimu.model.contributor;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookParagraph;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class StoryBookContributionEvent extends ContributionEvent {
    
    @NotNull
    @ManyToOne
    private StoryBook storyBook;
    
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

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
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
}
