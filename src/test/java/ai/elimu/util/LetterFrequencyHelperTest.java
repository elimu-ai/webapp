package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LetterFrequencyHelperTest {

    @Test
    public void testGetLetterFrequency() {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, Language.ENG);
        assertEquals(5, letterFrequencyMap.get("o"));
        assertEquals(3, letterFrequencyMap.get("e"));
        assertEquals(3, letterFrequencyMap.get("l"));
        assertEquals(2, letterFrequencyMap.get("a"));
        assertEquals(2, letterFrequencyMap.get("d"));
        assertEquals(2, letterFrequencyMap.get("m"));
        assertEquals(1, letterFrequencyMap.get("b"));
        assertEquals(1, letterFrequencyMap.get("c"));
        assertEquals(1, letterFrequencyMap.get("n"));
        assertEquals(1, letterFrequencyMap.get("C"));
        assertEquals(1, letterFrequencyMap.get("L"));
        assertEquals(1, letterFrequencyMap.get("M"));
    }
}
