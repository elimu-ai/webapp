package ai.elimu.model.analytics;

import ai.elimu.model.content.StoryBook;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class StoryBookLearningEvent extends LearningEvent {
    
    @ManyToOne
    private StoryBook storyBook;

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }
}
