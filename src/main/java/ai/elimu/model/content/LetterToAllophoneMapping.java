package ai.elimu.model.content;

import ai.elimu.model.BaseEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Contains information about the various sounds a letter can represent.
 */
@Entity
public class LetterToAllophoneMapping extends BaseEntity {
    
    @Deprecated
    @NotNull
    @ManyToOne
    private Letter letter;
    
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Letter> letters;
    
//    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Allophone> allophones;
    
    private int usageCount;

    @Deprecated
    public Letter getLetter() {
        return letter;
    }

    @Deprecated
    public void setLetter(Letter letter) {
        this.letter = letter;
    }
    
    public List<Letter> getLetters() {
        return letters;
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }

    public List<Allophone> getAllophones() {
        return allophones;
    }

    public void setAllophones(List<Allophone> allophones) {
        this.allophones = allophones;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
