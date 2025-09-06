package ai.elimu.entity.content;

import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
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
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Entity
@Slf4j
public class Word extends Content {

  @Deprecated // TODO: will be replaced by `toString()`
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

  public String toString() {
      WordGson wordGson = JpaToGsonConverter.getWordGson(this);
      log.info("wordGson.getId(): " + wordGson.getId());
      return wordGson.toString();
  }
}
