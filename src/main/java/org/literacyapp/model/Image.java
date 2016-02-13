package org.literacyapp.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.enums.ImageType;

@Entity
public class Image extends BaseEntity {

    @NotNull
    private String title;
    
    @NotNull
    private ImageType imageType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ImageType getImageType() {
        return imageType;
    }

    public void setImageType(ImageType imageType) {
        this.imageType = imageType;
    }
}
