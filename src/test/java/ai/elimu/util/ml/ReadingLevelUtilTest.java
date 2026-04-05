package ai.elimu.util.ml;

import ai.elimu.model.v2.enums.ReadingLevel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Simple tests for verifying that the machine learning model is not broken. 
 * The test parameters are based on the regression lines visualized at 
 * https://github.com/elimu-ai/ml-storybook-reading-level/tree/main/pmml/step1_prepare
 */
public class ReadingLevelUtilTest {

    @Test
    public void testPredictReadingLevel_Level1() {
        assertEquals(ReadingLevel.LEVEL1, ReadingLevelUtil.predictReadingLevel(10, 16, 100));
    }

    @Test
    public void testPredictReadingLevel_Level2() {
        assertEquals(ReadingLevel.LEVEL2, ReadingLevelUtil.predictReadingLevel(12, 32, 500));
    }

    @Test
    public void testPredictReadingLevel_Level3() {
        assertEquals(ReadingLevel.LEVEL3, ReadingLevelUtil.predictReadingLevel(15, 45, 900));
    }

    @Test
    public void testPredictReadingLevel_Level4() {
        assertEquals(ReadingLevel.LEVEL4, ReadingLevelUtil.predictReadingLevel(17, 60, 1_200));
    }
}
