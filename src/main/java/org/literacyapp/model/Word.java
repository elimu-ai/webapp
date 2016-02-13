package org.literacyapp.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.Language;

@Entity
public class Word extends BaseEntity {
    
    @NotNull
    private Language language;

    @NotNull
    private String text;
    
    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
