package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LetterExtractionHelperTest {

    @Test
    public void testGetLetters() {
        String paragraph = "\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"";
        List<String> letters = LetterExtractionHelper.getLetters(paragraph, Language.ENG);
        assertThat(letters.get(0), is("M"));
        assertThat(letters.get(1), is("o"));
        assertThat(letters.get(2), is("m"));
        assertThat(letters.get(3), is("c"));
        assertThat(letters.get(4), is("a"));
        assertThat(letters.get(5), is("l"));
        assertThat(letters.get(6), is("l"));
        assertThat(letters.get(7), is("e"));
        assertThat(letters.get(8), is("d"));
        assertThat(letters.get(9), is("L"));
        assertThat(letters.get(10), is("e"));
        assertThat(letters.get(11), is("b"));
        assertThat(letters.get(12), is("o"));
    }
    
    @Test
    public void testGetLetters_Bengali() {
        String paragraph = "সোফি সবচেয়ে";
        List<String> letters = LetterExtractionHelper.getLetters(paragraph, Language.BEN);
        
        assertThat(letters.size(), is(11));
        
        // সো = স + ো
        assertThat(letters.get(0), is("স"));
        assertThat(letters.get(1), is("ো"));
        
        // ফি = ফ + ি
        assertThat(letters.get(2), is("ফ"));
        assertThat(letters.get(3), is("ি"));
        
        
        assertThat(letters.get(4), is("স"));
        assertThat(letters.get(5), is("ব"));
                
        // চে = চ + ে
        assertThat(letters.get(6), is("চ"));
        assertThat(letters.get(7), is("ে"));
                
        // য়ে = য + ় + ে
        assertThat(letters.get(8), is("য"));
        assertThat(letters.get(9), is("়"));
        assertThat(letters.get(10), is("ে"));
    }
}
