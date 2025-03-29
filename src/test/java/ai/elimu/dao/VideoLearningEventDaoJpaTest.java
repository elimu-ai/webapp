package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Calendar;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.analytics.VideoLearningEvent;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class VideoLearningEventDaoJpaTest {

  private final VideoLearningEventDao videoLearningEventDao;

  @Autowired
  public VideoLearningEventDaoJpaTest(VideoLearningEventDao videoLearningEventDao) {
    this.videoLearningEventDao = videoLearningEventDao;
  }

  @Test
  public void testRead() {
    Calendar timestamp = Calendar.getInstance();
    String androidId = "e387e38700000001";
    String packageName = "ai.elimu.filamu";
    String videoTitle = "akili and me - the rectangle song";

    VideoLearningEvent existingEvent = videoLearningEventDao.read(
        timestamp,
        androidId,
        packageName,
        videoTitle
    );
    assertNull(existingEvent);

    VideoLearningEvent event = new VideoLearningEvent();
    event.setTimestamp(timestamp);
    event.setAndroidId(androidId);
    event.setPackageName(packageName);
    event.setVideoTitle(videoTitle);
    videoLearningEventDao.create(event);

    existingEvent = videoLearningEventDao.read(
        timestamp,
        androidId,
        packageName,
        videoTitle
    );
    assertNotNull(existingEvent);
  }
}
