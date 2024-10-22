package ai.elimu.util.ml;

import ai.elimu.model.v2.enums.ReadingLevel;
import org.pmml4s.model.Model;

import java.util.Arrays;
import java.util.Map;

import static ai.elimu.util.AppConstants.READING_LEVEL_CONSTANTS.*;

public class ReadingLevelUtil {

    public static ReadingLevel predictReadingLevel(
            int chapterCount,
            int paragraphCount,
            int wordCount,
            String modelFilePath
    ) {

        Model model = Model.fromFile(modelFilePath);
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

        String readingLevelAsString = LEVEL + resultAsInteger;
        return ReadingLevel.valueOf(readingLevelAsString);

    }
}
