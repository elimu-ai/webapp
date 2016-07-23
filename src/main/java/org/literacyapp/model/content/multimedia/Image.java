package org.literacyapp.model.content.multimedia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.content.ImageType;

@Entity
public class Image extends Multimedia {

    @NotNull
    private String title;
    
    @NotNull
    @Lob
    @Column(length=4194304) // 4MB
    private byte[] bytes;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ImageType imageType;
    
    @NotNull
    private String dominantColor; // Web color

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }
}
