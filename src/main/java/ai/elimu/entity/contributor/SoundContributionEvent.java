package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Sound;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class SoundContributionEvent extends ContributionEvent {
    
    @NotNull
    @ManyToOne
    private Sound sound;

    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
}
