package ai.elimu.model.content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;

@Entity
public class StoryBook extends Content {

    @NotNull
    private String title;
    
    @Column(length = 1024)
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ContentLicense contentLicense;
    
    @Length(max = 1000)
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
