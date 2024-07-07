package ai.elimu.entity.contributor;

import ai.elimu.entity.content.Number;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
