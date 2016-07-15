package org.literacyapp.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.ConsonantPlace;
import org.literacyapp.model.enums.ConsonantType;
import org.literacyapp.model.enums.ConsonantVoicing;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.LipRounding;
import org.literacyapp.model.enums.SoundType;
import org.literacyapp.model.enums.VowelFrontness;
import org.literacyapp.model.enums.VowelHeight;
import org.literacyapp.model.enums.VowelLength;

/**
 * Speech sound
 */
@Entity
public class Allophone extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @NotNull
    // TODO: @Column(length = 1)
    private String valueIpa; // IPA - International Phonetic Alphabet
    
    @NotNull
    // TODO: @Column(length = 1)
    private String valueSampa; // X-SAMPA - Extended Speech Assessment Methods Phonetic Alphabet
    
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
    
    @OneToOne
    private Contributor contributor;
    
    // TODO: @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

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

    public SoundType getSoundType() {
        return soundType;
    }

    public void setSoundType(SoundType soundType) {
        this.soundType = soundType;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
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
