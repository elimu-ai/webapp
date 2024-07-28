package ai.elimu.model.contributor;

import ai.elimu.model.content.Number;
import javax.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class NumberContributionEvent extends ContributionEvent {
    
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
