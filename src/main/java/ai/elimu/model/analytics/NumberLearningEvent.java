package ai.elimu.model.analytics;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import ai.elimu.model.content.Number;

@Entity
public class NumberLearningEvent extends LearningEvent {
    
    @NotNull
    @ManyToOne
    private Number number;

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }
}
