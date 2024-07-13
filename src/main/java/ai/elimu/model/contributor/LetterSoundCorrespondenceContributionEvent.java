package ai.elimu.model.contributor;

import ai.elimu.model.content.LetterSoundCorrespondence;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LetterSoundCorrespondenceContributionEvent extends ContributionEvent {

    @NotNull
    @ManyToOne
    private LetterSoundCorrespondence letterSound;

    public LetterSoundCorrespondence getLetterSound() {
        return letterSound;
    }

    public void setLetterSound(LetterSoundCorrespondence letterSound) {
        this.letterSound = letterSound;
    }
}
