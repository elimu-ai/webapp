package ai.elimu.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnalyticsHelperTest {
    
    @Test
    public void testExtractAndroidIdFromCsvFilename() {
        String filename = "745f90e7aae26423_3001012_storybook-learning-events_2020-05-02.csv";
        assertEquals("745f90e7aae26423", AnalyticsHelper.extractAndroidIdFromCsvFilename(filename));
        
        filename = "7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv";
        assertEquals("7161a85a0e4751cd", AnalyticsHelper.extractAndroidIdFromCsvFilename(filename));
    }
    
    @Test
    public void testExtractVersionCodeFromCsvFilename() {
        String filename = "745f90e7aae26423_3001012_storybook-learning-events_2020-05-02.csv";
        assertEquals(3001012, AnalyticsHelper.extractVersionCodeFromCsvFilename(filename));
        
        filename = "7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv";
        assertEquals(3001012, AnalyticsHelper.extractVersionCodeFromCsvFilename(filename));
    }

    @Test
    public void testRedactAndroidId() {
        String androidId = "745f90e7aae26423";
        assertEquals("745f***6423", AnalyticsHelper.redactAndroidId(androidId));
    }
}
