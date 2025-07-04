package ai.elimu.entity.analytics;

import ai.elimu.entity.content.LetterSound;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LetterSoundLearningEvent extends LearningEvent {

  /**
   * This field might not be included, e.g. if the letter-sound correspondence was learned 
   * in a 3rd-party app that did not load the content from the elimu.ai Content Provider. 
   * In this case, the {@link #letterSoundId} will be {@code null}.
   */
  private Long letterSoundId;

  @ManyToOne
  private LetterSound letterSound;
}
