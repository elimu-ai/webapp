package ai.elimu.util.ml;

import ai.elimu.model.v2.enums.ReadingLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadingLevelUtilTest {

    @Test
    public void testPredictReadingLevel_Level1() {

        int chapterCount = 12;
        int paragraphCount = 18;
        int wordCount = 150;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount);
        assertEquals(ReadingLevel.LEVEL1, result, "Expected ReadingLevel to be LEVEL1, but got: " + result);

    }

    @Test
    public void testPredictReadingLevel_Level2() {

        int chapterCount = 20;
        int paragraphCount = 30;
        int wordCount = 300;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount);
        assertEquals(ReadingLevel.LEVEL2, result, "Expected ReadingLevel to be LEVEL2, but got: " + result);

    }

    @Test
    public void testPredictReadingLevel_Level3() {

        int chapterCount = 25;
        int paragraphCount = 40;
        int wordCount = 350;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount);
        assertEquals(ReadingLevel.LEVEL3, result, "Expected ReadingLevel to be LEVEL3, but got: " + result);

    }

    @Test
    public void testPredictReadingLevel_Level4() {

        int chapterCount = 15;
        int paragraphCount = 45;
        int wordCount = 559;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount);
        assertEquals(ReadingLevel.LEVEL4, result, "Expected ReadingLevel to be LEVEL4, but got: " + result);

    }
}