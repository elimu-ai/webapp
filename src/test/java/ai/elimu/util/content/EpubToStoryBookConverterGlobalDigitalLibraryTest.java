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
        assertThat(storyBook.getDescription(), is("পোশাক উৎসবের বর্ণ, শব্দ, গন্ধ ও অনুভূতি উপভোগ করো।"));
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
        assertThat(storyBook.getDescription(), is("Girl travels with her uncle to space and returns years later."));
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
        assertThat(storyBook.getDescription(), is("बादल घुमने चला. उसे कौन-कौन मिला ? फिर वह क्या करने लगा ?"));
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
        assertThat(storyBook.getDescription(), is("Hii ni hadithi ya Kuku na Jongoo (kiwango cha 4),  ambayo imebadilishwa kufaa kiwango cha 1."));
        assertThat(storyBook.getParagraphs().size(), is(20));
    }
}
