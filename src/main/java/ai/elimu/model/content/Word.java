package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;

@Entity
public class Word extends Content {

    @NotNull
    private String text;
    
    @Deprecated
    @NotNull
    private String phonetics; // IPA
    
//    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Allophone> allophones;
    
    private int usageCount; // Based on StoryBook content
    
    @Enumerated(EnumType.STRING)
    private WordType wordType;
    
    @Enumerated(EnumType.STRING)
    private SpellingConsistency spellingConsistency;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
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
