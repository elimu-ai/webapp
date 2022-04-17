package ai.elimu.util;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class AnalyticsHelperTest {
    
    @Test
    public void testExtractAndroidIdFromCsvFilename() {
        String filename = "745f90e7aae26423_3001012_storybook-learning-events_2020-05-02.csv";
        assertThat(AnalyticsHelper.extractAndroidIdFromCsvFilename(filename), is("745f90e7aae26423"));
        
        filename = "7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv";
        assertThat(AnalyticsHelper.extractAndroidIdFromCsvFilename(filename), is("7161a85a0e4751cd"));
    }
    
    @Test
    public void testExtractVersionCodeFromCsvFilename() {
        String filename = "745f90e7aae26423_3001012_storybook-learning-events_2020-05-02.csv";
        assertThat(AnalyticsHelper.extractVersionCodeFromCsvFilename(filename), is(3001012));
        
        filename = "7161a85a0e4751cd_3001012_word-learning-events_2020-04-23.csv";
        assertThat(AnalyticsHelper.extractVersionCodeFromCsvFilename(filename), is(3001012));
    }
}
