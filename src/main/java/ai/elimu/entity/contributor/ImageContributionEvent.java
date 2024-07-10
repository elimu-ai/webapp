package ai.elimu.entity.contributor;

import ai.elimu.entity.content.multimedia.Image;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ImageContributionEvent extends ContributionEvent {
    
    @NotNull
    @ManyToOne
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
