package ai.elimu.model.analytics;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.content.Letter;

@Entity
public class LetterLearningEvent extends LearningEvent {
    
    @NotNull
    @ManyToOne
    private Letter letter;

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }
}
