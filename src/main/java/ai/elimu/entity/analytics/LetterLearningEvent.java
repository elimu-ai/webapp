package ai.elimu.entity.analytics;

import ai.elimu.entity.content.Letter;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LetterLearningEvent extends LearningEvent {

  @ManyToOne
  private Letter letter;

  /**
   * A {@link Letter}'s text value is used as a fall-back if the Android application did not use a Letter ID. This can happen if the learning event occurred within a 3rd-party application which is not
   * integrated with the elimu.ai Content Provider.
   */
  @NotNull
  private String letterText;
}
