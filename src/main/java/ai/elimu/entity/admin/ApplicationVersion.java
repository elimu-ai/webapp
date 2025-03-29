package ai.elimu.entity.admin;

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
   * Do not use this property to calculate the size of the APK file. Instead, use the {@link #fileSizeInKb} property.
   */
  @NotNull
  @Lob
  @Column(length = 1073741824) // 1024MB
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
}
