package ai.elimu.util.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.entity.analytics.NumberAssessmentEvent;
import ai.elimu.entity.analytics.NumberLearningEvent;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.entity.analytics.WordAssessmentEvent;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;

public class CsvAnalyticsExtractionHelperTest {

    /**
     * Test extraction of data from CSV files generated by version 3002014 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.2.14
     */
    @Test
    public void testExtractLetterSoundLearningEvents_v3002014() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3002014_letter-sound-learning-events_2025-05-11.csv");
        File csvFile = resource.getFile();
        
        List<LetterSoundLearningEvent> letterSoundLearningEvents = CsvAnalyticsExtractionHelper.extractLetterSoundLearningEvents(csvFile);
        assertEquals(6, letterSoundLearningEvents.size());

        LetterSoundLearningEvent letterSoundLearningEvent = letterSoundLearningEvents.get(0);
        assertEquals(1746952025000L, letterSoundLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", letterSoundLearningEvent.getAndroidId());
        assertEquals("ai.elimu.herufi.debug", letterSoundLearningEvent.getPackageName());
        assertEquals(300, letterSoundLearningEvent.getLetterSoundId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005009 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.9
     */
    @Test
    public void testExtractLetterSoundLearningEvents_v3005009() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3005009_letter-sound-learning-events_2025-06-18.csv");
        File csvFile = resource.getFile();
        
        List<LetterSoundLearningEvent> letterSoundLearningEvents = CsvAnalyticsExtractionHelper.extractLetterSoundLearningEvents(csvFile);
        assertEquals(5, letterSoundLearningEvents.size());

        LetterSoundLearningEvent letterSoundLearningEvent = letterSoundLearningEvents.get(0);
        assertEquals(1750210474 * 1_000L, letterSoundLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", letterSoundLearningEvent.getAndroidId());
        assertEquals("ai.elimu.herufi.debug", letterSoundLearningEvent.getPackageName());
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, letterSoundLearningEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, letterSoundLearningEvent.getExperimentGroup());
        assertEquals(149, letterSoundLearningEvent.getLetterSoundId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005013 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.13
     */
    @Test
    public void testExtractLetterSoundLearningEvents_v3005013() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("7e89c8e7f4c68405_3005013_letter-sound-learning-events_2025-05-14.csv");
        File csvFile = resource.getFile();
        
        List<LetterSoundLearningEvent> letterSoundLearningEvents = CsvAnalyticsExtractionHelper.extractLetterSoundLearningEvents(csvFile);
        assertEquals(5, letterSoundLearningEvents.size());

        LetterSoundLearningEvent letterSoundLearningEvent = letterSoundLearningEvents.get(0);
        assertEquals(1747200557 * 1_000L, letterSoundLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("7e89c8e7f4c68405", letterSoundLearningEvent.getAndroidId());
        assertEquals("ai.elimu.herufi", letterSoundLearningEvent.getPackageName());
        assertNull(letterSoundLearningEvent.getResearchExperiment());
        assertNull(letterSoundLearningEvent.getExperimentGroup());
        assertEquals(Arrays.asList(), letterSoundLearningEvent.getLetterSoundLetters());
        assertEquals(Arrays.asList(), letterSoundLearningEvent.getLetterSoundSounds());
        assertEquals(1, letterSoundLearningEvent.getLetterSoundId());
    }

    /**
     * Test extraction of data from CSV files generated by version 4001002 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/4.1.2
     */
    @Test
    public void testExtractLetterSoundLearningEvents_v4001002() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_4001002_letter-sound-learning-events_2025-07-28.csv");
        File csvFile = resource.getFile();
        
        List<LetterSoundLearningEvent> letterSoundLearningEvents = CsvAnalyticsExtractionHelper.extractLetterSoundLearningEvents(csvFile);
        assertEquals(22, letterSoundLearningEvents.size());

        LetterSoundLearningEvent letterSoundLearningEvent = letterSoundLearningEvents.get(0);
        assertEquals(1753693734 * 1_000L, letterSoundLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", letterSoundLearningEvent.getAndroidId());
        assertEquals("ai.elimu.herufi.debug", letterSoundLearningEvent.getPackageName());
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, letterSoundLearningEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, letterSoundLearningEvent.getExperimentGroup());
        assertEquals(Arrays.asList("น"), letterSoundLearningEvent.getLetterSoundLetters());
        assertEquals(Arrays.asList("n"), letterSoundLearningEvent.getLetterSoundSounds());
        assertEquals(7, letterSoundLearningEvent.getLetterSoundId());
    }

    // TODO: letter-sound assessment events

    
    /**
     * Test extraction of data from CSV files generated by version 3001030 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.1.30
     */
    @Test
    public void testExtractWordAssessmentEvents_v3001030() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("1bb5b718814899b5_3001030_word-assessment-events_2025-03-19.csv");
        File csvFile = resource.getFile();
        
        List<WordAssessmentEvent> wordAssessmentEvents = CsvAnalyticsExtractionHelper.extractWordAssessmentEvents(csvFile);
        assertEquals(1, wordAssessmentEvents.size());

        WordAssessmentEvent wordAssessmentEvent = wordAssessmentEvents.get(0);
        assertEquals(1742402392000L, wordAssessmentEvent.getTimestamp().getTimeInMillis());
        assertEquals("1bb5b718814899b5", wordAssessmentEvent.getAndroidId());
        assertEquals("ai.elimu.kukariri.debug", wordAssessmentEvent.getPackageName());
        assertEquals("aso", wordAssessmentEvent.getWordText());
        assertEquals(1, wordAssessmentEvent.getWordId());
        assertEquals(1.0F, wordAssessmentEvent.getMasteryScore());
        assertEquals(25457, wordAssessmentEvent.getTimeSpentMs());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005009 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.9
     */
    @Test
    public void testExtractWordAssessmentEvents_v3005009() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3005009_word-assessment-events_2025-06-19.csv");
        File csvFile = resource.getFile();
        
        List<WordAssessmentEvent> wordAssessmentEvents = CsvAnalyticsExtractionHelper.extractWordAssessmentEvents(csvFile);
        assertEquals(3, wordAssessmentEvents.size());

        WordAssessmentEvent wordAssessmentEvent = wordAssessmentEvents.get(0);
        assertEquals(1750305009 * 1_000L, wordAssessmentEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", wordAssessmentEvent.getAndroidId());
        assertEquals("ai.elimu.kukariri.debug", wordAssessmentEvent.getPackageName());
        assertEquals(1.0F, wordAssessmentEvent.getMasteryScore());
        assertEquals(7_345, wordAssessmentEvent.getTimeSpentMs());
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, wordAssessmentEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, wordAssessmentEvent.getExperimentGroup());
        assertEquals("มา", wordAssessmentEvent.getWordText());
        assertEquals(35, wordAssessmentEvent.getWordId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3001030 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.1.30
     */
    @Test
    public void testExtractWordLearningEvents_v3001030() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3001030_word-learning-events_2025-03-18.csv");
        File csvFile = resource.getFile();
        
        List<WordLearningEvent> wordLearningEvents = CsvAnalyticsExtractionHelper.extractWordLearningEvents(csvFile);
        assertEquals(143, wordLearningEvents.size());

        WordLearningEvent wordLearningEvent = wordLearningEvents.get(0);
        assertEquals(1742293958000L, wordLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", wordLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", wordLearningEvent.getPackageName());
        assertEquals("ฉัน", wordLearningEvent.getWordText());
        // assertEquals(36, wordLearningEvent.getWordId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005009 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.9
     */
    @Test
    public void testExtractWordLearningEvents_v3005009() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3005009_word-learning-events_2025-06-18.csv");
        File csvFile = resource.getFile();
        
        List<WordLearningEvent> wordLearningEvents = CsvAnalyticsExtractionHelper.extractWordLearningEvents(csvFile);
        assertEquals(31, wordLearningEvents.size());

        WordLearningEvent wordLearningEvent = wordLearningEvents.get(0);
        assertEquals(1750210620 * 1_000L, wordLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", wordLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", wordLearningEvent.getPackageName());
        assertNull(wordLearningEvent.getAdditionalData());
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, wordLearningEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, wordLearningEvent.getExperimentGroup());
        assertEquals("ดี", wordLearningEvent.getWordText());
    }


    /**
     * Test extraction of data from CSV files generated by version 4000028 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/4.0.28
     */
    @Test
    public void testExtractNumberLearningEvents_v4000028() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_4000028_number-learning-events_2025-07-04.csv");
        File csvFile = resource.getFile();
        
        List<NumberLearningEvent> numberLearningEvents = CsvAnalyticsExtractionHelper.extractNumberLearningEvents(csvFile);
        assertEquals(1, numberLearningEvents.size());

        NumberLearningEvent numberLearningEvent = numberLearningEvents.get(0);
        assertEquals(1751610361 * 1_000L, numberLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", numberLearningEvent.getAndroidId());
        assertEquals("ai.elimu.calculator.debug", numberLearningEvent.getPackageName());
        assertEquals(numberLearningEvent.getResearchExperiment(), ResearchExperiment.EXP_0_WORD_EMOJIS);
        assertEquals(numberLearningEvent.getExperimentGroup(), ExperimentGroup.TREATMENT);
        assertEquals(5, numberLearningEvent.getNumberValue());
        assertNull(numberLearningEvent.getNumberSymbol());
        assertNull(numberLearningEvent.getNumberId());
    }

    /**
     * Test extraction of data from CSV files generated by version 4000028 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/4.0.28
     */
    @Test
    public void testExtractNumberAssessmentEvents_v4000028() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_4000028_number-assessment-events_2025-07-03.csv");
        File csvFile = resource.getFile();
        
        List<NumberAssessmentEvent> numberAssessmentEvents = CsvAnalyticsExtractionHelper.extractNumberAssessmentEvents(csvFile);
        assertEquals(3, numberAssessmentEvents.size());

        NumberAssessmentEvent numberAssessmentEvent = numberAssessmentEvents.get(0);
        assertEquals(1751536157 * 1_000L, numberAssessmentEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", numberAssessmentEvent.getAndroidId());
        assertEquals("ai.elimu.learndigits.debug", numberAssessmentEvent.getPackageName());
        assertEquals(0.0F, numberAssessmentEvent.getMasteryScore());
        assertEquals(1_782, numberAssessmentEvent.getTimeSpentMs());
        JSONObject additionalData = new JSONObject(numberAssessmentEvent.getAdditionalData());
        assertTrue(additionalData.has("numberSelected"));
        assertEquals(6, additionalData.getInt("numberSelected"));
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, numberAssessmentEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, numberAssessmentEvent.getExperimentGroup());
        assertEquals(9, numberAssessmentEvent.getNumberValue());
        assertNull(numberAssessmentEvent.getNumberId());
    }

