package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class WordFrequencyHelperTest {

    @Test
    public void testGetWordFrequency() {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"");
        paragraphs.add("\"Look at my skirt. It's too small,\" said Lebo.");
        Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, Language.ENG);
        assertThat(wordFrequencyMap.get("small"), is(2));
        assertThat(wordFrequencyMap.get("look"), is(2));
        assertThat(wordFrequencyMap.get("lebo"), is(2));
        assertThat(wordFrequencyMap.get("too"), is(2));
        assertThat(wordFrequencyMap.get("all"), is(1));
    }
}
