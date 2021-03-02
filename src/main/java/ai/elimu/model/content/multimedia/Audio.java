package ai.elimu.model.content.multimedia;

import ai.elimu.model.content.Word;
import ai.elimu.model.enums.PeerReviewStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import ai.elimu.model.enums.content.AudioFormat;
import javax.persistence.ManyToOne;

@Entity
public class Audio extends Multimedia {
    
    /**
     * Will be used if the Audio recording was made for a particular {@link Word}.
     */
    @ManyToOne
    private Word word;
    
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
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AudioFormat audioFormat;
    
    @Enumerated(EnumType.STRING)
    private PeerReviewStatus peerReviewStatus = PeerReviewStatus.PENDING;
    
    public Word getWord() {
        return word;
    }
    
    public void setWord(Word word) {
        this.word = word;
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

    public AudioFormat getAudioFormat() {
        return audioFormat;
    }

    public void setAudioFormat(AudioFormat audioFormat) {
        this.audioFormat = audioFormat;
    }

    public PeerReviewStatus getPeerReviewStatus() {
        return peerReviewStatus;
    }

    public void setPeerReviewStatus(PeerReviewStatus peerReviewStatus) {
        this.peerReviewStatus = peerReviewStatus;
    }
}