    // TODO: number learning events

    
    /**
     * Test extraction of data from CSV files generated by version 3001030 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.1.30
     */
    @Test
    public void testExtractStoryBookLearningEvents_v3001030() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3001030_storybook-learning-events_2025-03-18.csv");
        File csvFile = resource.getFile();
        
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.extractStoryBookLearningEvents(csvFile);
        assertEquals(8, storyBookLearningEvents.size());

        StoryBookLearningEvent storyBookLearningEvent = storyBookLearningEvents.get(0);
        assertEquals(1742293901000L, storyBookLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", storyBookLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", storyBookLearningEvent.getPackageName());
        assertEquals("", storyBookLearningEvent.getStoryBookTitle());
        assertEquals(2, storyBookLearningEvent.getStoryBookId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3002014 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.2.14
     */
    @Test
    public void testExtractStoryBookLearningEvents_v3002014() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3002014_storybook-learning-events_2025-05-26.csv");
        File csvFile = resource.getFile();
        
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.extractStoryBookLearningEvents(csvFile);
        assertEquals(2, storyBookLearningEvents.size());

        StoryBookLearningEvent storyBookLearningEvent = storyBookLearningEvents.get(0);
        assertEquals(1748252197000L, storyBookLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", storyBookLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", storyBookLearningEvent.getPackageName());
        assertEquals("", storyBookLearningEvent.getStoryBookTitle());
        assertEquals(31, storyBookLearningEvent.getStoryBookId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3003000 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.3.0
     */
    @Test
    public void testExtractStoryBookLearningEvents_v3003000() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3003000_storybook-learning-events_2025-06-03.csv");
        File csvFile = resource.getFile();
        
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.extractStoryBookLearningEvents(csvFile);
        assertEquals(2, storyBookLearningEvents.size());

        StoryBookLearningEvent storyBookLearningEvent = storyBookLearningEvents.get(0);
        assertEquals(1748252197000L, storyBookLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", storyBookLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", storyBookLearningEvent.getPackageName());
        assertEquals("กลโกงเจ้าจิ้งจอก", storyBookLearningEvent.getStoryBookTitle());
        assertEquals(31, storyBookLearningEvent.getStoryBookId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005009 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.9
     */
    @Test
    public void testExtractStoryBookLearningEvents_v3005009() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3005009_storybook-learning-events_2025-06-20.csv");
        File csvFile = resource.getFile();
        
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.extractStoryBookLearningEvents(csvFile);
        assertEquals(4, storyBookLearningEvents.size());

        StoryBookLearningEvent storyBookLearningEvent = storyBookLearningEvents.get(0);
        assertEquals(1750389715 * 1_000L, storyBookLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", storyBookLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", storyBookLearningEvent.getPackageName());
        assertNull(storyBookLearningEvent.getAdditionalData());
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, storyBookLearningEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, storyBookLearningEvent.getExperimentGroup());
        assertEquals("จับปลา", storyBookLearningEvent.getStoryBookTitle());
        assertEquals(48, storyBookLearningEvent.getStoryBookId());
    }

    /**
     * Test extraction of data from CSV files generated by version 4000034 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/4.0.34
     */
    @Test
    public void testExtractStoryBookLearningEvents_v4000034() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fbc880caac090c43_4000034_storybook-learning-events_2025-07-18.csv");
        File csvFile = resource.getFile();
        
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.extractStoryBookLearningEvents(csvFile);
        assertEquals(2, storyBookLearningEvents.size());

        StoryBookLearningEvent storyBookLearningEvent = storyBookLearningEvents.get(0);
        assertEquals(1752824314 * 1_000L, storyBookLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("fbc880caac090c43", storyBookLearningEvent.getAndroidId());
        assertEquals("ai.elimu.vitabu.debug", storyBookLearningEvent.getPackageName());
        JSONObject additionalData = new JSONObject(storyBookLearningEvent.getAdditionalData());
        assertTrue(additionalData.has("eventType"));
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, storyBookLearningEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.CONTROL, storyBookLearningEvent.getExperimentGroup());
        assertEquals("एक था मोटा राजा", storyBookLearningEvent.getStoryBookTitle());
        assertNull(storyBookLearningEvent.getStoryBookId());
    }

    
    /**
     * Test extraction of data from CSV files generated by version 3001018 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.1.18
     */
    @Test
    public void testExtractVideoLearningEvents_v3001018() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("e387e38700000001_3001018_video-learning-events_2024-10-09.csv");
        File csvFile = resource.getFile();
        
