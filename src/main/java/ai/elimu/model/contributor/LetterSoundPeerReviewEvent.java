package ai.elimu.model.contributor;

import ai.elimu.model.content.LetterSound;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link LetterSound} which
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class LetterSoundPeerReviewEvent extends PeerReviewEvent {
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private LetterSoundContributionEvent letterSoundContributionEvent;

    public LetterSoundContributionEvent getLetterSoundContributionEvent() {
        return letterSoundContributionEvent;
    }

    public void setLetterSoundContributionEvent(LetterSoundContributionEvent letterSoundContributionEvent) {
        this.letterSoundContributionEvent = letterSoundContributionEvent;
    }
}
