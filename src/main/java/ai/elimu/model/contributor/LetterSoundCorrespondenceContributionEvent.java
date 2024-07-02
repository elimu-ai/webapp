package ai.elimu.model.contributor;

import ai.elimu.model.content.LetterSoundCorrespondence;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LetterSoundCorrespondenceContributionEvent extends ContributionEvent {

    @NotNull
    @ManyToOne
    private LetterSoundCorrespondence letterSoundCorrespondence;

    public LetterSoundCorrespondence getLetterSoundCorrespondence() {
        return letterSoundCorrespondence;
    }

    public void setLetterSoundCorrespondence(LetterSoundCorrespondence letterSoundCorrespondence) {
        this.letterSoundCorrespondence = letterSoundCorrespondence;
    }
}
