package ai.elimu.model.contributor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link Word} which 
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class WordPeerReviewEvent extends PeerReviewEvent {
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private WordContributionEvent wordContributionEvent;

    public WordContributionEvent getWordContributionEvent() {
        return wordContributionEvent;
    }

    public void setWordContributionEvent(WordContributionEvent wordContributionEvent) {
        this.wordContributionEvent = wordContributionEvent;
    }
}
