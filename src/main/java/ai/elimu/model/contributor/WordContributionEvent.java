package ai.elimu.model.contributor;

import ai.elimu.model.content.Word;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

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
