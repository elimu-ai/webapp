package ai.elimu.entity.contributor;

import ai.elimu.entity.content.multimedia.Audio;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
