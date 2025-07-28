package ai.elimu.entity.analytics;

import java.util.List;

import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.converters.StringListConverter;
import jakarta.persistence.Convert;
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
    @Convert(converter = StringListConverter.class)
    private List<String> letterSoundLetters;

    /**
     * The sequence of sounds (IPA values). E.g. <code>"ʃ"</code>.
     */
    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> letterSoundSounds;

    /**
     * This field might not be included, e.g. if the assessment task was done in a 
     * 3rd-party app that did not load the content from the elimu.ai Content Provider. 
     * In this case, the {@link #letterSoundId} will be {@code null}.
     */
    private Long letterSoundId;

    @ManyToOne
    private LetterSound letterSound;
}
