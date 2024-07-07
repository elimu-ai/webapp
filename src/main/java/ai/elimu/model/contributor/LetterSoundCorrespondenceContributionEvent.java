package ai.elimu.model.contributor;

import ai.elimu.model.content.LetterSound;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LetterSoundCorrespondenceContributionEvent extends ContributionEvent {

    @NotNull
    @ManyToOne
    private LetterSound letterSoundCorrespondence;

    public LetterSound getLetterSoundCorrespondence() {
        return letterSoundCorrespondence;
    }

    public void setLetterSoundCorrespondence(LetterSound letterSoundCorrespondence) {
        this.letterSoundCorrespondence = letterSoundCorrespondence;
    }
}
