package org.literacyapp.model.content;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.content.WordType;

@Entity
public class Word extends Content {

    @NotNull
    private String text;
    
    @NotNull
    private String phonetics; // IPA
    
    private int usageCount; // Based on StoryBook content
    
    @Enumerated(EnumType.STRING)
    private WordType wordType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPhonetics() {
        return phonetics;
    }

    public void setPhonetics(String phonetics) {
        this.phonetics = phonetics;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }
}
