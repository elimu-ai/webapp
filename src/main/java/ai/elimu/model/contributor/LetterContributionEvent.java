package ai.elimu.model.contributor;

import ai.elimu.model.content.Letter;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class LetterContributionEvent extends ContributionEvent {
    
    @NotNull
    @ManyToOne
    private Letter letter;

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
}
