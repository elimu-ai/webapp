package ai.elimu.entity.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderColumn;
import jakarta.validation.constraints.NotNull;
import java.util.List;

import ai.elimu.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class StoryBookParagraph extends BaseEntity {

  @ManyToOne
  private StoryBookChapter storyBookChapter;

  /**
   * [0, 1, 2, ...]
   */
  @NotNull
  private Integer sortOrder;

  @NotNull
  @Column(length = 1024)
  private String originalText;

  @OrderColumn
  @ManyToMany(fetch = FetchType.EAGER)
  private List<Word> words;
}
