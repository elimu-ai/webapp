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
  @Size(max = 4)
  @Column(length = 4)
  private String text;

  private boolean diacritic;
}
