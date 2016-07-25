package org.literacyapp.model;

import java.util.Calendar;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

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
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeRegistered; // Time of first synchronization with server
    
    @NotNull
    private Integer osVersion;
    
    @NotNull
    private String locale; // Expected format "en"/"en-US"
    
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Device> devicesNearby;

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

    public Integer getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(Integer osVersion) {
        this.osVersion = osVersion;
    }
    
    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Set<Device> getDevicesNearby() {
        return devicesNearby;
    }

    public void setDevicesNearby(Set<Device> devicesNearby) {
        this.devicesNearby = devicesNearby;
    }
}
