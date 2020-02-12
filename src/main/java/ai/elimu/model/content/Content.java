package ai.elimu.model.content;

import java.util.Calendar;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.ContentStatus;

/**
 * Parent class for different types of educational content.
 */
@MappedSuperclass
public abstract class Content extends BaseEntity {
    
    @Deprecated
    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeLastUpdate;
    
    @NotNull
    private Integer revisionNumber = 1; // [1, 2, 3, ...]
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus = ContentStatus.ACTIVE;
    
    @Deprecated
    public Language getLanguage() {
        return language;
    }
    
    @Deprecated
    public void setLanguage(Language language) {
        this.language = language;
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
