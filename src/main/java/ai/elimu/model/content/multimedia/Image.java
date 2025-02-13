package ai.elimu.model.content.multimedia;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import ai.elimu.model.v2.enums.content.ImageFormat;

@Entity
public class Image extends Multimedia {

    @NotNull
    private String title;
    
    @Deprecated
    @Basic(fetch = FetchType.LAZY)
    @NotNull
    @Lob
    @Column(length=10485760) // 10MB
    private byte[] bytes;

    /**
     * IPFS Content Identifier (CID). Based on the file content's cryptographic hash.
     */
    // @NotNull
    private String cid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ImageFormat imageFormat;
    
//    @NotNull
    private String dominantColor; // Web color

    public String getUrl() {
        if (cid != null) {
            return "https://black-historic-wren-832.mypinata.cloud/ipfs/" + cid + "?img-width=640";
        } else {
            return "/image/" + getId() + "_r" + getRevisionNumber() + "." + imageFormat.toString().toLowerCase();
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Deprecated
    public byte[] getBytes() {
        return bytes;
    }

    @Deprecated
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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
