package ai.elimu.entity.contributor;

import ai.elimu.entity.content.LetterSound;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LetterSoundContributionEvent extends ContributionEvent {

  @NotNull
  @ManyToOne
  private LetterSound letterSound;
}
