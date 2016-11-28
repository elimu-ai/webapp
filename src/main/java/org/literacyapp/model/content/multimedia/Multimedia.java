package org.literacyapp.model.content.multimedia;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.literacyapp.model.content.Content;
import org.literacyapp.model.enums.ContentLicense;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;

/**
 * Parent class for different types of multimedia (images, audios, etc).
 */
@Entity
// TODO: change to abstract class
public class Multimedia extends Content {
    
    @NotNull
    private String contentType;
    
    @Enumerated(EnumType.STRING)
    private ContentLicense contentLicense;
    
    @Length(max = 1000)
    @Column(length = 1000)
    private String attributionUrl;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<LiteracySkill> literacySkills;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<NumeracySkill> numeracySkills;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    public ContentLicense getContentLicense() {
        return contentLicense;
    }

    public void setContentLicense(ContentLicense contentLicense) {
        this.contentLicense = contentLicense;
    }

    public String getAttributionUrl() {
        return attributionUrl;
    }

    public void setAttributionUrl(String attributionUrl) {
        this.attributionUrl = attributionUrl;
    }

    public Set<LiteracySkill> getLiteracySkills() {
        return literacySkills;
    }

    public void setLiteracySkills(Set<LiteracySkill> literacySkills) {
        this.literacySkills = literacySkills;
    }

    public Set<NumeracySkill> getNumeracySkills() {
        return numeracySkills;
    }

    public void setNumeracySkills(Set<NumeracySkill> numeracySkills) {
        this.numeracySkills = numeracySkills;
    }
}
