package ai.elimu.model.content;

import java.util.Calendar;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.v2.enums.content.ContentStatus;
import ai.elimu.model.enums.PeerReviewStatus;

/**
 * Parent class for different types of educational content.
 */
@MappedSuperclass
public abstract class Content extends BaseEntity {

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeLastUpdate;
    
    @NotNull
    private Integer revisionNumber = 1; // [1, 2, 3, ...]
    
    /**
     * See UsageCountSchedulers in {@link ai.elimu.tasks} for details on how this value is being updated on a regular 
     * basis.
     */
    @NotNull
    private Integer usageCount = 0;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ContentStatus contentStatus = ContentStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private PeerReviewStatus peerReviewStatus = PeerReviewStatus.PENDING;

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
    
    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }

    public PeerReviewStatus getPeerReviewStatus() {
        return peerReviewStatus;
    }

    public void setPeerReviewStatus(PeerReviewStatus peerReviewStatus) {
        this.peerReviewStatus = peerReviewStatus;
    }
}
