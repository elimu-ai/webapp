package ai.elimu.model.analytics;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.admin.Application;
import java.util.Calendar;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class LearningEvent extends BaseEntity {
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;
    
    /**
     * See https://developer.android.com/reference/android/provider/Settings.Secure#ANDROID_ID
     */
    @NotNull
    private String androidId;
    
    @ManyToOne
    private Application application;

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public String getAndroidId() {
        return androidId;
    }

    public void setAndroidId(String androidId) {
        this.androidId = androidId;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
