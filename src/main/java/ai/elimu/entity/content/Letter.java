package ai.elimu.entity.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Letter extends Content {

  @NotNull
  @Size(max = 3)
  @Column(length = 3)
  private String text;

  private boolean diacritic;
}
