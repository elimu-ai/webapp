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
    VideoDao videoDao;

    @Test
    public void testReadAllOrdered() {
        List<Video> expectedVideos = new ArrayList<>();
        expectedVideos.add(getVideo("Alaska"));
        expectedVideos.add(getVideo("Back to the Future"));
        expectedVideos.add(getVideo("Dirty Harry"));
        expectedVideos.add(getVideo("Predator"));
        expectedVideos.add(getVideo("The Wind"));

        videoDao.create(getVideo("Predator"));
        videoDao.create(getVideo("Back to the Future"));
        videoDao.create(getVideo("Dirty Harry"));
        videoDao.create(getVideo("Alaska"));
        videoDao.create(getVideo("The Wind"));

        List<Video> videosActual = videoDao.readAllOrdered();

        Assert.assertArrayEquals(expectedVideos.stream().map(Video::getTitle).toArray(), videosActual.stream().map(Video::getTitle).toArray());
    }

    @Test
    public void testReadByTitle() {
        videoDao.create(getVideo("Madagascar"));

        assertTrue("Madagascar".equals(videoDao.read("Madagascar").getTitle()));
        assertNull(videoDao.read("None"));
    }

    private Video getVideo(String title) {
        Video video = new Video();
        video.setTitle(title);
        return video;
    }
}