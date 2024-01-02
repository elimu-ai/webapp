package ai.elimu.dao;

import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.v2.enums.content.ContentStatus;
import ai.elimu.utilTest.ImageUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class StoryBookDaoTest extends TestCase {

    @Autowired
    private StoryBookDao storyBookDao;

    @Autowired
    ImageDao imageDao;

    @Test
    public void testReadByTitle() {
        assertNull(storyBookDao.readByTitle("test read by title"));

        storyBookDao.create(getStoryBook("test read by title"));
        assertNotNull(storyBookDao.readByTitle("test read by title"));
    }

    @Test
    public void testReadAllWithCoverImage() {
        imageDao.create(ImageUtil.getImage("test read all with image"));
        Image image = imageDao.read("test read all with image");

        assertEquals(0, storyBookDao.readAllWithCoverImage(image).size());

        storyBookDao.create(getStoryBook("test read all with image", image));

        assertEquals(1, storyBookDao.readAllWithCoverImage(image).size());
    }

    private StoryBook getStoryBook(String title) {
        StoryBook storyBook = new StoryBook();
        storyBook.setTitle(title);
        storyBook.setRevisionNumber(1);
        storyBook.setContentStatus(ContentStatus.ACTIVE);
        return storyBook;
    }

    private StoryBook getStoryBook(String title, Image image) {
        StoryBook storyBook = getStoryBook(title);
        storyBook.setCoverImage(image);
        return storyBook;
    }

}