package ai.elimu.entity.analytics;

import ai.elimu.entity.content.multimedia.Video;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class VideoLearningEvent extends LearningEvent {

  @NotNull
  private String videoTitle;

  /**
   * This field might not be included, e.g. if the videos were opened in a 3rd-party app that did not load the videos from the elimu.ai Content Provider. In this case, the {@link #videoId} will be
   * {@code null}.
   */
  private Long videoId;

  /**
   * This field will only be populated if a corresponding {@link Video} can be found in the database for a given {@link #videoId}.
   */
  @ManyToOne
  private Video video;
}
