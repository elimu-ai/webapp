package ai.elimu.model.analytics;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.admin.Application;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import java.util.Calendar;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class LearningEvent extends BaseEntity {
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timestamp;
    
    /**
     * See https://developer.android.com/reference/android/provider/Settings.Secure#ANDROID_ID
     */
    @NotNull
    private String androidId;
    
    /**
     * The package name of the {@link #application} where the learning event occurred.
     */
    @NotNull
    private String packageName;
    
    /**
     * This field will only be populated if a corresponding {@link Application} can be 
     * found in the database for the {@link #packageName}.
     */
    @ManyToOne
    private Application application;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private LearningEventType learningEventType;

    /**
     * Any additional data should be stored in the format of a JSON object.
     * </p>
     * 
     * Example:
     * <pre>
     * {'video_playback_position_ms': 27946}
     * </pre>
     */
    @Column(length = 1024)
    private String additionalData;

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }
}
