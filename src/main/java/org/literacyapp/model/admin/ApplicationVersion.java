package org.literacyapp.model.admin;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.BaseEntity;
import org.literacyapp.model.Contributor;

@Entity
public class ApplicationVersion extends BaseEntity {
    
    @ManyToOne
    private Application application;
    
    @NotNull
    @Lob
    @Column(length=104857600) // 100MB
    private byte[] bytes;
    
    @NotNull
    private String contentType;
    
    @NotNull
    private Integer versionCode;
    
    // TODO: minSdk
    
    private String startCommand; // "adb shell <command>"
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar timeUploaded;
    
    @ManyToOne
    private Contributor contributor;

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }
    
    public String getStartCommand() {
        return startCommand;
    }

    public void setStartCommand(String startCommand) {
        this.startCommand = startCommand;
    }

    public Calendar getTimeUploaded() {
        return timeUploaded;
    }

    public void setTimeUploaded(Calendar timeUploaded) {
        this.timeUploaded = timeUploaded;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }
}
