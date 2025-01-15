package ai.elimu.model.content;

import jakarta.persistence.Entity;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.content.multimedia.Image;
import jakarta.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class StoryBookChapter extends BaseEntity {
    
//    @NotNull
    @ManyToOne
    private StoryBook storyBook;
    
    /**
     * [0, 1, 2, ...]
     */
    @NotNull
    private Integer sortOrder;
    
    @ManyToOne
    private Image image;

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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
