package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Word;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class WordContributionEvent extends ContributionEvent {
    
    @NotNull
    @ManyToOne
    private Word word;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
