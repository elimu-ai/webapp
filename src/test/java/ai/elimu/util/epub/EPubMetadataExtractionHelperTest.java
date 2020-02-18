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

public class EPubMetadataExtractionHelperTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testExtractTitleFromOpfFile_GLOBAL_DIGITAL_LIBRARY() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_book.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertThat(title, is("উৎসব"));
        
        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertThat(description, is("পোশাক উৎসবের বর্ণ, শব্দ, গন্ধ ও অনুভূতি উপভোগ করো।"));
    }
    
    @Test
    public void testExtractTitleFromOpfFile_LETS_READ_ASIA() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertThat(title, is("Hindi na Ako natatakot!"));
        
        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertThat(description, is("Ang batang si Marah,  mag-isang lumabas sa dilim.  Matatakot kaya siya?"));
    }
}
