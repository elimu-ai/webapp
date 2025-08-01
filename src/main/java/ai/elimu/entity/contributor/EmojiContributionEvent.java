package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Emoji;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class EmojiContributionEvent extends ContributionEvent {

  @NotNull
  @ManyToOne
  private Emoji emoji;
}
