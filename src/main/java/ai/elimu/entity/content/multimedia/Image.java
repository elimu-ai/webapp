package ai.elimu.entity.content.multimedia;

import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image extends Multimedia {

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
   * Content Identifier (CID). Based on the file content's GitHub hash.
   */
  // @NotNull
  private String cid;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ImageFormat imageFormat;

  //    @NotNull
  private String dominantColor; // Web color

  public String getUrl() {
    return "https://raw.githubusercontent.com/elimu-ai/webapp-lfs/main" +
        "/lang-" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language") +
        "/images" +
        "/" + getChecksumMd5() + "." + getImageFormat().toString().toLowerCase();
  }
}
