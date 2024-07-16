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

public class EPubImageExtractionHelperTest {
    
    private final Logger logger = LogManager.getLogger();
    
    @Test
    public void testExtractImageReferenceFromChapterFile_BEN_GDL_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("1e8e58cc7d627a7896737cfb3eba8270.jpg", imageReference);
    }
    
    @Test
    public void testExtractImageReferenceFromChapterFile_BEN_SW_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_2.jpg", imageReference);
    }
    
    @Test
    public void testExtractImageReferenceFromChapterFile_ENG_LRA_377b7e63() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_1.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("p-1.jpg", imageReference);
    }
    
    @Test
    public void testExtractImageReferenceFromChapterFile_TGL_LRA_faa0d66e() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-faa0d66e-564f-4d72-a1d3-ec46fb754205.epub_Page_3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_3.jpg", imageReference);
    }
}
