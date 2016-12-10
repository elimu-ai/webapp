package org.literacyapp.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.literacyapp.model.enums.Locale;

@Entity
public class Student extends BaseEntity {
    
    @NotNull
    @Column(unique = true)
    private String uniqueId; // "<deviceId>_<Long>"

    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @NotEmpty
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Device> devices;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
