package ai.elimu.util.ml;

import ai.elimu.model.v2.enums.ReadingLevel;
import org.pmml4s.model.Model;

import java.util.Arrays;
import java.util.Map;

import static ai.elimu.util.ReadingLevelConstants.READING_LEVEL_CONSTANTS.*;

public class ReadingLevelUtil {

    /**
     * Predicts the reading level based on chapter count, paragraph count, and word count using a machine learning model.
     *
     * <p>This method loads a pre-trained machine learning model and predicts the reading level by passing the
     * given chapter count, paragraph count, and word count as input features. The model returns a numeric
     * prediction, which is then converted into a corresponding {@link ReadingLevel} enum.</p>
     *
     * @param chapterCount    The number of chapters in the text. Must be an integer value.
     * @param paragraphCount  The number of paragraphs in the text. Must be an integer value.
     * @param wordCount       The number of words in the text. Must be an integer value.
     *
     * @return The predicted {@link ReadingLevel} based on the input features.
     *
     * <p>Example usage:</p>
     * <pre>
     * {@code
     * int chapterCount = 10;
     * int paragraphCount = 50;
     * int wordCount = 300;
     * ReadingLevel readingLevel = PredictionUtils.predictReadingLevel(chapterCount, paragraphCount, wordCount);
     * System.out.println("Predicted Reading Level: " + readingLevel);
     * }
     * </pre>
     */
    public static ReadingLevel predictReadingLevel(
            int chapterCount,
            int paragraphCount,
            int wordCount
    ) {

        Model model = Model.fromFile(READING_LEVEL_MODEL_FILE_PATH_KEY);
        Map<String, Double> features = Map.of(
                CHAPTER_COUNT_KEY, (double) chapterCount,
                PARAGRAPH_COUNT_KEY, (double) paragraphCount,
                WORD_COUNT_KEY, (double) wordCount
        );

        Object[] valuesMap = Arrays.stream(model.inputNames())
                .map(features::get)
                .toArray();

        Object[] results = model.predict(valuesMap);

        Object result = results[0];
        Double resultAsDouble = (Double) result;
        int resultAsInteger = resultAsDouble.intValue();

        String readingLevelAsString = READING_LEVEL_KEY + resultAsInteger;
        return ReadingLevel.valueOf(readingLevelAsString);

    }
}
