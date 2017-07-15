package ai.elimu.model.content;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderColumn;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Number extends Content {
    
    @NotNull
    private Integer value;
    
    private String symbol;
    
    @Deprecated
    @OneToOne
    private Word word;
    
    @NotEmpty
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Word> words;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Deprecated
    public Word getWord() {
        return word;
    }

    @Deprecated
    public void setWord(Word word) {
        this.word = word;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }
}
