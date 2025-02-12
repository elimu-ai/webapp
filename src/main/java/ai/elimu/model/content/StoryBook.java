package ai.elimu.model.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import jakarta.validation.constraints.Size;

@Entity
public class StoryBook extends Content {

    @NotNull
    private String title;
    
    @Column(length = 1024)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ContentLicense contentLicense;
    
    @Size(max = 1000)
    @Column(length = 1000)
    private String attributionUrl;
    
//    @NotNull
    @ManyToOne
    private Image coverImage;
    
//    @NotNull
    @Enumerated(EnumType.STRING)
    private ReadingLevel readingLevel;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public ContentLicense getContentLicense() {
        return contentLicense;
    }

    public void setContentLicense(ContentLicense contentLicense) {
        this.contentLicense = contentLicense;
    }

    public String getAttributionUrl() {
        return attributionUrl;
    }

    public void setAttributionUrl(String attributionUrl) {
        this.attributionUrl = attributionUrl;
    }
    
    public Image getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    public ReadingLevel getReadingLevel() {
        return readingLevel;
    }

    public void setReadingLevel(ReadingLevel readingLevel) {
        this.readingLevel = readingLevel;
    }
}
