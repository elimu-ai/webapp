package ai.elimu.model.contributor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.content.Word;

@Entity
public class WordRevisionEvent extends ContributorEvent {
    
    @NotNull
    @ManyToOne
    private Word word;
    
    @NotNull
    private String text;
    
    @NotNull
    private String phonetics; // IPA
    
    private String comment;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
