package ai.elimu.entity.contributor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link StoryBook} which 
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class StoryBookPeerReviewEvent extends PeerReviewEvent {
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private StoryBookContributionEvent storyBookContributionEvent;

    public StoryBookContributionEvent getStoryBookContributionEvent() {
        return storyBookContributionEvent;
    }

    public void setStoryBookContributionEvent(StoryBookContributionEvent storyBookContributionEvent) {
        this.storyBookContributionEvent = storyBookContributionEvent;
    }
}
