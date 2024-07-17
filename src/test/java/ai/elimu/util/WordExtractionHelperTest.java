package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordExtractionHelperTest {

    @Test
    public void testGetWords() {
        String paragraph = "\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"";
        List<String> words = WordExtractionHelper.getWords(paragraph, Language.ENG);
        assertEquals("Mom", words.get(0));
        assertEquals("called", words.get(1));
        assertEquals("Lebo", words.get(2));
        assertEquals("Come", words.get(3));
        assertEquals("and", words.get(4));
        assertEquals("look", words.get(5));
        assertEquals("These", words.get(6));
        assertEquals("clothes", words.get(7));
        assertEquals("are", words.get(8));
        assertEquals("all", words.get(9));
        assertEquals("too", words.get(10));
        assertEquals("small", words.get(11));
        assertEquals("for", words.get(12));
        assertEquals("me", words.get(13));

        paragraph = "\"Look at my skirt. It's too small,\" said Lebo.";
        words = WordExtractionHelper.getWords(paragraph, Language.ENG);
        assertEquals("Look", words.get(0));
        assertEquals("at", words.get(1));
        assertEquals("my", words.get(2));
        assertEquals("skirt", words.get(3));
        assertEquals("It's", words.get(4));
        assertEquals("too", words.get(5));
        assertEquals("small", words.get(6));
        assertEquals("said", words.get(7));
        assertEquals("Lebo", words.get(8));

        // Test with extra spaces
        paragraph = "This is a girl called Norah.   Her doll’s name is Selah.";
        words = WordExtractionHelper.getWords(paragraph, Language.ENG);
        System.out.println(words);
        assertEquals("This", words.get(0));
        assertEquals("is", words.get(1));
        assertEquals("a", words.get(2));
        assertEquals("girl", words.get(3));
        assertEquals("called", words.get(4));
        assertEquals("Norah", words.get(5));
        assertEquals("Her", words.get(6));
        assertEquals("doll’s", words.get(7));
        assertEquals("name", words.get(8));
        assertEquals("is", words.get(9));
        assertEquals("Selah", words.get(10));
    }

    @Test
    public void testGetWordsWhenDanda() {
        String paragraph = "अब गेंदबाज़ी की बारी मेरी। और यह हो गए आप... आउट!";
        List<String> words = WordExtractionHelper.getWords(paragraph, Language.HIN);
        assertEquals("अब", words.get(0));
        assertEquals("गेंदबाज़ी", words.get(1));
        assertEquals("की", words.get(2));
        assertEquals("बारी", words.get(3));
        assertEquals("मेरी", words.get(4));
        assertEquals("और", words.get(5));
        assertEquals("यह", words.get(6));
        assertEquals("हो", words.get(7));
        assertEquals("गए", words.get(8));
        assertEquals("आप", words.get(9));
        assertEquals("आउट", words.get(10));
    }

    @Test
    public void testGetWordsInsideQuotes() {
        String paragraph = "I wanted to play the ball very much. I said, “Ah! Football.”";
        List<String> words = WordExtractionHelper.getWords(paragraph, Language.ENG);
        assertEquals("I", words.get(0));
        assertEquals("wanted", words.get(1));
        assertEquals("to", words.get(2));
        assertEquals("play", words.get(3));
        assertEquals("the", words.get(4));
        assertEquals("ball", words.get(5));
        assertEquals("very", words.get(6));
        assertEquals("much", words.get(7));
        assertEquals("I", words.get(8));
        assertEquals("said", words.get(9));
        assertEquals("Ah", words.get(10));
        assertEquals("Football", words.get(11));
    }
}
