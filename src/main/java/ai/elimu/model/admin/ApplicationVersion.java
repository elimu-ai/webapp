package ai.elimu.model.admin;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.Contributor;

@Entity
public class ApplicationVersion extends BaseEntity {
    
    @ManyToOne
    private Application application;
    
    /**
     * Do not use this property to calculate the size of the APK file. Instead, use the 
     * {@link #fileSizeInKb} property.
     */
    @NotNull
    @Lob
    @Column(length=524288000) // 500MB
    private byte[] bytes;
    
    /**
     * This property is used to prevent calls to {@link #bytes} just to get the size of the APK file.
     */
    @NotNull
    private Integer fileSizeInKb;
    
    /**
     * MD5 checksum of the APK file.
     */
    @NotNull
    private String checksumMd5;
    
    @NotNull
    private String contentType;
    
    @NotNull
    private Integer versionCode;
    
    @NotNull
    private String versionName;
    
    @NotNull
    private String label;
    
    @NotNull
    @Lob
    @Column(length=512000) // 1MB
    private byte[] icon;
    
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
    
    public Integer getFileSizeInKb() {
        return fileSizeInKb;
    }

    public void setFileSizeInKb(Integer fileSizeInKb) {
        this.fileSizeInKb = fileSizeInKb;
    }
    
    public String getChecksumMd5() {
        return checksumMd5;
    }

    public void setChecksumMd5(String checksumMd5) {
        this.checksumMd5 = checksumMd5;
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
    
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
    
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
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
