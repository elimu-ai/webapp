package ai.elimu.entity.analytics;

import ai.elimu.entity.content.Word;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WordLearningEvent extends LearningEvent {

  @ManyToOne
  private Word word;

  /**
   * A Word's text value is used as a fall-back if the Android application did not use a Word ID.
   */
//    @NotNull
  private String wordText;
}
