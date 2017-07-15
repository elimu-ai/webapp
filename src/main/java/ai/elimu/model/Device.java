package ai.elimu.model;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import ai.elimu.model.enums.Locale;

@Entity
public class Device extends BaseEntity {

    @NotNull
    @Column(unique=true)
    private String deviceId;
    
    @NotNull
    private String deviceManufacturer;
    
    @NotNull
    private String deviceModel;
    
    @NotNull
    private String deviceSerial;
    
    // TODO: @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeRegistered; // Time of first synchronization with server
    
    @NotNull
    private String remoteAddress; // IP address during registration
    
    @NotNull
    private Integer osVersion;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Device> devicesNearby = new HashSet<>();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
    
    public String getDeviceSerial() {
        return deviceSerial;
    }

    public void setDeviceSerial(String deviceSerial) {
        this.deviceSerial = deviceSerial;
    }

    public Calendar getTimeRegistered() {
        return timeRegistered;
    }

    public void setTimeRegistered(Calendar timeRegistered) {
        this.timeRegistered = timeRegistered;
    }
    
    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public Integer getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(Integer osVersion) {
        this.osVersion = osVersion;
    }
    
    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Set<Device> getDevicesNearby() {
        return devicesNearby;
    }

    public void setDevicesNearby(Set<Device> devicesNearby) {
        this.devicesNearby = devicesNearby;
    }
}
