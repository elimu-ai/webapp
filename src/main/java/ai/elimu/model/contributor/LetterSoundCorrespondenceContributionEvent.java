package ai.elimu.model.contributor;

import ai.elimu.model.content.LetterSound;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LetterSoundCorrespondenceContributionEvent extends ContributionEvent {

    @NotNull
    @ManyToOne
    private LetterSound letterSound;

    public LetterSound getLetterSoundCorrespondence() {
        return letterSound;
    }

    public void setLetterSoundCorrespondence(LetterSound letterSound) {
        this.letterSound = letterSound;
    }
}
