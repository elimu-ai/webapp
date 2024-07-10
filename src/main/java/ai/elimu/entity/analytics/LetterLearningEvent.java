package ai.elimu.entity.analytics;

import ai.elimu.entity.content.Letter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class LetterLearningEvent extends LearningEvent {
    
    @ManyToOne
    private Letter letter;
    
    /**
     * A {@link Letter}'s text value is used as a fall-back if the Android application did not use a Letter ID. 
     * This can happen if the learning event occurred within a 3rd-party application which is not integrated with the 
     * elimu.ai Content Provider.
     */
    @NotNull
    private String letterText;

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public String getLetterText() {
        return letterText;
    }

    public void setLetterText(String letterText) {
        this.letterText = letterText;
    }
}
