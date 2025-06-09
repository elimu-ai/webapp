package ai.elimu.util.epub;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import ai.elimu.model.v2.enums.Language;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class EPubParagraphExtractionHelperTest {
    
    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1349() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1349.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.ENG);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Fifth grade student, Little Miss Grace,", storyBookParagraphs.get(0));
        assertEquals("was totally fascinated by outer space.", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.ENG);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Some wild cats have stripes.", storyBookParagraphs.get(0));
        assertEquals("Like the tiger!", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_GDL_1855_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-gdl-1855.epub_chapter-4.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.ENG);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Some wild cats have spots.", storyBookParagraphs.get(0));
        assertEquals("Like the leopard and the leopard cat!", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch1() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_1.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.ENG);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("The moon rises.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch5() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_5.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.ENG);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("What if I try talking to them? The moon slowly moves toward the group of stars.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_ENG_LRA_377b7e63_ch6() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("eng-lra-377b7e63-6126-4cfe-bcee-1538d75c1b2f_Page_6.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.ENG);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("Will they play with me? I am so different from them. I should try.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_TGL_LRA_faa0d66e() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-faa0d66e-564f-4d72-a1d3-ec46fb754205.epub_Page_3.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.TGL);

        assertEquals(3, storyBookParagraphs.size());
        assertEquals("WAAAAHHHH!", storyBookParagraphs.get(0));
        assertEquals("Ang ibong Brahminy ay umiiyak tulad ng isang gutom na sanggol.", storyBookParagraphs.get(1));
        assertEquals("WAAAAHHHH!", storyBookParagraphs.get(2));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_TGL_LRA_7f877260_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-7f877260-ec7c-4970-b6e2-2ee41231d96d.epub_Page_4.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.TGL);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("Ano kaya kung kaya kang palundagin ng jelly beans nang napakataas?", storyBookParagraphs.get(0));
        assertEquals("Maari kang makarating sa paaralan sa isang malaking hakbang.", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_TGL_LRA_7f877260_ch13() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tgl-lra-7f877260-ec7c-4970-b6e2-2ee41231d96d.epub_Page_13.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.TGL);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("\"Nagmumuni-muni lang,\" sabi niya.", storyBookParagraphs.get(0));
    }

    @Disabled // TODO: handle &#xa0; interpreted as white space: "कुत्ता &#xa0;सैर"
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1287_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1287.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.HIN);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("उस मोटे राजा के पास एक पतला कुत्ता था । एक दिन मोटा राजा और उसका पतला कुत्ता सैर करने गए।", storyBookParagraphs.get(0));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-2.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.HIN);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("एक बादल घुमने चला.", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_HIN_GDL_1296_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-gdl-1296.epub_chapter-3.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.HIN);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("साथ में चिड़िया भी उड़ती", storyBookParagraphs.get(0));
        assertEquals("हुई चली.", storyBookParagraphs.get(1));
    }
    
    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_99651_ch3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-99651-hamare-mitra-kon-hai.epub_3.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);

        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.HIN);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("हमारा सबसे अच्छा मित्र है पक्षी!", storyBookParagraphs.get(0));
        assertEquals("(चिड़िया)", storyBookParagraphs.get(1));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_HIN_SW_10145_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("hin-sw-10145-ek-sau-saintisvan-paer.epub_4.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.HIN);

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
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.HIN);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("\"देखो माँ, मैं बंदर की तरह झूल रही हूँ।\"", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_THA_LRA_f1562b02_ch4() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-lra-f1562b02-2883-466e-ab2e-fde46b0ee60c.epub_Page_4.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.THA);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("นี่ คือ หู ของ ฉัน ฉัน ใช้ หู เพื่อ ฟัง เสียง ต่าง ต่าง", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_THA_LRA_376896c7_ch9() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-lra-376896c7-b7de-40ee-83c3-d11e5bf68e19.epub_Page_9.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.THA);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("คุณ แม่! คุณ แม่! มอง ไป ทาง ไหน มี แต่ คน หนู ชัก สับสน ไม่ รู้ จะ เดิน ตรง ไหน", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_THA_SW_446843_ch9() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-sw-446843-kon-kong-joa-jing-jog.epub_9.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.THA);

        assertEquals(2, storyBookParagraphs.size());
        assertEquals("สัตว์ มากมาย ต่าง หลั่ง", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_THA_LRA_c2d75faf_ch8() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("tha-lra-c2d75faf-4145-424f-9f58-21182945d986.epub_Page_8.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.THA);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("“ส่วน นี่ คือ ท่า ตั้ง วง บน” “ทำ ตาม ฉัน นะ”", storyBookParagraphs.get(0));
    }

    @Test
    public void testExtractParagraphsFromChapterFile_VIE_LRA_b46cf9ee_ch2() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(EPubParagraphExtractionHelper.class);
        Resource resource = resourceLoader.getResource("vie-lra-b46cf9ee-1298-4b81-a285-ecd5c2021af6.epub_Page_2.xhtml");
        File xhtmlFile = resource.getFile();
        log.debug("xhtmlFile: " + xhtmlFile);
        
        List<String> storyBookParagraphs = EPubParagraphExtractionHelper.extractParagraphsFromChapterFile(xhtmlFile, Language.VIE);

        assertEquals(1, storyBookParagraphs.size());
        assertEquals("Chó thách Mèo vật nhau Xem thử tay nào thắng", storyBookParagraphs.get(0));
    }
}
