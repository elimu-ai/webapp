package ai.elimu.entity.analytics;

import ai.elimu.entity.content.Number;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class NumberLearningEvent extends LearningEvent {

  /**
   * The number represented as an Integer. E.g. <code>10</code>.
   */
  @NotNull
  private Integer numberValue;

  /**
   * The number represented as a symbol specific to the language. E.g. <code>"१०"</code>.
   */
  private String numberSymbol;

  /**
   * This field might not be included, e.g. if the assessment task was done in a 
   * 3rd-party app that did not load the content from the elimu.ai Content Provider. 
   * In that case, this field will be {@code null}.
   */
  private Long numberId;

  @ManyToOne
  private Number number;
}
