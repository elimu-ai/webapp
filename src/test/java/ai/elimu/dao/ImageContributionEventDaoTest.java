package ai.elimu.dao;

import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.ImageContributionEvent;
import ai.elimu.model.enums.Platform;
import ai.elimu.utilTest.ImageUtil;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class ImageContributionEventDaoTest extends TestCase {

    @Autowired
    private ImageContributionEventDao imageContributionEventDao;

    @Autowired
    private ContributorDao contributorDao;

    @Autowired
    private ImageDao imageDao;

    @Test
    public void testReadCount() {
        imageDao.create(ImageUtil.getImage("Test read count"));
        Image image = imageDao.read("Test read count");
        contributorDao.create(getContributor("test@email.com"));
        Contributor contributor = contributorDao.read("test@email.com");

        assertEquals(0, (long) imageContributionEventDao.readCount(contributor));

        imageContributionEventDao.create(getImageContributionEvent(image, contributor));
        imageContributionEventDao.create(getImageContributionEvent(image, contributor));

        assertEquals(2, (long) imageContributionEventDao.readCount(contributor));
    }

    @Test
    public void testReadAllForImage() {
        imageDao.create(ImageUtil.getImage("Test read all for image"));
        Image image = imageDao.read("Test read all for image");

        assertEquals(0, imageContributionEventDao.readAll(image).size());

        imageContributionEventDao.create(getImageContributionEvent(image));
        imageContributionEventDao.create(getImageContributionEvent(image));

        assertEquals(2, imageContributionEventDao.readAll(image).size());
    }

    @Test
    public void testReadAllForContributor() {
        imageDao.create(ImageUtil.getImage("Test read all for contributor"));
        Image image = imageDao.read("Test read all for contributor");
        contributorDao.create(getContributor("test1@email.com"));
        Contributor contributor = contributorDao.read("test1@email.com");

        assertEquals(0, imageContributionEventDao.readAll(contributor).size());

        imageContributionEventDao.create(getImageContributionEvent(image, contributor));
        imageContributionEventDao.create(getImageContributionEvent(image, contributor));

        assertEquals(2, imageContributionEventDao.readAll(contributor).size());
    }

    @Test
    public void testDeleteAllEventsForImage() {
        imageDao.create(ImageUtil.getImage("Image for event"));
        Image image = imageDao.read("Image for event");
        imageContributionEventDao.create(getImageContributionEvent(image));

        assertTrue(imageContributionEventDao.readAll(image).size() > 0);

        imageContributionEventDao.deleteAllEventsForImage(image);

        assertEquals(0, imageContributionEventDao.readAll(image).size());
    }

    private ImageContributionEvent getImageContributionEvent(Image image) {
        ImageContributionEvent imageContributionEvent = new ImageContributionEvent();
        imageContributionEvent.setImage(image);
        imageContributionEvent.setRevisionNumber(1);
        imageContributionEvent.setTime(Calendar.getInstance());
        imageContributionEvent.setTimeSpentMs(1L);
        imageContributionEvent.setPlatform(Platform.WEBAPP);
        return imageContributionEvent;
    }

    private ImageContributionEvent getImageContributionEvent(Image image, Contributor contributor) {
        ImageContributionEvent imageContributionEvent = getImageContributionEvent(image);
        imageContributionEvent.setContributor(contributor);
        return imageContributionEvent;
    }

    private Contributor getContributor(String email) {
        Contributor contributor = new Contributor();
        contributor.setEmail(email);
        contributor.setRegistrationTime(Calendar.getInstance());
        return contributor;
    }

}