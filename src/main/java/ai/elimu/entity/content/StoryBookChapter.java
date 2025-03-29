package ai.elimu.entity.content;

import ai.elimu.entity.BaseEntity;
import ai.elimu.entity.content.multimedia.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoryBookChapter extends BaseEntity {

  //    @NotNull
  @ManyToOne
  private StoryBook storyBook;

  /**
   * [0, 1, 2, ...]
   */
  @NotNull
  private Integer sortOrder;

  @ManyToOne
  private Image image;
}
