package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SyllableFrequencyHelperTest {

    @Test
    public void testGetSyllableFrequency() {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        Map<String, Integer> syllableFrequencyMap = SyllableFrequencyHelper.getSyllableFrequency(paragraphs, Language.ENG);
        assertEquals(1, syllableFrequencyMap.get("mom"));
        assertEquals(1, syllableFrequencyMap.get("called"));
        assertEquals(1, syllableFrequencyMap.get("lebo"));
        assertEquals(1, syllableFrequencyMap.get("come"));
        assertEquals(1, syllableFrequencyMap.get("and"));
        assertEquals(1, syllableFrequencyMap.get("look"));
    }
}
