package ai.elimu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;

public class PhoneticsHelperTest {

    @Test
    public void testGetAllophones_localeEN() {
        Word word = new Word();
        word.setLocale(Locale.EN);
        word.setText("zero");
        word.setPhonetics("ˈzɪrɔʊ");
        List<String> allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("z"));
        assertThat(allophones.get(1), is("ɪ"));
        assertThat(allophones.get(2), is("r"));
        assertThat(allophones.get(3), is("ɔ"));
        assertThat(allophones.get(4), is("ʊ"));
        
        word.setText("one");
        word.setPhonetics("wʌn");
        allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("w"));
        assertThat(allophones.get(1), is("ʌ"));
        assertThat(allophones.get(2), is("n"));
        
        word.setText("five");
        word.setPhonetics("fɑɪv");
        allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("f"));
        assertThat(allophones.get(1), is("ɑɪ"));
        assertThat(allophones.get(2), is("v"));
        
        word.setText("eight");
        word.setPhonetics("ɛɪt");
        allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("ɛɪ"));
        assertThat(allophones.get(1), is("t"));
    }
    
    @Test
    public void testGetAllophones_localeSW() {
        Word word = new Word();
        word.setLocale(Locale.SW);
        word.setText("sifuri");
        word.setPhonetics("siˈfuɾi");
        List<String> allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("s"));
        assertThat(allophones.get(1), is("i"));
        assertThat(allophones.get(2), is("f"));
        assertThat(allophones.get(3), is("u"));
        assertThat(allophones.get(4), is("ɾ"));
        assertThat(allophones.get(5), is("i"));
        
        word.setText("moja");
        word.setPhonetics("ˈmɔjɑ");
        allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("m"));
        assertThat(allophones.get(1), is("ɔ"));
        assertThat(allophones.get(2), is("j"));
        assertThat(allophones.get(3), is("ɑ"));
        
        word.setText("mbili");
        word.setPhonetics("mˈbili");
        allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("mb"));
        assertThat(allophones.get(1), is("i"));
        assertThat(allophones.get(2), is("l"));
        assertThat(allophones.get(3), is("i"));
        
        word.setText("nne");
        word.setPhonetics("nˈnɛ");
        allophones = PhoneticsHelper.getAllophones(word);
        assertThat(allophones.get(0), is("n"));
        assertThat(allophones.get(1), is("n"));
        assertThat(allophones.get(2), is("ɛ"));
    }
}
