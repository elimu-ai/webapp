package ai.elimu.entity.content;

import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Word extends Content {

  @Deprecated // TODO: replace with list of letterSounds
  @NotNull
  private String text;

  @NotEmpty
  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER)
  private List<LetterSound> letterSounds;

  /**
   * As an example, the verb "reading" will be linked to the root verb "read".
   */
  @ManyToOne
  private Word rootWord;

  @Enumerated(EnumType.STRING)
  private WordType wordType;

  //    @NotNull
  @Enumerated(EnumType.STRING)
  private SpellingConsistency spellingConsistency;
}
