package ai.elimu.model.content.multimedia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import ai.elimu.model.enums.content.AudioFormat;

@Entity
public class Audio extends Multimedia {
    
    @NotNull
    private String transcription;
    
    @NotNull
    @Lob
    @Column(length=209715200) // 200MB
    private byte[] bytes;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private AudioFormat audioFormat;
    
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
}
