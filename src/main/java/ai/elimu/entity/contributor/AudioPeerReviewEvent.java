package ai.elimu.entity.contributor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * An event where a {@link Contributor} peer-reviews a {@link Audio} which 
 * was added/edited by another {@link Contributor}.
 */
@Entity
public class AudioPeerReviewEvent extends PeerReviewEvent {
    
    /**
     * The contribution event which is being peer-reviewed.
     */
    @NotNull
    @ManyToOne
    private AudioContributionEvent audioContributionEvent;

    public AudioContributionEvent getAudioContributionEvent() {
        return audioContributionEvent;
    }

    public void setAudioContributionEvent(AudioContributionEvent audioContributionEvent) {
        this.audioContributionEvent = audioContributionEvent;
    }
}
