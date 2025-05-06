package ai.elimu.entity.application;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.util.Calendar;

import ai.elimu.entity.BaseEntity;
import ai.elimu.entity.contributor.Contributor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ApplicationVersion extends BaseEntity {

  @ManyToOne
  private Application application;

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

  @Deprecated
  @NotNull
  private String contentType;

  @NotNull
  private Integer versionCode;

  @NotNull
  private String versionName;

  @NotNull
  private String label;

  @NotNull
  private Integer minSdkVersion;

  @NotNull
  @Lob
  @Column(length = 512000) // 1MB
  private byte[] icon;

  @NotNull
  @Temporal(TemporalType.TIMESTAMP)
  private Calendar timeUploaded;

  @ManyToOne
  private Contributor contributor;

  /**
   * E.g. "https://github.com/elimu-ai/sound-cards/releases/download/2.1.0/ai.elimu.soundcards-2.1.0.apk"
   */
  public String getFileUrl() {
    return "https://github.com/elimu-ai" +
        "/" + getApplication().getRepoName() +
        "/releases" +
        "/download" +
        "/" + getVersionName() +
        "/" + getApplication().getPackageName() + "-" + getVersionName() + ".apk";
  }
}
