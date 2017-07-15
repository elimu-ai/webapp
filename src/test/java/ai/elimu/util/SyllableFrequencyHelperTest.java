package ai.elimu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.Locale;

public class SyllableFrequencyHelperTest {

    @Test
    public void testGetSyllableFrequency() {
        StoryBook storyBook = new StoryBook();
        storyBook.setLocale(Locale.EN);
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        storyBook.setParagraphs(paragraphs);
        Map<String, Integer> syllableFrequencyMap = SyllableFrequencyHelper.getSyllableFrequency(storyBook);
        assertThat(syllableFrequencyMap.get("mom"), is(1));
        assertThat(syllableFrequencyMap.get("called"), is(1));
        assertThat(syllableFrequencyMap.get("lebo"), is(1));
        assertThat(syllableFrequencyMap.get("come"), is(1));
        assertThat(syllableFrequencyMap.get("and"), is(1));
        assertThat(syllableFrequencyMap.get("look"), is(1));
    }
}
