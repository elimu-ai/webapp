package ai.elimu.util.ml;

import ai.elimu.model.v2.enums.ReadingLevel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadingLevelUtilTest {

    @Test
    public void testPredictReadingLevel_Level1() {

        String modelFilePath = "src/test/resources/ai/elimu/util/reading_level/model1.pmml";
        int chapterCount = 5;
        int paragraphCount = 20;
        int wordCount = 100;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount, modelFilePath);
        assertEquals(ReadingLevel.LEVEL1, result, "Expected ReadingLevel to be LEVEL1, but got: " + result);

    }

    @Test
    public void testPredictReadingLevel_Level2() {

        String modelFilePath = "src/test/resources/ai/elimu/util/reading_level/model1.pmml";
        int chapterCount = 12;
        int paragraphCount = 22;
        int wordCount = 250;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount, modelFilePath);
        assertEquals(ReadingLevel.LEVEL2, result, "Expected ReadingLevel to be LEVEL2, but got: " + result);

    }

    @Test
    public void testPredictReadingLevel_Level3() {

        String modelFilePath = "src/test/resources/ai/elimu/util/reading_level/model1.pmml";
        int chapterCount = 12;
        int paragraphCount = 25;
        int wordCount = 350;

        ReadingLevel result = ReadingLevelUtil.predictReadingLevel(chapterCount, paragraphCount, wordCount, modelFilePath);
        assertEquals(ReadingLevel.LEVEL3, result, "Expected ReadingLevel to be LEVEL3, but got: " + result);

    }

    @Test
    public void testPredictReadingLevel_InvalidModelFile() {

        assertThrows(IOException.class, () -> {
            ReadingLevelUtil.predictReadingLevel(1, 1, 1, "invalidPath");
        }, "Expected IOException when loading an invalid model file path");
    }
}