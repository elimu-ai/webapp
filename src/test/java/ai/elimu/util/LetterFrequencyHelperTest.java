package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class LetterFrequencyHelperTest {

    @Test
    public void testGetLetterFrequency() {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, Language.ENG);
        assertThat(letterFrequencyMap.get("o"), is(5));
        assertThat(letterFrequencyMap.get("e"), is(3));
        assertThat(letterFrequencyMap.get("l"), is(3));
        assertThat(letterFrequencyMap.get("a"), is(2));
        assertThat(letterFrequencyMap.get("d"), is(2));
        assertThat(letterFrequencyMap.get("m"), is(2));
        assertThat(letterFrequencyMap.get("b"), is(1));
        assertThat(letterFrequencyMap.get("c"), is(1));
        assertThat(letterFrequencyMap.get("n"), is(1));
        assertThat(letterFrequencyMap.get("C"), is(1));
        assertThat(letterFrequencyMap.get("L"), is(1));
        assertThat(letterFrequencyMap.get("M"), is(1));
    }
}
