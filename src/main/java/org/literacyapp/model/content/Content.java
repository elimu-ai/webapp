package org.literacyapp.model.content;

import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.BaseEntity;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.content.ContentStatus;

/**
 * Parent class for different types of educational content.
 */
@Entity
public abstract class Content extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeLastUpdate;
    
    @NotNull
    private Integer revisionNumber; // [1, 2, 3, ...]
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Calendar getTimeLastUpdate() {
        return timeLastUpdate;
    }

    public void setTimeLastUpdate(Calendar timeLastUpdate) {
        this.timeLastUpdate = timeLastUpdate;
    }

    public Integer getRevisionNumber() {
        return revisionNumber;
    }

    public void setRevisionNumber(Integer revisionNumber) {
        this.revisionNumber = revisionNumber;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }
}
