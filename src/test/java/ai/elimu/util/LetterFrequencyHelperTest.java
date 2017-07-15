package ai.elimu.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import ai.elimu.model.content.StoryBook;

public class LetterFrequencyHelperTest {

    @Test
    public void testGetLetterFrequency() {
        StoryBook storyBook = new StoryBook();
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        storyBook.setParagraphs(paragraphs);
        Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(storyBook);
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
