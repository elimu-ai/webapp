package ai.elimu.entity.contributor;

import ai.elimu.entity.content.multimedia.Image;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ImageContributionEvent extends ContributionEvent {

  @NotNull
  @ManyToOne
  private Image image;
}
