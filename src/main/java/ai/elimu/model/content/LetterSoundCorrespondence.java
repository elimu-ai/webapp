package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains information about the various sounds a letter (or letter combination) can represent.
 */
@Entity
public class LetterSoundCorrespondence extends Content {
    
//    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Letter> letters;
    
//    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Allophone> sounds;
    
    public List<Letter> getLetters() {
        return letters;
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public List<Allophone> getSounds() {
        return sounds;
    }

    public void setSounds(List<Allophone> sounds) {
        this.sounds = sounds;
    }
}
