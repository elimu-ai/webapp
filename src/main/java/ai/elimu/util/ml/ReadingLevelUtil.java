package ai.elimu.util.ml;

import ai.elimu.model.v2.enums.ReadingLevel;
import lombok.extern.slf4j.Slf4j;

import org.pmml4s.model.Model;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static ai.elimu.util.ReadingLevelConstants.READING_LEVEL_CONSTANTS.*;

@Slf4j
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
        log.info("predictReadingLevel");
        log.info("chapterCount: " + chapterCount + ", paragraphCount: " + paragraphCount + ", wordCount: " + wordCount);

        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(ReadingLevelUtil.class);
        Resource resource = resourceLoader.getResource("step2_2_model.pmml");
        
        ReadingLevel readingLevel = null;
        try {
            File modelFile = resource.getFile();
            log.info("modelFile.getPath(): " + modelFile.getPath());
            log.info("modelFile.exists(): " + modelFile.exists());

            Model model = Model.fromFile(modelFile);
            Map<String, Double> features = Map.of(
                    CHAPTER_COUNT_KEY, (double) chapterCount,
                    PARAGRAPH_COUNT_KEY, (double) paragraphCount,
                    WORD_COUNT_KEY, (double) wordCount
            );
            log.info("features: " + features);
    
            Object[] valuesMap = Arrays.stream(model.inputNames())
                    .map(features::get)
                    .toArray();
            log.info("valuesMap: " + valuesMap);
    
            Object[] results = model.predict(valuesMap);
            log.info("results: " + results);
    
            Object result = results[0];
            log.info("result: " + result);

            Double resultAsDouble = (Double) result;
            log.info("resultAsDouble: " + resultAsDouble);

            int resultAsInteger = resultAsDouble.intValue();
            log.info("resultAsInteger: " + resultAsInteger);
    
            String readingLevelAsString = "LEVEL" + resultAsInteger;
            log.info("readingLevelAsString: " + readingLevelAsString);

            readingLevel = ReadingLevel.valueOf(readingLevelAsString);
        } catch (IOException exception) {
            log.error(null, exception);
        }
        log.info("readingLevel: " + readingLevel);
        return readingLevel;
    }
}
