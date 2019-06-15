package ai.elimu.util.content.multimedia;

import ai.elimu.model.content.StoryBook;
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
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testgetStoryBookFromEpub_graceInSpace() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("Grace in Space.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverter.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("Grace in Space"));
        // TODO
    }
    
    @Test
    public void testgetStoryBookFromEpub_WhatIf_() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("What If_.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverter.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("What If?"));
        // TODO
    }
}
