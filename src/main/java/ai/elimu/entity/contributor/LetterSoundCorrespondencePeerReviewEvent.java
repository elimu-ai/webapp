package ai.elimu.entity.contributor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link LetterSoundCorrespondence} which 
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class LetterSoundCorrespondencePeerReviewEvent extends PeerReviewEvent {
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent;

    public LetterSoundCorrespondenceContributionEvent getLetterSoundCorrespondenceContributionEvent() {
        return letterSoundCorrespondenceContributionEvent;
    }

    public void setLetterSoundCorrespondenceContributionEvent(LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent) {
        this.letterSoundCorrespondenceContributionEvent = letterSoundCorrespondenceContributionEvent;
    }
}
