package org.literacyapp.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.literacyapp.model.enums.content.allophone.ConsonantPlace;
import org.literacyapp.model.enums.content.allophone.ConsonantType;
import org.literacyapp.model.enums.content.allophone.ConsonantVoicing;
import org.literacyapp.model.enums.content.allophone.LipRounding;
import org.literacyapp.model.enums.content.allophone.SoundType;
import org.literacyapp.model.enums.content.allophone.VowelFrontness;
import org.literacyapp.model.enums.content.allophone.VowelHeight;
import org.literacyapp.model.enums.content.allophone.VowelLength;

/**
 * Speech sound
 */
@Entity
public class Allophone extends Content {
    
    @NotNull
    @Length(max = 3)
    @Column(length = 3)
    private String valueIpa; // IPA - International Phonetic Alphabet
    
    @NotNull
    @Length(max = 3)
    @Column(length = 3)
    private String valueSampa; // X-SAMPA - Extended Speech Assessment Methods Phonetic Alphabet
    
    private boolean diacritic;
    
    @Enumerated(EnumType.STRING)
    private SoundType soundType;
    
    @Enumerated(EnumType.STRING)
    private VowelLength vowelLength;
  
    @Enumerated(EnumType.STRING)
    private VowelHeight vowelHeight;

    @Enumerated(EnumType.STRING)
    private VowelFrontness vowelFrontness;
    
    @Enumerated(EnumType.STRING)
    private LipRounding lipRounding;
    
    @Enumerated(EnumType.STRING)
    private ConsonantType consonantType;
    
    @Enumerated(EnumType.STRING)
    private ConsonantPlace consonantPlace;
    
    @Enumerated(EnumType.STRING)
    private ConsonantVoicing consonantVoicing;
    
    private int usageCount; // Based on StoryBook content

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

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
