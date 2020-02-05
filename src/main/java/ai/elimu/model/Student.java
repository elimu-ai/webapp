package ai.elimu.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import ai.elimu.model.enums.Locale;

@Entity
public class Student extends BaseEntity {
    
    @NotNull
    @Column(unique = true)
    private String uniqueId; // "<deviceId>_<Long>"
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeCreated; // Time created on Device
    
    // TODO: avatar

    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
    
    public Calendar getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Calendar timeCreated) {
        this.timeCreated = timeCreated;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
