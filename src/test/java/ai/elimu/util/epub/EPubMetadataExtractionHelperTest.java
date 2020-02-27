package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EPubMetadataExtractionHelperTest {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testExtractTitleFromOpfFile_BEN_GLOBAL_DIGITAL_LIBRARY() throws IOException {
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
    public void testExtractTitleFromOpfFile_FIL_LETS_READ_ASIA_0acfe340() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertThat(title, is("Hindi na Ako natatakot!"));
        
        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertThat(description, is("Ang batang si Marah,  mag-isang lumabas sa dilim.  Matatakot kaya siya?"));
    }
    
    @Test
    public void testExtractTitleFromOpfFile_FIL_LETS_READ_ASIA_627f64f8() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-627f64f8-a002-4c0f-8bb6-3701ce5fcf19.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertThat(title, is("Si Pusa at si Aso at ang Sumbrero"));
        
        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertThat(description, is("Mayroong mga bagong sumbrero sina Pusa at Aso! Ngunit ano ang kanilang gagawin pagkatapos tangayin ng malakas  na hangin ang kani-kaniyang sumbrero sa tubig? Sana, mayroon silang kaibigan na nauuhaw."));
    }
    
    @Test
    public void testExtractTitleFromOpfFile_FIL_STORYWEAVER_105391() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-sw-105391-tayo-ay-magbilang.epub_package.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertThat(title, is("Tayo ay Magbilang!"));
        
        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertThat(description, is(nullValue()));
    }
    
    @Test
    public void testExtractTitleFromOpfFile_BEN_STORYWEAVER_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_package.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertThat(title, is("ঘুমকাতুরে ভীম"));
        
        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertThat(description, is(nullValue()));
    }
    
    
    @Test
    public void testExtractCoverImageReferenceFromOpfFile_FIL_GLOBAL_DIGITAL_LIBRARY_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_book.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        assertThat(coverImageReference, is("99e3d3af620881991813482fb602a1f6.jpg"));
    }
    
    @Test
    public void testExtractCoverImageReferenceFromOpfFile_FIL_LETS_READ_ASIA_0acfe340() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        assertThat(coverImageReference, is("coverImage.jpeg"));
    }
    
    @Test
    public void testExtractCoverImageReferenceFromOpfFile_BEN_STORYWEAVER_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_package.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        assertThat(coverImageReference, is("image_1.jpg"));
    }
}
