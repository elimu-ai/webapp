package ai.elimu.entity.analytics;

import jakarta.persistence.Entity;
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

    /**
     * A value in the range [0.0, 1.0].
     */
    private Float masteryScore;

    /**
     * The number of milliseconds passed between the student opening the assessment task 
     * and submitting a response. E.g. <code>15000</code>.
     */
    private Long timeSpentMs;
}
