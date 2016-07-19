package org.literacyapp.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Parent class for different types of multimedia (images, audios, etc).
 */
@Entity
public class Multimedia extends Content {
    
    @NotNull
    private String contentType;
    
    // TODO: license type
    
    @Column(length = 1000)
    private String attributionUrl;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getAttributionUrl() {
        return attributionUrl;
    }

    public void setAttributionUrl(String attributionUrl) {
        this.attributionUrl = attributionUrl;
    }
}
