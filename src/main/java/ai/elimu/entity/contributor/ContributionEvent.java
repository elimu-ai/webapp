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
 * Parent class for various types of content contribution events.
 */
@Getter
@Setter
@MappedSuperclass
public class ContributionEvent extends BaseEntity {

  @NotNull
  @ManyToOne
  private Contributor contributor;

  /**
   * The content's revision number (after being created/edited).
   */
  @NotNull
  private Integer revisionNumber;

  /**
   * A comment explaining the contribution.
   */
  @Column(length = 1000)
  private String comment;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar timestamp;

  /**
   * The time passed during the creation/editing.
   */
  @NotNull
  private Long timeSpentMs;
}
