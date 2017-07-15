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
    
    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Allophone> allophones;
    
    @Length(max = 1)
    @Column(length = 1)
    private String braille;
    
    private int usageCount; // Based on StoryBook content (all difficulty levels)

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public List<Allophone> getAllophones() {
        return allophones;
    }

    public void setAllophones(List<Allophone> allophones) {
        this.allophones = allophones;
    }
    
    public String getBraille() {
        return braille;
    }

    public void setBraille(String braille) {
        this.braille = braille;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
