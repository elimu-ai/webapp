package ai.elimu.model.content.multimedia;

import ai.elimu.model.v2.enums.content.VideoFormat;
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

  @NotNull
  @Lob
  @Column(length = 209715200) // 200MB
  private byte[] bytes;

  @NotNull
  @Lob
  @Column(length = 1048576) // 1MB
  private byte[] thumbnail;

  @NotNull
  @Enumerated(EnumType.STRING)
  private VideoFormat videoFormat;
}
