package ai.elimu.model.content.multimedia;

import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import ai.elimu.model.v2.enums.content.AudioFormat;
import jakarta.persistence.ManyToOne;

@Entity
public class Audio extends Multimedia {
    
    /**
     * Will be used if the Audio recording was made for a particular {@link Word}.
     */
    @ManyToOne
    private Word word;
    
    /**
     * Will be used if the Audio recording was made for a particular {@link StoryBookParagraph}.
     */
    @ManyToOne
    private StoryBookParagraph storyBookParagraph;
    
    /**
     * A title describing the audio recording. This does not have match the 
     * audio's actual content.
     */
    @NotNull
    private String title;
    
    /**
     * The actual content of the audio recording.
     */
    @NotNull
    private String transcription;
    
    @NotNull
    @Lob
    @Column(length=209715200) // 200MB
    private byte[] bytes;
    
    /**
     * The duration of the audio recording in milliseconds.
     */
    private Long durationMs;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AudioFormat audioFormat;

    public Word getWord() {
        return word;
    }
    
    public void setWord(Word word) {
        this.word = word;
    }
    
    public StoryBookParagraph getStoryBookParagraph() {
        return storyBookParagraph;
    }
    
    public void setStoryBookParagraph(StoryBookParagraph storyBookParagraph) {
        this.storyBookParagraph = storyBookParagraph;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
    public Long getDurationMs() {
        return durationMs;
    }
    
    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }
}
