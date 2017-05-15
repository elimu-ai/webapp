package org.literacyapp.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
public class Letter extends Content {

    @NotNull
    @Length(max = 1)
    @Column(length = 1)
    private String text;
    
    @Length(max = 1)
    @Column(length = 1)
    private String braille;
    
    private int usageCount; // Based on StoryBook content (all difficulty levels)

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public String getBraille() {
        return braille;
    }

    public void setBraille(String braille) {
        this.braille = braille;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
