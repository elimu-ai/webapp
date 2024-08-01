package ai.elimu.model.analytics;

import ai.elimu.model.content.StoryBook;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class StoryBookLearningEvent extends LearningEvent {
    
    @NotNull
    private Long storyBookId;
    
    @NotNull
    private String storyBookTitle;
    
    /**
     * This field will only be populated if a corresponding {@link StoryBook} can be 
     * found in the database for the {@link #storyBookId}.
     */
    @ManyToOne
    private StoryBook storyBook;
    
    public Long getStoryBookId() {
        return storyBookId;
    }

    public void setStoryBookId(Long storyBookId) {
        this.storyBookId = storyBookId;
    }

    public String getStoryBookTitle() {
        return storyBookTitle;
    }

    public void setStoryBookTitle(String storyBookTitle) {
        this.storyBookTitle = storyBookTitle;
    }

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }
}
