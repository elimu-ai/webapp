package org.literacyapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.Language;

@Entity
public class Number extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

    @NotNull
    @Column(unique = true, length = 1)
    private Integer value;

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
