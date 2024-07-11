package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordFrequencyHelperTest {

    @Test
    public void testGetWordFrequency() {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"");
        paragraphs.add("\"Look at my skirt. It's too small,\" said Lebo.");
        Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, Language.ENG);
        assertEquals(2, wordFrequencyMap.get("small"));
        assertEquals(2, wordFrequencyMap.get("look"));
        assertEquals(2, wordFrequencyMap.get("lebo"));
        assertEquals(2, wordFrequencyMap.get("too"));
        assertEquals(1, wordFrequencyMap.get("all"));
    }
}
