package ai.elimu.model.contributor;

import ai.elimu.model.content.LetterSound;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LetterSoundContributionEvent extends ContributionEvent {

    @NotNull
    @ManyToOne
    private LetterSound letterSound;

    public LetterSound getLetterSound() {
        return letterSound;
    }

    public void setLetterSound(LetterSound letterSound) {
        this.letterSound = letterSound;
    }
}
