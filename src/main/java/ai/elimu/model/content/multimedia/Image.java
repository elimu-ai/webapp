package ai.elimu.model.content.multimedia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import ai.elimu.model.enums.content.ImageFormat;

@Entity
public class Image extends Multimedia {

    @NotNull
    private String title;
    
    @NotNull
    @Lob
    @Column(length=10485760) // 10MB
    private byte[] bytes;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private ImageFormat imageFormat;
    
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

    public ImageFormat getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(ImageFormat imageFormat) {
        this.imageFormat = imageFormat;
    }

    public String getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(String dominantColor) {
        this.dominantColor = dominantColor;
    }
}
