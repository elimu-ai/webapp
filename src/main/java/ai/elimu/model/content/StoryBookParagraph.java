package ai.elimu.model.content;

import javax.persistence.Entity;
import ai.elimu.model.BaseEntity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class StoryBookParagraph extends BaseEntity {
    
    @ManyToOne
    private StoryBookChapter storyBookChapter;
    
    /**
     * [0, 1, 2, ...]
     */
    @NotNull
    private Integer sortOrder;
    
    @NotNull
    private String originalText;
    
    public StoryBookChapter getStoryBookChapter() {
        return storyBookChapter;
    }

    public void setStoryBookChapter(StoryBookChapter storyBookChapter) {
        this.storyBookChapter = storyBookChapter;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }
}
