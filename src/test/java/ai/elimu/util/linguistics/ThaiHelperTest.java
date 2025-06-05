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

    @Test
    public void testReplaceๆ() throws UnsupportedEncodingException {
        assertEquals("บ่อย บ่อย",ThaiHelper.replaceๆ("บ่อย ๆ"));
        assertEquals("บ่อย บ่อย",ThaiHelper.replaceๆ("บ่อยๆ"));

        assertEquals("ขนมปัง น่า รัก รัก ชิ้น หนึ่ง",ThaiHelper.replaceๆ("ขนมปัง น่า รัก ๆ ชิ้น หนึ่ง"));
        assertEquals("ได้ เลย ขณะ เดียวกัน ใน ละคร เด็ก คน อื่น อื่น สามารถ เล่น ได้ หลาย บท",ThaiHelper.replaceๆ("ได้ เลย ขณะ เดียวกัน ใน ละคร เด็ก คน อื่นๆ สามารถ เล่น ได้ หลาย บท"));

        assertEquals("เล็ก เล็ก น้อย น้อย เกี่ยว กับ",ThaiHelper.replaceๆ("เล็ก ๆ น้อย ๆ เกี่ยว กับ"));
        assertEquals("ฉะนั้น คุณ เห็น แล้ว มัน ง่าย ง่าย จริง จริง นะ ครับ",ThaiHelper.replaceๆ("ฉะนั้น คุณ เห็น แล้ว มัน ง่ายๆ จริงๆ นะ ครับ"));
    }
}
