package ai.elimu.dao;

import ai.elimu.model.content.multimedia.Video;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VideoDaoTest extends TestCase {

    @Autowired
    private VideoDao videoDao;

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

        Assert.assertArrayEquals(
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
