package org.literacyapp.model;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.ImageType;
import org.literacyapp.model.enums.Language;

@Entity
public class Image extends BaseEntity {
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Language language;

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
    private String contentType;
    
    @NotNull
    private int[] dominantColor; // RGB array
    
    @OneToOne
    private Contributor contributor;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;
    
    private String attributionUrl;
    // TODO: store license type

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

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
    
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int[] getDominantColor() {
        return dominantColor;
    }

    public void setDominantColor(int[] dominantColor) {
        this.dominantColor = dominantColor;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public String getAttributionUrl() {
        return attributionUrl;
    }

    public void setAttributionUrl(String attributionUrl) {
        this.attributionUrl = attributionUrl;
    }
}
