package ai.elimu.model.analytics;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.analytics.LearningEventType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class StoryBookLearningEvent extends LearningEvent {
    
    @ManyToOne
    private StoryBook storyBook;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private LearningEventType learningEventType;

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }
}
