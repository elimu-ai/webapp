package ai.elimu.dao;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import ai.elimu.entity.content.multimedia.Video;

@SpringJUnitConfig(locations = {
    "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
    "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@TestMethodOrder(MethodName.class)
public class VideoDaoTest {

  private final VideoDao videoDao;

  @Autowired
  public VideoDaoTest(VideoDao videoDao) {
    this.videoDao = videoDao;
  }

  @Test
  public void testReadAllOrdered() {
    List<Video> expectedVideos = new ArrayList<>();
    expectedVideos.add(getVideo("count to ten with me"));
    expectedVideos.add(getVideo("letter a"));
    expectedVideos.add(getVideo("one two three song"));

    videoDao.create(getVideo("letter a"));
    videoDao.create(getVideo("one two three song"));
    videoDao.create(getVideo("count to ten with me"));

    List<Video> videosActual = videoDao.readAllOrdered();

    assertArrayEquals(
        expectedVideos.stream().map(Video::getTitle).toArray(),
        videosActual.stream().map(Video::getTitle).toArray()
    );
  }

  @Test
  public void testReadByTitle() {
    videoDao.create(getVideo("the rectangle song"));
    assertTrue("the rectangle song".equals(videoDao.read("the rectangle song").getTitle()));
    assertNull(videoDao.read("None"));
  }

  private Video getVideo(String title) {
    Video video = new Video();
    video.setTitle(title);
    return video;
  }
}
