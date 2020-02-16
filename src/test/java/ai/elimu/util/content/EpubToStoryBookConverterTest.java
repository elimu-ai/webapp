package ai.elimu.util.content;

import ai.elimu.model.content.StoryBook;
import ai.elimu.util.content.multimedia.EpubToStoryBookConverter;
import ai.elimu.web.content.storybook.StoryBookListController;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EpubToStoryBookConverterTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testgetStoryBookFromEpub_graceInSpace() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("Grace_in_Space.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverter.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("Grace in Space"));
        assertThat(storyBook.getParagraphs().size(), is(39));
    }
}
