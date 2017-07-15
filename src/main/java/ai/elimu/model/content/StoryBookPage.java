package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import org.hibernate.validator.constraints.NotEmpty;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;

@Entity
public class StoryBookPage extends BaseEntity {
    
    @ManyToOne
    private Image image;

    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Word> words;
    
    @ManyToOne
    private Audio audio;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
