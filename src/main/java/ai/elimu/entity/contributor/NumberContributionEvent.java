package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Number;
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
