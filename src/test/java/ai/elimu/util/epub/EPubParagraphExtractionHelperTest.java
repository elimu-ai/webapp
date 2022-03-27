package ai.elimu.util.epub;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EPubParagraphExtractionHelperTest {
    
    private final Logger logger = LogManager.getLogger();
    
    @Test
    public void testExtractParagraphsFromChapterFile_BEN_GDL_761() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-gdl-761.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("আজকে ছুটির দিন আনন্দে হারাই! চলো সবে পোশাকের উৎসবে যাই!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_BEN_SW_11791() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("ben-sw-11791-ghumkature-bhim.epub_2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("ভীমের শুধু ঘুম আর ঘুম। সকালে উঠতেই পারে না।"));
        assertThat(storyBookParagraphs.get(1), is("ধোপা রামু সুযোগ পেলেই ভীমকে বকা দেয়।"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1349() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1349.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Fifth grade student, Little Miss Grace,"));
        assertThat(storyBookParagraphs.get(1), is("was totally fascinated by outer space."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Some wild cats have stripes."));
        assertThat(storyBookParagraphs.get(1), is("Like the tiger!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Some wild cats have spots."));
        assertThat(storyBookParagraphs.get(1), is("Like the leopard and the leopard cat!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch1() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_1.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("The moon rises."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_faa0d66e() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-faa0d66e-564f-4d72-a1d3-ec46fb754205.epub_Page_3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(3));
        assertThat(storyBookParagraphs.get(0), is("WAAAAHHHH!"));
        assertThat(storyBookParagraphs.get(1), is("Ang ibong Brahminy ay umiiyak tulad ng isang gutom na sanggol."));
        assertThat(storyBookParagraphs.get(2), is("WAAAAHHHH!"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_7f877260_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-7f877260-ec7c-4970-b6e2-2ee41231d96d.epub_Page_4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("Ano kaya kung kaya kang palundagin ng jelly beans nang napakataas?"));
        assertThat(storyBookParagraphs.get(1), is("Maari kang makarating sa paaralan sa isang malaking hakbang."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_FIL_LRA_7f877260_ch13() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fil-lra-7f877260-ec7c-4970-b6e2-2ee41231d96d.epub_Page_13.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("\"Nagmumuni-muni lang,\" sabi niya."));
    }
    
    @Ignore // TODO: handle &#xa0; interpreted as white space: "कुत्ता &#xa0;सैर"
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1287_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1287.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("उस मोटे राजा के पास एक पतला कुत्ता था । एक दिन मोटा राजा और उसका पतला कुत्ता सैर करने गए।"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("एक बादल घुमने चला."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("साथ में चिड़िया भी उड़ती"));
        assertThat(storyBookParagraphs.get(1), is("हुई चली."));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_99651_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-99651-hamare-mitra-kon-hai.epub_3.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(2));
        assertThat(storyBookParagraphs.get(0), is("हमारा सबसे अच्छा मित्र है पक्षी!"));
        assertThat(storyBookParagraphs.get(1), is("(चिड़िया)"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_10145_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-10145-ek-sau-saintisvan-paer.epub_4.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(3));
        assertThat(storyBookParagraphs.get(0), is("एक गोजर एक बड़े से भूरे पत्ते के भीतर"));
        assertThat(storyBookParagraphs.get(1), is("ख़ुमारी में दुबकी हुई थी।"));
        assertThat(storyBookParagraphs.get(2), is("चिड़ियों की चहचहाट ने उसे जगा दिया।"));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_141016_ch8() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-141016-tumi-ke-park-ka-din.epub_8.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("\"देखो माँ, मैं बंदर की तरह झूल रही हूँ।\""));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_SWA_GDL_30() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("swa-gdl-30.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        logger.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile);
        assertThat(storyBookParagraphs.size(), is(1));
        assertThat(storyBookParagraphs.get(0), is("Kuku na Jongoo walikuwa marafiki."));
    }
}
