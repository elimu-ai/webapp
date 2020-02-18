package ai.elimu.model.content;

import javax.persistence.Entity;
import ai.elimu.model.BaseEntity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class StoryBookChapter extends BaseEntity {
    
    @ManyToOne
    private StoryBook storyBook;
    
    /**
     * [0, 1, 2, ...]
     */
    @NotNull
    private Integer sortOrder;

    public StoryBook getStoryBook() {
        return storyBook;
    }

    public void setStoryBook(StoryBook storyBook) {
        this.storyBook = storyBook;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
