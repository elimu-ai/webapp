package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EPubParagraphExtractionHelperTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testExtractParagraphsFromChapterFile_BEN_GDL_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("\n" +
                " আজকে ছুটির দিন আনন্দে হারাই!\n" +
                " চলো সবে পোশাকের উৎসবে যাই!\n" +
                ""));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1349() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1349.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("\n"
                + " Fifth grade student, Little Miss Grace,\n"
                + ""));
        assertThat(storyBookParagraphs.get(1), is("\n"
                + " was totally fascinated by outer space .\n"
                + ""));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_SWA_GDL_30() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("swa-gdl-30.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("\n" +
                "Kuku na Jongoo walikuwa marafiki.\n" +
                ""));
    }
}
