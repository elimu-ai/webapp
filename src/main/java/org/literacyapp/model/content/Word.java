package org.literacyapp.model.content;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Word extends Content {

    @NotNull
    private String text;
    
    @NotNull
    private String phonetics; // IPA
    
    private int usageCount; // Based on StoryBook content

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
}
