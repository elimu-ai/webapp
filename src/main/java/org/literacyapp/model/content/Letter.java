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
    
    private int usageCount; // Based on StoryBook content

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
