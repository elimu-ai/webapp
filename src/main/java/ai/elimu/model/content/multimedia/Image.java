package ai.elimu.model.content.multimedia;

import ai.elimu.model.v2.enums.content.ImageFormat;
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
   * IPFS Content Identifier (CID). Based on the file content's cryptographic hash.
   */
  // @NotNull
  private String cid;

  @NotNull
  @Enumerated(EnumType.STRING)
  private ImageFormat imageFormat;

  //    @NotNull
  private String dominantColor; // Web color

  public String getUrl() {
    if (cid != null) {
      return "https://black-historic-wren-832.mypinata.cloud/ipfs/" + cid + "?img-width=640";
    } else {
      return "/image/" + getId() + "_r" + getRevisionNumber() + "." + imageFormat.toString().toLowerCase();
    }
  }
}
