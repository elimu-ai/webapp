package ai.elimu.entity.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Emoji extends Content {

  @NotNull
  @Size(max = 4)
  @Column(length = 4)
  private String glyph; // E.g. '🦋' or

  /**
   * The Unicode version when the glyph was first introduced.
   * <p/>
   * Should be 10.0 or lower, in order to be compatible with Android SDK 8.0 (API level 26).
   */
  @NotNull
  private Double unicodeVersion;

  /**
   * The Unicode Emoji version when the glyph was first introduced.
   * <p/>
   * Should be 5.0 or lower, in order to be compatible with Android SDK 8.0 (API level 26).
   * <p/>
   * See https://unicode.org/Public/emoji/5.0/emoji-data.txt
   */
  @NotNull
  private Double unicodeEmojiVersion;

  /**
   * Word(s) used for labeling the emoji.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "words_id", unique = false)
  )
  private Set<Word> words;
}
