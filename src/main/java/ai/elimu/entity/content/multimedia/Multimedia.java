package ai.elimu.entity.content.multimedia;

import ai.elimu.entity.content.Content;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.enums.ContentLicense;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

/**
 * Parent class for different types of multimedia (images, videos, etc).
 */
@Getter
@Setter
@MappedSuperclass
public abstract class Multimedia extends Content {

  /**
   * The MIME type - https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types
   * <p>
   * Example: "video/mp4"
   */
  @NotNull
  private String contentType;

  @Enumerated(EnumType.STRING)
  private ContentLicense contentLicense;

  @Size(max = 1000)
  @Column(length = 1000)
  private String attributionUrl;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<LiteracySkill> literacySkills;

  @ElementCollection(fetch = FetchType.EAGER)
  @Enumerated(EnumType.STRING)
  private Set<NumeracySkill> numeracySkills;

  /**
   * {@link Letter}s used as labels for this content.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "letters_id", unique = false)
  )
  private Set<Letter> letters;

  /**
   * {@link Number}s used as labels for this content.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "numbers_id", unique = false)
  )
  private Set<Number> numbers;

  /**
   * {@link Word}s used as labels for this content.
   */
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      inverseJoinColumns = @JoinColumn(name = "words_id", unique = false)
  )
  private Set<Word> words;
}
