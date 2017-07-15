package ai.elimu.dao;

import ai.elimu.dao.ImageDao;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import ai.elimu.model.content.multimedia.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml")
public class BaseDaoTest {
	
    @Autowired
    private ImageDao imageDao;

    @Test
    public void testCreate() {
        Image image1 = new Image();
        image1.setTitle("Title1");
        imageDao.create(image1);
        assertNotNull(image1.getId());

        Image image2 = new Image();
        image2.setTitle("Title1");
        imageDao.create(image2);
        assertNotNull(image2.getId());
    }

    @Test
    public void testRead() {
        Image image1 = new Image();
        image1.setTitle("Title1");
        imageDao.create(image1);
        assertNotNull(image1.getId());
        assertNotNull(imageDao.read(image1.getId()));
    }

    @Test
    public void testReadAll() {
        Image image1 = new Image();
        image1.setTitle("Title1");
        imageDao.create(image1);
        assertNotNull(image1.getId());

        Image image2 = new Image();
        image2.setTitle("Title1");
        imageDao.create(image2);
        assertNotNull(image2.getId());

        assertTrue(imageDao.readAll().size() >= 2);
    }

    @Test
    public void testUpdate() {
        // TODO
    }

    @Test
    public void testDelete() {
        // TODO
    }
}
