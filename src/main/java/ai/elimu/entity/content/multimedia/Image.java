package ai.elimu.entity.content.multimedia;

import ai.elimu.model.v2.enums.content.ImageFormat;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Image extends Multimedia {

  @NotNull
  private String title;

  @Deprecated
  @Basic(fetch = FetchType.LAZY)
  @NotNull
  @Lob
  @Column(length = 10485760) // 10MB
  private byte[] bytes;

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
    String filename = getId() + "_r" + getRevisionNumber() + "." + getImageFormat().toString().toLowerCase();
    if (cid != null) {
      return "https://raw.githubusercontent.com/elimu-ai/webapp-lfs/main/" +
          "lang-" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language") + "/" +
          "images/" +
          filename;
    } else {
      return "/image/" + filename;
    }
  }
}
