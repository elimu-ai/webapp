package ai.elimu.model.contributor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link Number} which 
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class NumberPeerReviewEvent extends PeerReviewEvent {
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private NumberContributionEvent numberContributionEvent;

    public NumberContributionEvent getNumberContributionEvent() {
        return numberContributionEvent;
    }

    public void setNumberContributionEvent(NumberContributionEvent numberContributionEvent) {
        this.numberContributionEvent = numberContributionEvent;
    }
}