        List<VideoLearningEvent> videoLearningEvents = CsvAnalyticsExtractionHelper.extractVideoLearningEvents(csvFile);
        assertEquals(6, videoLearningEvents.size());

        VideoLearningEvent videoLearningEvent = videoLearningEvents.get(0);
        assertEquals(1728486312000L, videoLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("e387e38700000001", videoLearningEvent.getAndroidId());
        assertEquals("ai.elimu.filamu", videoLearningEvent.getPackageName());
        assertEquals("akili and me - the rectangle song", videoLearningEvent.getVideoTitle());
        assertEquals(13, videoLearningEvent.getVideoId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005009 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.9
     */
    @Test
    public void testExtractVideoLearningEvents_v3005009() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("5b7c682a12ecbe2e_3005009_video-learning-events_2025-06-18.csv");
        File csvFile = resource.getFile();
        
        List<VideoLearningEvent> videoLearningEvents = CsvAnalyticsExtractionHelper.extractVideoLearningEvents(csvFile);
        assertEquals(2, videoLearningEvents.size());

        VideoLearningEvent videoLearningEvent = videoLearningEvents.get(0);
        assertEquals(1750210660 * 1_000L, videoLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("5b7c682a12ecbe2e", videoLearningEvent.getAndroidId());
        assertEquals("ai.elimu.filamu.debug", videoLearningEvent.getPackageName());
        assertNull(videoLearningEvent.getAdditionalData());
        assertEquals(ResearchExperiment.EXP_0_WORD_EMOJIS, videoLearningEvent.getResearchExperiment());
        assertEquals(ExperimentGroup.TREATMENT, videoLearningEvent.getExperimentGroup());
        assertEquals("เพลง นับเลข 1-10 ｜ เพลงเด็กอนุบาล ｜ นับเล", videoLearningEvent.getVideoTitle());
        assertEquals(9, videoLearningEvent.getVideoId());
    }

    /**
     * Test extraction of data from CSV files generated by version 3005013 of the Analytics app: 
     * https://github.com/elimu-ai/analytics/releases/tag/3.5.13
     */
    @Test
    public void testExtractVideoLearningEvents_v3005013() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
        Resource resource = resourceLoader.getResource("7e89c8e7f4c68405_3005013_video-learning-events_2025-06-13.csv");
        File csvFile = resource.getFile();
        
        List<VideoLearningEvent> videoLearningEvents = CsvAnalyticsExtractionHelper.extractVideoLearningEvents(csvFile);
        assertEquals(9, videoLearningEvents.size());

        VideoLearningEvent videoLearningEvent = videoLearningEvents.get(0);
        assertEquals(1749775644 * 1_000L, videoLearningEvent.getTimestamp().getTimeInMillis());
        assertEquals("7e89c8e7f4c68405", videoLearningEvent.getAndroidId());
        assertEquals("ai.elimu.filamu", videoLearningEvent.getPackageName());
        assertNull(videoLearningEvent.getAdditionalData());
        assertNull(videoLearningEvent.getResearchExperiment());
        assertNull(videoLearningEvent.getExperimentGroup());
        assertEquals("akili and me - letter b", videoLearningEvent.getVideoTitle());
        assertEquals(2, videoLearningEvent.getVideoId());
    }
}
