package org.literacyapp.model.contributor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.content.Content;

@Entity
public class ContentCreationEvent extends ContributorEvent {
    
    @NotNull
    @ManyToOne
    private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
