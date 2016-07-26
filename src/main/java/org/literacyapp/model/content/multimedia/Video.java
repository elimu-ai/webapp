package org.literacyapp.model.content.multimedia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.content.VideoFormat;

@Entity
public class Video extends Multimedia {
    
    private String title;
    
    @NotNull
    @Lob
    @Column(length=104857600) // 100MB
    private byte[] bytes;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private VideoFormat videoFormat;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
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
