package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Word;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WordContributionEvent extends ContributionEvent {

  @NotNull
  @ManyToOne
  private Word word;
}
