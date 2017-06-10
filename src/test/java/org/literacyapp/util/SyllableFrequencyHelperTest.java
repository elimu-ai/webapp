package org.literacyapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.enums.Locale;

public class SyllableFrequencyHelperTest {

    @Test
    public void testGetSyllableFrequency() {
        StoryBook storyBook = new StoryBook();
        storyBook.setLocale(Locale.EN);
        List<String> paragraphs = new ArrayList<>();
        paragraphs.add("\"Mom,\" called Lebo. \"Come and look.\"");
        storyBook.setParagraphs(paragraphs);
        Map<String, Integer> syllableFrequencyMap = SyllableFrequencyHelper.getSyllableFrequency(storyBook);
        System.out.println("syllableFrequencyMap: " + syllableFrequencyMap);
        
//        assertThat(syllableFrequencyMap.get("o"), is(5));
//        assertThat(syllableFrequencyMap.get("e"), is(3));
//        assertThat(syllableFrequencyMap.get("l"), is(3));
//        assertThat(syllableFrequencyMap.get("a"), is(2));
//        assertThat(syllableFrequencyMap.get("d"), is(2));
//        assertThat(syllableFrequencyMap.get("m"), is(2));
//        assertThat(syllableFrequencyMap.get("b"), is(1));
//        assertThat(syllableFrequencyMap.get("c"), is(1));
//        assertThat(syllableFrequencyMap.get("n"), is(1));
//        assertThat(syllableFrequencyMap.get("C"), is(1));
//        assertThat(syllableFrequencyMap.get("L"), is(1));
//        assertThat(syllableFrequencyMap.get("M"), is(1));
    }
}
