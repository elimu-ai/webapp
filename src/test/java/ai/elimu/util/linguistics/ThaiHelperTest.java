package ai.elimu.util.linguistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;

public class ThaiHelperTest {

    @Test
    public void testSplitIntoWords() throws UnsupportedEncodingException {
        assertEquals("ฉัน จะ ไป โรงเรียน", ThaiHelper.splitIntoWords("ฉันจะไปโรงเรียน"));
        assertEquals("เดี๋ยว วัน นี้ เรา กลับ บ้าน ไป พัก", ThaiHelper.splitIntoWords("เดี๋ยววันนี้เรากลับบ้านไปพัก"));
    }

    @Test
    public void testSplitIntoWords_withQuotes() throws UnsupportedEncodingException {
        assertEquals("\" มัน แพง ไป นะ \"",ThaiHelper.splitIntoWords("\"มันแพงไปนะ\""));
    }
}
