package ai.elimu.entity.content;

import ai.elimu.entity.BaseEntity;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.model.v2.enums.content.ContentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;

/**
 * Parent class for different types of educational content.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Content extends BaseEntity {

  @Temporal(TemporalType.TIMESTAMP)
  private Calendar timeLastUpdate;

  @NotNull
  private Integer revisionNumber = 1; // [1, 2, 3, ...]

  /**
   * See UsageCountSchedulers in {@link ai.elimu.tasks} for details on how this value is being updated on a regular basis.
   */
  @NotNull
  private Integer usageCount = 0;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ContentStatus contentStatus = ContentStatus.ACTIVE;

  @Enumerated(EnumType.STRING)
  private PeerReviewStatus peerReviewStatus = PeerReviewStatus.PENDING;
}
