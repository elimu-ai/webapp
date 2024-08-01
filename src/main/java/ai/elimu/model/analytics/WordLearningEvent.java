package ai.elimu.model.analytics;

import ai.elimu.model.content.Word;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class WordLearningEvent extends LearningEvent {
    
    @ManyToOne
    private Word word;
    
    /**
     * A Word's text value is used as a fall-back if the Android application did not use a Word ID.
     */
//    @NotNull
    private String wordText;

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
}
