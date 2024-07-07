package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import javax.persistence.ManyToOne;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Word extends Content {

    @Deprecated // TODO: replace with list of LetterSoundCorrespondences
    @NotNull
    private String text;
    
    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<LetterSound> letterSounds;
    
    /**
     * As an example, the verb "reading" will be linked to the root verb "read".
     */
    @ManyToOne
    private Word rootWord;
    
    @Enumerated(EnumType.STRING)
    private WordType wordType;
    
//    @NotNull
    @Enumerated(EnumType.STRING)
    private SpellingConsistency spellingConsistency;

    @Deprecated
    public String getText() {
        return text;
    }

    @Deprecated
    public void setText(String text) {
        this.text = text;
    }
    
    public List<LetterSound> getLetterSoundCorrespondences() {
        return letterSounds;
    }

    public void setLetterSoundCorrespondences(List<LetterSound> letterSounds) {
        this.letterSounds = letterSounds;
    }
    
    public Word getRootWord() {
        return rootWord;
    }

    public void setRootWord(Word rootWord) {
        this.rootWord = rootWord;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public SpellingConsistency getSpellingConsistency() {
        return spellingConsistency;
    }

    public void setSpellingConsistency(SpellingConsistency spellingConsistency) {
        this.spellingConsistency = spellingConsistency;
    }
}
