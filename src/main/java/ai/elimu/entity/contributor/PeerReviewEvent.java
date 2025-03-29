package ai.elimu.entity.contributor;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;

import ai.elimu.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * An event where a {@link Contributor} peer-reviews a {@link ContributionEvent} of another {@link Contributor}.
 */
@Getter
@Setter
@MappedSuperclass
public class PeerReviewEvent extends BaseEntity {

  @NotNull
  @ManyToOne
  private Contributor contributor;

  /**
   * Whether or not the {@link ContributionEvent} was approved.
   */
  @NotNull
  private Boolean approved;

  /**
   * Any additional explanations. This field is mandatory only if the {@link ContributionEvent} was <i>not</i> approved.
   */
  @Column(length = 1000)
  private String comment;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar timestamp;
}
