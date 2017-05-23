package org.literacyapp.model.analytics;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.literacyapp.model.content.Number;

@Entity
public class NumberLearningEvent extends LearningEvent {
    
    @NotNull
    private Number number;

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }
}
