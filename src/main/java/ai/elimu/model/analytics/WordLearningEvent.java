package ai.elimu.model.analytics;

import ai.elimu.model.content.Word;
import ai.elimu.model.enums.analytics.LearningEventType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class WordLearningEvent extends LearningEvent {
    
    @ManyToOne
    private Word word;
    
    /**
     * A Word's text value is used as a fall-back if the Android application did not use a Word ID.
     */
    private String wordText;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private LearningEventType learningEventType;

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }

    public LearningEventType getLearningEventType() {
        return learningEventType;
    }

    public void setLearningEventType(LearningEventType learningEventType) {
        this.learningEventType = learningEventType;
    }
}
