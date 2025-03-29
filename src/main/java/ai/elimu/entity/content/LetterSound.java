package ai.elimu.entity.content;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderColumn;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Contains information about the various sounds a letter (or letter combination) can represent.
 */
@Getter
@Setter
@Entity
public class LetterSound extends Content {

  //    @NotEmpty
  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Letter> letters;

  //    @NotEmpty
  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Sound> sounds;
}
