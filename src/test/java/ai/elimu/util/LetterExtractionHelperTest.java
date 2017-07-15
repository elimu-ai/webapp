package ai.elimu.util;

import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LetterExtractionHelperTest {

    @Test
    public void testGetLetters() {
        String paragraph = "\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"";
        List<String> letters = LetterExtractionHelper.getLetters(paragraph);
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
}
