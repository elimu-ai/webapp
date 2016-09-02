package org.literacyapp.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.literacyapp.model.enums.Locale;

@Entity
public class Student extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @NotEmpty
    @OneToMany
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
}
