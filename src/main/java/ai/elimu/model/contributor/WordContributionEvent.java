package ai.elimu.model.contributor;

import ai.elimu.model.BaseEntity;
import ai.elimu.model.content.Word;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class WordContributionEvent extends BaseEntity {
    
    @NotNull
    @ManyToOne
    private Contributor contributor;
    
    @NotNull
    @ManyToOne
    private Word word;

    private String comment;
    
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar time;

    public String getComment() {
        return comment;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public Calendar getTime() {
        return time;
    }

    public Word getWord() {
        return word;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
