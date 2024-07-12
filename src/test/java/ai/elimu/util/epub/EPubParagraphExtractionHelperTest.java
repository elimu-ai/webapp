package ai.elimu.util.epub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EPubParagraphExtractionHelperTest {
    
    private final Logger logger = LogManager.getLogger();

    @Test
    public void testExtractParagraphsFromChapterFile_BEN_GDL_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("আজকে ছুটির দিন আনন্দে হারাই! চলো সবে পোশাকের উৎসবে যাই!", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_BEN_SW_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("ভীমের শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।", storyBookParagraphs.get(0));
        assertEquals("ধোপা রামু সুযোগ পেলেই ভীমকে বকা দেয়।", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1349() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1349.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Fifth grade student, Little Miss Grace,", storyBookParagraphs.get(0));
        assertEquals("was totally fascinated by outer space.", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Some wild cats have stripes.", storyBookParagraphs.get(0));
        assertEquals("Like the tiger!", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Some wild cats have spots.", storyBookParagraphs.get(0));
        assertEquals("Like the leopard and the leopard cat!", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch1() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_1.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("The moon rises.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch5() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_5.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("What if I try talking to them? The moon slowly moves toward the group of stars.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch6() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_6.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("Will they play with me? I am so different from them. I should try.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_faa0d66e() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-faa0d66e-564f-4d72-a1d3-ec46fb754205.epub_Page_3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(3, storyBookParagraphs.size());
        assertEquals("WAAAAHHHH!", storyBookParagraphs.get(0));
        assertEquals("Ang ibong Brahminy ay umiiyak tulad ng isang gutom na sanggol.", storyBookParagraphs.get(1));
        assertEquals("WAAAAHHHH!", storyBookParagraphs.get(2));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_7f877260_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-7f877260-ec7c-4970-b6e2-2ee41231d96d.epub_Page_4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Ano kaya kung kaya kang palundagin ng jelly beans nang napakataas?", storyBookParagraphs.get(0));
        assertEquals("Maari kang makarating sa paaralan sa isang malaking hakbang.", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_7f877260_ch13() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-7f877260-ec7c-4970-b6e2-2ee41231d96d.epub_Page_13.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("\"Nagmumuni-muni lang,\" sabi niya.", storyBookParagraphs.get(0));
    }

    @Disabled // TODO: handle &#xa0; interpreted as white space: "कुत्ता &#xa0;सैर"
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1287_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1287.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("उस मोटे राजा के पास एक पतला कुत्ता था । एक दिन मोटा राजा और उसका पतला कुत्ता सैर करने गए।", storyBookParagraphs.get(0));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("एक बादल घुमने चला.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("साथ में चिड़िया भी उड़ती", storyBookParagraphs.get(0));
        assertEquals("हुई चली.", storyBookParagraphs.get(1));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_99651_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-99651-hamare-mitra-kon-hai.epub_3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);

        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("हमारा सबसे अच्छा मित्र है पक्षी!", storyBookParagraphs.get(0));
        assertEquals("(चिड़िया)", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_10145_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-10145-ek-sau-saintisvan-paer.epub_4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(3, storyBookParagraphs.size());
        assertEquals("एक गोजर एक बड़े से भूरे पत्ते के भीतर", storyBookParagraphs.get(0));
        assertEquals("ख़ुमारी में दुबकी हुई थी।", storyBookParagraphs.get(1));
        assertEquals("चिड़ियों की चहचहाट ने उसे जगा दिया।", storyBookParagraphs.get(2));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_141016_ch8() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-141016-tumi-ke-park-ka-din.epub_8.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("\"देखो माँ, मैं बंदर की तरह झूल रही हूँ।\"", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_SWA_GDL_30() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("swa-gdl-30.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);

        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("Kuku na Jongoo walikuwa marafiki.", storyBookParagraphs.get(0));
    }
}
