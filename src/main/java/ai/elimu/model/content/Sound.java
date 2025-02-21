package ai.elimu.model.content;

import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.v2.enums.content.sound.ConsonantPlace;
import ai.elimu.model.v2.enums.content.sound.ConsonantType;
import ai.elimu.model.v2.enums.content.sound.ConsonantVoicing;
import ai.elimu.model.v2.enums.content.sound.LipRounding;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import ai.elimu.model.v2.enums.content.sound.VowelFrontness;
import ai.elimu.model.v2.enums.content.sound.VowelHeight;
import ai.elimu.model.v2.enums.content.sound.VowelLength;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Speech sound
 */
@Getter
@Setter
@Entity
public class Sound extends Content {

  /**
   * IPA - International Phonetic Alphabet
   * <p/>
   * See https://en.wikipedia.org/wiki/International_Phonetic_Alphabet#Letters
   */
  @NotNull
  @Size(max = 3)
  @Column(length = 3)
  private String valueIpa;

  /**
   * X-SAMPA - Extended Speech Assessment Methods Phonetic Alphabet.
   * <p/>
   * See https://en.wikipedia.org/wiki/X-SAMPA#Lower_case_symbols
   * <p/>
   * X-SAMPA is used to enable integration with Text-to-Speech (TTS) technology.
   */
  @NotNull
  @Size(max = 5)
  @Column(length = 5)
  private String valueSampa;

  //    @NotNull
  @OneToOne
  private Audio audio;

  private boolean diacritic;

  @Enumerated(EnumType.STRING)
  private SoundType soundType;

  /**
   * Only used if {@code soundType == SoundType.VOWEL}
   */
  @Enumerated(EnumType.STRING)
  private VowelLength vowelLength;

  /**
   * Only used if {@code soundType == SoundType.VOWEL}
   */
  @Enumerated(EnumType.STRING)
  private VowelHeight vowelHeight;

  /**
   * Only used if {@code soundType == SoundType.VOWEL}
   */
  @Enumerated(EnumType.STRING)
  private VowelFrontness vowelFrontness;

  @Enumerated(EnumType.STRING)
  private LipRounding lipRounding;

  /**
   * Only used if {@code soundType == SoundType.CONSONANT}
   */
  @Enumerated(EnumType.STRING)
  private ConsonantType consonantType;

  /**
   * Only used if {@code soundType == SoundType.CONSONANT}
   */
  @Enumerated(EnumType.STRING)
  private ConsonantPlace consonantPlace;

  /**
   * Only used if {@code soundType == SoundType.CONSONANT}
   */
  @Enumerated(EnumType.STRING)
  private ConsonantVoicing consonantVoicing;
}
