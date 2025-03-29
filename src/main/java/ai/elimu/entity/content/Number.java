package ai.elimu.entity.content;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Number extends Content {

  @NotNull
  private Integer value;

  private String symbol;

  @NotEmpty
  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Word> words;
}
