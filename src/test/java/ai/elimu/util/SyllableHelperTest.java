package ai.elimu.util;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.Language;

public class SyllableHelperTest {

    @Test
    public void testGetMonoSyllables_languageEN() {
        Word word = new Word();
        
        word.setText("am");
        List<String> syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("am"));
        
        word.setText("to");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("to"));
        
        word.setText("was");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("was"));
        
        word.setText("the");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("the"));
    }
    
    @Test
    public void testGetDiSyllables_languageEN() {
        Word word = new Word();
        
        word.setText("mother");
        List<String> syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("moth"));
        assertThat(syllables.get(1), is("er"));
        
        word.setText("baby");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("ba"));
        assertThat(syllables.get(1), is("by"));
        
        word.setText("happy");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("hap"));
        assertThat(syllables.get(1), is("py"));
        
        word.setText("into");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("in"));
        assertThat(syllables.get(1), is("to"));
        
        word.setText("father");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("fa"));
        assertThat(syllables.get(1), is("ther"));
        
//        word.setText("football");
//        syllables = SyllableHelper.getSyllables(word, Language.ENG);
//        assertThat(syllables.get(0), is("foot"));
//        assertThat(syllables.get(1), is("ball"));
        
//        word.setText("elephant");
//        syllables = SyllableHelper.getSyllables(word, Language.ENG);
//        assertThat(syllables.get(0), is("ele"));
//        assertThat(syllables.get(1), is("phant"));
    }
    
    @Test
    public void testGetTriSyllables_languageEN() {
        Word word = new Word();
        word.setText("grandmother");
        List<String> syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertThat(syllables.get(0), is("grand"));
        assertThat(syllables.get(1), is("moth"));
        assertThat(syllables.get(2), is("er"));
    }
}
