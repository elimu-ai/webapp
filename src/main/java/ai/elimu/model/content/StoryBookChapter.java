package ai.elimu.model.content;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.content.multimedia.Image;
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
