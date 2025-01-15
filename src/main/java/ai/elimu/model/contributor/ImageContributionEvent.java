package ai.elimu.model.contributor;

import ai.elimu.model.content.multimedia.Image;
import jakarta.persistence.*;
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
