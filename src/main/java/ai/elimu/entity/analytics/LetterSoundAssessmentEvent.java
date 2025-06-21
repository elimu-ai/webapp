package ai.elimu.entity.analytics;

import ai.elimu.entity.content.LetterSound;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LetterSoundAssessmentEvent extends AssessmentEvent {

    /**
     * The sequence of letters. E.g. <code>"sh"</code>.
     */
    @NotNull
    private String letterSoundLetters;

    /**
     * The sequence of sounds (IPA values). E.g. <code>"ʃ"</code>.
     */
    @NotNull
    private String letterSoundSounds;

    /**
     * This field might not be included, e.g. if the assessment task was done in a 
     * 3rd-party app that did not load the content from the elimu.ai Content Provider. 
     * In this case, the {@link #letterSoundId} will be {@code null}.
     */
    private Long letterSoundId;

    @ManyToOne
    private LetterSound letterSound;
}
