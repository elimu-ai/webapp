package ai.elimu.model.contributor;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * An event where a {@link Contributor} peer-reviews a {@link Audio} which was added/edited by another {@link Contributor}.
 */
@Deprecated
@Getter
@Setter
@Entity
public class AudioPeerReviewEvent extends PeerReviewEvent {

  /**
   * The contribution event which is being peer-reviewed.
   */
  @NotNull
  @ManyToOne
  private AudioContributionEvent audioContributionEvent;
}
