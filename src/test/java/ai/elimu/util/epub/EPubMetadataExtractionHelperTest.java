package ai.elimu.util.epub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EPubMetadataExtractionHelperTest {
    
    private final Logger logger = LogManager.getLogger();

    @Test
    public void testExtractTitleFromOpfFile_BEN_GLOBAL_DIGITAL_LIBRARY() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_book.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertEquals("উৎসব", title);

        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertEquals("পোশাক উৎসবের বর্ণ, শব্দ, গন্ধ ও অনুভূতি উপভোগ করো।", description);
    }

    @Test
    public void testExtractTitleFromOpfFile_TGL_LETS_READ_ASIA_0acfe340() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertEquals("Hindi na Ako natatakot!", title);

        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertEquals("Ang batang si Marah,  mag-isang lumabas sa dilim.  Matatakot kaya siya?", description);
    }

    @Test
    public void testExtractTitleFromOpfFile_TGL_LETS_READ_ASIA_627f64f8() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-627f64f8-a002-4c0f-8bb6-3701ce5fcf19.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertEquals("Si Pusa at si Aso at ang Sumbrero", title);

        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertEquals("Mayroong mga bagong sumbrero sina Pusa at Aso! Ngunit ano ang kanilang gagawin pagkatapos tangayin ng malakas  na hangin ang kani-kaniyang sumbrero sa tubig? Sana, mayroon silang kaibigan na nauuhaw.", description);
    }

    @Test
    public void testExtractTitleFromOpfFile_TGL_STORYWEAVER_105391() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-sw-105391-tayo-ay-magbilang.epub_package.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertEquals("Tayo ay Magbilang!", title);

        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertEquals(null, description); // or assertEquals("", description);
    }

    @Test
    public void testExtractTitleFromOpfFile_BEN_STORYWEAVER_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_package.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String title = EPubMetadataExtractionHelper.extractTitleFromOpfFile(opfFile);
        assertEquals("ঘুমকাতুরে ভীম", title);

        String description = EPubMetadataExtractionHelper.extractDescriptionFromOpfFile(opfFile);
        assertEquals(null, description); // or assertEquals("", description);
    }

    
    @Test
    public void testExtractCoverImageReferenceFromOpfFile_BEN_GLOBAL_DIGITAL_LIBRARY_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_book.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        assertEquals("99e3d3af620881991813482fb602a1f6.jpg", coverImageReference);
    }

    @Test
    public void testExtractCoverImageReferenceFromOpfFile_TGL_LETS_READ_ASIA_0acfe340() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-0acfe340-6116-4f8a-a45d-c925c8a1fd0e.epub_content.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        assertEquals("coverImage.jpeg", coverImageReference);
    }

    @Test
    public void testExtractCoverImageReferenceFromOpfFile_BEN_STORYWEAVER_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_package.opf");
        File opfFile = resource.getFile();
        logger.debug("opfFile: " + opfFile);
        
        String coverImageReference = EPubMetadataExtractionHelper.extractCoverImageReferenceFromOpfFile(opfFile);
        assertEquals("image_1.jpg", coverImageReference);
    }
}
