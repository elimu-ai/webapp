package ai.elimu.model.content;

import java.util.Calendar;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.enums.content.ContentStatus;

/**
 * Parent class for different types of educational content.
 */
@MappedSuperclass
public abstract class Content extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeLastUpdate;
    
    @NotNull
    private Integer revisionNumber = 1; // [1, 2, 3, ...]
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus = ContentStatus.ACTIVE;

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
