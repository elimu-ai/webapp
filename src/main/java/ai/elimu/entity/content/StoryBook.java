package ai.elimu.entity.content;

import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoryBook extends Content {

  @NotNull
  private String title;

  @Column(length = 1024)
  private String description;

  @Enumerated(EnumType.STRING)
  private ContentLicense contentLicense;

  @Size(max = 1000)
  @Column(length = 1000)
  private String attributionUrl;

  //    @NotNull
  @ManyToOne
  private Image coverImage;

  //    @NotNull
  @Enumerated(EnumType.STRING)
  private ReadingLevel readingLevel;
}
