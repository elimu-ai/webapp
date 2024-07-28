package ai.elimu.model.contributor;

import ai.elimu.model.content.multimedia.Audio;
import javax.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class AudioContributionEvent extends ContributionEvent {
    
    @NotNull
    @ManyToOne
    private Audio audio;

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }
}
