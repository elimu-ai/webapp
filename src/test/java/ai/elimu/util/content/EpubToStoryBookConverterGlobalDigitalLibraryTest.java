package ai.elimu.util.content;

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

public class EpubToStoryBookConverterGlobalDigitalLibraryTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testGetStoryBookFromEpub_Bengali() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("ben-gdl-761.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverterGlobalDigitalLibrary.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("উৎসব"));
        assertThat(storyBook.getParagraphs().size(), is(15));
    }
    
    @Test
    public void testGetStoryBookFromEpub_English() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("eng-gdl-1349.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverterGlobalDigitalLibrary.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("Grace in Space"));
        assertThat(storyBook.getParagraphs().size(), is(39));
    }
    
    @Test
    public void testGetStoryBookFromEpub_Hindi() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("hin-gdl-1296.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverterGlobalDigitalLibrary.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("कहानी- बादल की सैर"));
        assertThat(storyBook.getParagraphs().size(), is(69));
    }
    
    @Test
    public void testGetStoryBookFromEpub_Swahili() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(StoryBookListController.class);
        Resource ePubResource = resourceLoader.getResource("swa-gdl-30.epub");
        logger.info("ePubResource: " + ePubResource);
        File ePubFile = ePubResource.getFile();
        
        StoryBook storyBook = EpubToStoryBookConverterGlobalDigitalLibrary.getStoryBookFromEpub(ePubFile);
        assertThat(storyBook, not(nullValue()));
        assertThat(storyBook.getTitle(), is("Kuku na Jongoo"));
        assertThat(storyBook.getParagraphs().size(), is(20));
    }
}
