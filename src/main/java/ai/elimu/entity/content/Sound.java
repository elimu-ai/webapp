package ai.elimu.entity.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import ai.elimu.entity.content.multimedia.Audio;
import org.hibernate.validator.constraints.Length;
import ai.elimu.model.v2.enums.content.sound.ConsonantPlace;
import ai.elimu.model.v2.enums.content.sound.ConsonantType;
import ai.elimu.model.v2.enums.content.sound.ConsonantVoicing;
import ai.elimu.model.v2.enums.content.sound.LipRounding;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import ai.elimu.model.v2.enums.content.sound.VowelFrontness;
import ai.elimu.model.v2.enums.content.sound.VowelHeight;
import ai.elimu.model.v2.enums.content.sound.VowelLength;

/**
 * Speech sound
 */
@Entity
public class Sound extends Content {
    
    /**
     * IPA - International Phonetic Alphabet
     * <p />
     * See https://en.wikipedia.org/wiki/International_Phonetic_Alphabet#Letters
     */
    @NotNull
    @Length(max = 3)
    @Column(length = 3)
    private String valueIpa;
    
    /**
     * X-SAMPA - Extended Speech Assessment Methods Phonetic Alphabet.
     * <p />
     * See https://en.wikipedia.org/wiki/X-SAMPA#Lower_case_symbols
     * <p />
     * X-SAMPA is used to enable integration with Text-to-Speech (TTS) technology.
     */
    @NotNull
    @Length(max = 5)
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

    public String getValueIpa() {
        return valueIpa;
    }

    public void setValueIpa(String valueIpa) {
        this.valueIpa = valueIpa;
    }

    public String getValueSampa() {
        return valueSampa;
    }

    public void setValueSampa(String valueSampa) {
        this.valueSampa = valueSampa;
    }
    
    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public boolean isDiacritic() {
        return diacritic;
    }

    public void setDiacritic(boolean diacritic) {
        this.diacritic = diacritic;
    }
    
    public SoundType getSoundType() {
        return soundType;
    }

    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
    }

    public VowelLength getVowelLength() {
        return vowelLength;
    }

    public void setVowelLength(VowelLength vowelLength) {
        this.vowelLength = vowelLength;
    }

    public VowelHeight getVowelHeight() {
        return vowelHeight;
    }

    public void setVowelHeight(VowelHeight vowelHeight) {
        this.vowelHeight = vowelHeight;
    }

    public VowelFrontness getVowelFrontness() {
        return vowelFrontness;
    }

    public void setVowelFrontness(VowelFrontness vowelFrontness) {
        this.vowelFrontness = vowelFrontness;
    }

    public LipRounding getLipRounding() {
        return lipRounding;
    }

    public void setLipRounding(LipRounding lipRounding) {
        this.lipRounding = lipRounding;
    }

    public ConsonantType getConsonantType() {
        return consonantType;
    }

    public void setConsonantType(ConsonantType consonantType) {
        this.consonantType = consonantType;
    }

    public ConsonantPlace getConsonantPlace() {
        return consonantPlace;
    }

    public void setConsonantPlace(ConsonantPlace consonantPlace) {
        this.consonantPlace = consonantPlace;
    }

    public ConsonantVoicing getConsonantVoicing() {
        return consonantVoicing;
    }

    public void setConsonantVoicing(ConsonantVoicing consonantVoicing) {
        this.consonantVoicing = consonantVoicing;
    }
}
