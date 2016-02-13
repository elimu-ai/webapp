package org.literacyapp.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Audio extends BaseEntity {

    @NotNull
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
