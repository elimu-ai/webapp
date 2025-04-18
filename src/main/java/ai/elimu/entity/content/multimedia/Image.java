package ai.elimu.entity.content.multimedia;

import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import io.micrometer.common.util.StringUtils;
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
    String filename = getChecksumMd5() + "." + getImageFormat().toString().toLowerCase();
    if (StringUtils.isBlank(getCid())) {
      filename = getId() + "_r" + getRevisionNumber() + "." + getImageFormat().toString().toLowerCase();
    }
    return "https://raw.githubusercontent.com/elimu-ai/webapp-lfs/main" +
        "/lang-" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language") +
        "/images" +
        "/" + filename;
  }
}
