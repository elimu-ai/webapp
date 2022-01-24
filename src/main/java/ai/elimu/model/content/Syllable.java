package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;

@Entity
public class Syllable extends Content {

    @NotNull
    private String text;
    
//    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Allophone> allophones;
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public List<Allophone> getSounds() {
        return allophones;
    }

    public void setSounds(List<Allophone> sounds) {
        this.allophones = sounds;
    }
}
