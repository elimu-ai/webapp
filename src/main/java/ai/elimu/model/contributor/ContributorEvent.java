package ai.elimu.model.contributor;

import java.util.Calendar;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import ai.elimu.model.BaseEntity;
import ai.elimu.model.Contributor;

@MappedSuperclass
public abstract class ContributorEvent extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar calendar;

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
}
