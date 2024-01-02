package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.utilTest.ImageUtil;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "file:src/main/webapp/WEB-INF/spring/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/spring/applicationContext-jpa.xml"
})
public class ImageDeleteControllerTest extends TestCase {

    @Autowired
    ImageDeleteController imageDeleteController;

    @Autowired
    ImageDao imageDao;

    @Autowired
    StoryBookDao storyBookDao;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        assertNotNull(imageDeleteController);
        mockMvc = MockMvcBuilders.standaloneSetup(imageDeleteController).build();
        assertNotNull(mockMvc);
    }

    @Test
    public void testStoryBookContainImageError() throws Exception {
        imageDao.create(ImageUtil.getImage("Title"));
        Image image = imageDao.read("Title");

        storyBookDao.create(getStoryBook(image));

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/content/multimedia/image/delete/" + image.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        assertEquals("StoryBookContainImage", mockMvc.perform(requestBuilder).andReturn().getModelAndView().getModel().get("errorCode"));
    }

    @Test
    public void testDeleteImage() throws Exception {
        imageDao.create(ImageUtil.getImage("TitleRemove"));
        Image image = imageDao.read("TitleRemove");

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/content/multimedia/image/delete/" + image.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        assertNull(mockMvc.perform(requestBuilder).andReturn().getModelAndView().getModel().get("errorCode"));
        assertNull(imageDao.read("TitleRemove"));
    }

    private StoryBook getStoryBook(Image image) {
        StoryBook storyBook = new StoryBook();
        storyBook.setTitle("Story Book");
        storyBook.setCoverImage(image);
        return storyBook;
    }

}