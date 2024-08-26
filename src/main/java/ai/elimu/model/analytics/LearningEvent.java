package ai.elimu.model.analytics;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.admin.Application;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import java.util.Calendar;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
     * The package name of the {@link #application}.
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

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    public String getAndroidId() {
        if (!androidId.contains("***")) {
            // Hide parts of the Android ID, e.g. "7161a85a0e4751cd" --> "7161***51cd"
            return androidId.substring(0, 4) + "***" + androidId.substring(12);
        } else {
            return androidId;
        }
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
}
