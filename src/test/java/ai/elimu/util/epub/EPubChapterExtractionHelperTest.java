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

public class EPubChapterExtractionHelperTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_GLOBAL_DIGITAL_LIBRARY() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_toc.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(xhtmlFile);
        assertThat(chapterReferences.size(), is(12));
    }
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_LETS_READ_ASIA() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_toc.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFile(xhtmlFile);
        assertThat(chapterReferences.size(), is(12));
    }
    
    @Test
    public void testExtractChapterReferencesFromTableOfContentsFile_STORYWEAVER() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubChapterExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_toc.ncx");
        File ncxFile = resource.getFile();
        logger.debug("ncxFile: " + ncxFile);
        
        List<String> chapterReferences = EPubChapterExtractionHelper.extractChapterReferencesFromTableOfContentsFileNcx(ncxFile);
        assertThat(chapterReferences.size(), is(10));
    }
}
