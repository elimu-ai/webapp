package ai.elimu.model.content.multimedia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import ai.elimu.model.v2.enums.content.VideoFormat;

@Entity
public class Video extends Multimedia {
    
    @NotNull
    private String title;
    
    @NotNull
    @Lob
    @Column(length=209715200) // 200MB
    private byte[] bytes;
    
    @NotNull
    @Lob
    @Column(length=1048576) // 1MB
    private byte[] thumbnail;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private VideoFormat videoFormat;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    public VideoFormat getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(VideoFormat videoFormat) {
        this.videoFormat = videoFormat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
