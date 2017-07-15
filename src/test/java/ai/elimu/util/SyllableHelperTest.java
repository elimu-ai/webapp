package ai.elimu.util;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;

public class SyllableHelperTest {

    @Test
    public void testGetMonoSyllables_localeEN() {
        Word word = new Word();
        word.setLocale(Locale.EN);
        word.setText("am");
        word.setPhonetics("æm");
        List<String> syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("am"));
        
        word.setText("to");
        word.setPhonetics("tu");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("to"));
        
        word.setText("was");
        word.setPhonetics("wʌz");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("was"));
        
        word.setText("the");
        word.setPhonetics("ði");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("the"));
    }
    
    @Test
    public void testGetDiSyllables_localeEN() {
        Word word = new Word();
        word.setLocale(Locale.EN);
        word.setText("mother");
        word.setPhonetics("ˈmʌðər");
        List<String> syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("moth"));
        assertThat(syllables.get(1), is("er"));
        
        word.setText("baby");
        word.setPhonetics("ˈbɛɪbi");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("ba"));
        assertThat(syllables.get(1), is("by"));
        
        word.setText("happy");
        word.setPhonetics("ˈhæpi");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("hap"));
        assertThat(syllables.get(1), is("py"));
        
        word.setText("into");
        word.setPhonetics("ˈɪntu");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("in"));
        assertThat(syllables.get(1), is("to"));
        
        word.setText("father");
        word.setPhonetics("ˈfɑðər");
        syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("fa"));
        assertThat(syllables.get(1), is("ther"));
        
//        word.setText("football");
//        word.setPhonetics("ˈfʊtˌbɔl");
//        syllables = SyllableHelper.getSyllables(word);
//        assertThat(syllables.get(0), is("foot"));
//        assertThat(syllables.get(1), is("ball"));
        
//        word.setText("elephant");
//        word.setPhonetics("ˈɛləfənt");
//        syllables = SyllableHelper.getSyllables(word);
//        assertThat(syllables.get(0), is("ele"));
//        assertThat(syllables.get(1), is("phant"));
    }
    
    @Test
    public void testGetTriSyllables_localeEN() {
        Word word = new Word();
        word.setLocale(Locale.EN);
        word.setText("grandmother");
        word.setPhonetics("ˈgrændˌmʌðər");
        List<String> syllables = SyllableHelper.getSyllables(word);
        assertThat(syllables.get(0), is("grand"));
        assertThat(syllables.get(1), is("moth"));
        assertThat(syllables.get(2), is("er"));
    }
}
