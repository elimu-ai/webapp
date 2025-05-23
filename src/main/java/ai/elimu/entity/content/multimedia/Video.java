package ai.elimu.entity.content.multimedia;

import ai.elimu.model.v2.enums.content.VideoFormat;
import ai.elimu.util.GitHubLfsHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Video extends Multimedia {

  @NotNull
  private String title;

  /**
   * The file size (byte length).
   */
  @NotNull
  private Integer fileSize;

  /**
   * MD5 checksum of the file content.
   */
  @NotNull
  private String checksumMd5;

  /**
   * The blob SHA of the file. This value is returned from GitHub when creating new repository file
   * content via their REST API (see {@link GitHubLfsHelper}).
   */
  @NotNull
  private String checksumGitHub;

  @Deprecated
  @NotNull
  @Lob
  @Column(length = 1048576) // 1MB
  private byte[] thumbnail;

  @NotNull
  @Enumerated(EnumType.STRING)
  private VideoFormat videoFormat;

  public String getUrl() {
    return "https://raw.githubusercontent.com/elimu-ai/webapp-lfs/main" +
        "/lang-" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language") +
        "/videos" +
        "/" + getChecksumMd5() + "." + getVideoFormat().toString().toLowerCase();
  }
}
