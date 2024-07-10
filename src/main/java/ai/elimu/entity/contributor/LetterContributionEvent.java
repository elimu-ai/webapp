package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Letter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

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
