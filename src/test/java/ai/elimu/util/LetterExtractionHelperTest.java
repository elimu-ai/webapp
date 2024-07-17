package ai.elimu.util;

import ai.elimu.model.v2.enums.Language;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LetterExtractionHelperTest {

    @Test
    public void testGetLetters() {
        String paragraph = "\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"";
        List<String> letters = LetterExtractionHelper.getLetters(paragraph, Language.ENG);
        assertEquals("M", letters.get(0));
        assertEquals("o", letters.get(1));
        assertEquals("m", letters.get(2));
        assertEquals("c", letters.get(3));
        assertEquals("a", letters.get(4));
        assertEquals("l", letters.get(5));
        assertEquals("l", letters.get(6));
        assertEquals("e", letters.get(7));
        assertEquals("d", letters.get(8));
        assertEquals("L", letters.get(9));
        assertEquals("e", letters.get(10));
        assertEquals("b", letters.get(11));
        assertEquals("o", letters.get(12));
    }
    
    @Test
    public void testGetLetters_Hindi() {
        String paragraph = "मैं आनंद हूँ।";
        List<String> letters = LetterExtractionHelper.getLetters(paragraph, Language.HIN);
        assertEquals(3 + 4 + 3, letters.size());

        // मैं = म +  ै +  ं
        assertEquals("म", letters.get(0));
        assertEquals("ै", letters.get(1));
        assertEquals("ं", letters.get(2));
        
        // आनंद = आ + न +  ं + द
        assertEquals("आ", letters.get(3));
        assertEquals("न", letters.get(4));
        assertEquals("ं", letters.get(5));
        assertEquals("द", letters.get(6));
        
        // हूँ = ह +  ू +  ँ
        assertEquals("ह", letters.get(7));
        assertEquals("ू", letters.get(8));
        assertEquals("ँ", letters.get(9));
    }
}
