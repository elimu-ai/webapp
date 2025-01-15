package ai.elimu.model.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
public class Letter extends Content {

    @NotNull
    @Length(max = 2)
    @Column(length = 2)
    private String text;

    private boolean diacritic;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDiacritic() {
        return diacritic;
    }

    public void setDiacritic(boolean diacritic) {
        this.diacritic = diacritic;
    }
}
