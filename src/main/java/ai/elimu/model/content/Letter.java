package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Letter extends Content {

    @NotNull
    @Length(max = 1)
    @Column(length = 1)
    private String text;
    
    @Deprecated // Will be replaced by LetterToAllophoneMappings
    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Allophone> allophones;
    
    private boolean diacritic;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    @Deprecated
    public List<Allophone> getAllophones() {
        return allophones;
    }

    @Deprecated
    public void setAllophones(List<Allophone> allophones) {
        this.allophones = allophones;
    }
    
    public boolean isDiacritic() {
        return diacritic;
    }

    public void setDiacritic(boolean diacritic) {
        this.diacritic = diacritic;
    }
}
