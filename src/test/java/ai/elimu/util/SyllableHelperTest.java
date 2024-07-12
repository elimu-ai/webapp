package ai.elimu.util;

import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SyllableHelperTest {

    @Test
    public void testGetMonoSyllables_languageEN() {
        Word word = new Word();

        word.setText("am");
        List<String> syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("am", syllables.get(0));

        word.setText("to");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("to", syllables.get(0));

        word.setText("was");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("was", syllables.get(0));

        word.setText("the");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("the", syllables.get(0));
    }

    @Test
    public void testGetDiSyllables_languageEN() {
        Word word = new Word();

        word.setText("mother");
        List<String> syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("moth", syllables.get(0));
        assertEquals("er", syllables.get(1));

        word.setText("baby");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("ba", syllables.get(0));
        assertEquals("by", syllables.get(1));

        word.setText("happy");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("hap", syllables.get(0));
        assertEquals("py", syllables.get(1));

        word.setText("into");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("in", syllables.get(0));
        assertEquals("to", syllables.get(1));

        word.setText("father");
        syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("fa", syllables.get(0));
        assertEquals("ther", syllables.get(1));

        // Uncomment the following tests if you have the logic implemented for them
        // word.setText("football");
        // syllables = SyllableHelper.getSyllables(word, Language.ENG);
        // assertEquals("foot", syllables.get(0));
        // assertEquals("ball", syllables.get(1));

        // word.setText("elephant");
        // syllables = SyllableHelper.getSyllables(word, Language.ENG);
        // assertEquals("ele", syllables.get(0));
        // assertEquals("phant", syllables.get(1));
    }

    @Test
    public void testGetTriSyllables_languageEN() {
        Word word = new Word();
        word.setText("grandmother");
        List<String> syllables = SyllableHelper.getSyllables(word, Language.ENG);
        assertEquals("grand", syllables.get(0));
        assertEquals("moth", syllables.get(1));
        assertEquals("er", syllables.get(2));
    }
}
