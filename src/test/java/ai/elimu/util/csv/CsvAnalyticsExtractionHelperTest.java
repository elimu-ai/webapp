package ai.elimu.util.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.elimu.model.analytics.VideoLearningEvent;
import ai.elimu.model.v2.enums.analytics.LearningEventType;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class CsvAnalyticsExtractionHelperTest {

  private Logger logger = LogManager.getLogger();

  @Test
  public void testExtractVideoLearningEvents() throws IOException {
    ResourceLoader resourceLoader = new ClassRelativeResourceLoader(CsvAnalyticsExtractionHelper.class);
    Resource resource = resourceLoader.getResource("e387e38700000001_3001018_video-learning-events_2024-10-09.csv");
    File csvFile = resource.getFile();
    logger.debug("csvFile: " + csvFile);

    List<VideoLearningEvent> videoLearningEvents = CsvAnalyticsExtractionHelper.extractVideoLearningEvents(csvFile);
    assertEquals(6, videoLearningEvents.size());

    // Test the 1st row of data
    VideoLearningEvent event1st = videoLearningEvents.get(0);
    assertEquals(1728486312687L, event1st.getTimestamp().getTimeInMillis());
    assertEquals("e387e38700000001", event1st.getAndroidId());
    assertEquals("ai.elimu.analytics", event1st.getPackageName());
    assertEquals(13, event1st.getVideoId());
    assertEquals("akili and me - the rectangle song", event1st.getVideoTitle());
    assertEquals(LearningEventType.VIDEO_OPENED, event1st.getLearningEventType());
    assertEquals("", event1st.getAdditionalData());

    // Test the 2nd row of data
    VideoLearningEvent event2nd = videoLearningEvents.get(1);
    assertEquals(1728486319885L, event2nd.getTimestamp().getTimeInMillis());
    assertEquals("e387e38700000001", event2nd.getAndroidId());
    assertEquals("ai.elimu.analytics", event2nd.getPackageName());
    assertEquals(13, event2nd.getVideoId());
    assertEquals("akili and me - the rectangle song", event2nd.getVideoTitle());
    assertEquals(LearningEventType.VIDEO_CLOSED_BEFORE_COMPLETION, event2nd.getLearningEventType());
    assertEquals("{'video_playback_position_ms': 58007}", event2nd.getAdditionalData());
  }
}
