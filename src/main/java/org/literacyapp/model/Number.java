package org.literacyapp.model;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.Locale;

@Entity
public class Number extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;

    @NotNull
    private Integer value;
    
    private String symbol;
    
    @OneToOne
    private Word word;
    
    private int[] dominantColor; // RGB array
    
    @OneToOne
    private Contributor contributor;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public int[] getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(int[] dominantColor) {
        this.dominantColor = dominantColor;
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
}
