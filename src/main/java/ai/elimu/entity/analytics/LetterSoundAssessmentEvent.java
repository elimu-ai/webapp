package ai.elimu.entity.analytics;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LetterSoundAssessmentEvent extends AssessmentEvent {

    /**
     * The sequence of letters. E.g. <code>["s", "h"]</code>.
     */
    private String[] letterSoundLetters;

    /**
     * The sequence of sounds (IPA values). E.g. <code>["ʃ"]</code>.
     */
    private String [] letterSoundSounds;

    /**
     * This field might not be included, e.g. if the assessment task was done in a 
     * 3rd-party app that did not load the content from the elimu.ai Content Provider. 
     * In this case, the {@link #letterSoundId} will be {@code null}.
     */
    private Long letterSoundId;

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
