package ai.elimu.entity.analytics;

import ai.elimu.entity.content.Word;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class WordAssessmentEvent extends AssessmentEvent {

    /**
     * The word text. E.g. <code>"star"</code>.
     */
    private String wordText;

    /**
     * This field might not be included, e.g. if the assessment task was done in a 
     * 3rd-party app that did not load the content from the elimu.ai Content Provider. 
     * In this case, the {@link #wordId} will be {@code null}.
     */
    private Long wordId;

    @ManyToOne
    private Word word;
}
