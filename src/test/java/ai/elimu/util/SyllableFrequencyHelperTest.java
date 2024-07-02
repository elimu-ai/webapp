package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class SyllableFrequencyHelperTest {

    @Test
    public void testGetSyllableFrequency() {
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        Map<String, Integer> syllableFrequencyMap = SyllableFrequencyHelper.getSyllableFrequency(paragraphs, Language.ENG);
        assertThat(syllableFrequencyMap.get("mom"), is(1));
        assertThat(syllableFrequencyMap.get("called"), is(1));
        assertThat(syllableFrequencyMap.get("lebo"), is(1));
        assertThat(syllableFrequencyMap.get("come"), is(1));
        assertThat(syllableFrequencyMap.get("and"), is(1));
        assertThat(syllableFrequencyMap.get("look"), is(1));
    }
}
