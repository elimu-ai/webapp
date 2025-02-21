package ai.elimu.model.contributor;

import ai.elimu.model.content.Number;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class NumberContributionEvent extends ContributionEvent {

  @NotNull
  @ManyToOne
  private Number number;
}
