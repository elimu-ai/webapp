package ai.elimu.util.epub;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class EPubImageExtractionHelperTest {

    @Test	
    public void testExtractImageReferenceFromChapterFile_GLOBAL_DIGITAL_LIBRARY_eng() throws IOException {	
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);	
        Resource resource = resourceLoader.getResource("eng-gdl-1349.epub_chapter-3.xhtml");	
        File xhtmlFile = resource.getFile();	
        log.debug("xhtmlFile: " + xhtmlFile);	

        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);	
        assertEquals("21f0ca572d1f21c4813bfb910ccb935d.jpg", imageReference);	
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_STORYWEAVER_hin() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-10145-ek-sau-saintisvan-paer.epub_4.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);

        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_4.jpg", imageReference);
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_STORYWEAVER_tgl() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-sw-105391-tayo-ay-magbilang.epub_8.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);

        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_8.jpg", imageReference);
    }
    
    @Test
    public void testExtractImageReferenceFromChapterFile_LETS_READ_ASIA_eng() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_1.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("p-1.jpg", imageReference);
    }
    
    @Test
    public void testExtractImageReferenceFromChapterFile_LETS_READ_ASIA_tgl() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-faa0d66e-564f-4d72-a1d3-ec46fb754205.epub_Page_3.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_3.jpg", imageReference);
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_LETS_READ_ASIA_vie() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("vie-lra-4a049c77-4861-4513-8bf4-6d17c0199ae1.epub_Page_1.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("p-1.jpg", imageReference);
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_LETS_READ_ASIA_tha() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-lra-376896c7-b7de-40ee-83c3-d11e5bf68e19.epub_Page_9.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("p-9.jpg", imageReference);
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_STORYWEAVER_tha() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-sw-587076-phb-kan-pen-khrang-raek.epub_2.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_2.jpg", imageReference);
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_STORYWEAVER_tha_page9() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-sw-446843-kon-kong-joa-jing-jog.epub_9.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("image_9.jpg", imageReference);
    }

    @Test
    public void testExtractImageReferenceFromChapterFile_THA_LRA_c2d75faf_ch8() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubImageExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-lra-c2d75faf-4145-424f-9f58-21182945d986.epub_Page_8.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        String imageReference = EPubImageExtractionHelper.extractImageReferenceFromChapterFile(xhtmlFile);
        assertEquals("p-9.jpg", imageReference);
    }
}
