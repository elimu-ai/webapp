package ai.elimu.entity.analytics;

import ai.elimu.entity.content.StoryBook;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoryBookLearningEvent extends LearningEvent {

  private Long storyBookId;

  @NotNull
  private String storyBookTitle;

  /**
   * This field will only be populated if a corresponding {@link StoryBook} can be found in the database for the {@link #storyBookId}.
   */
  @ManyToOne
  private StoryBook storyBook;
}
