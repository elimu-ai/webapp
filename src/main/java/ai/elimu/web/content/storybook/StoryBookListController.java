package ai.elimu.web.content.storybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.GradeLevel;
import ai.elimu.model.enums.Language;
import ai.elimu.util.content.multimedia.EpubToStoryBookConverter;
import java.io.File;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/list")
public class StoryBookListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate StoryBooks
        List<StoryBook> storyBooksGenerated = generateStoryBooks(contributor.getLanguage());
        for (StoryBook storyBook : storyBooksGenerated) {
            StoryBook existingStoryBook = storyBookDao.readByTitle(storyBook.getLanguage(), storyBook.getTitle());
            if (existingStoryBook == null) {
                
                storyBookDao.create(storyBook);
            }
        }
        
        List<StoryBook> storyBooks = storyBookDao.readAllOrdered(contributor.getLanguage());
        model.addAttribute("storyBooks", storyBooks);

        return "content/storybook/list";
    }
    
    private List<StoryBook> generateStoryBooks(Language language) {
        List<StoryBook> storyBooks = new ArrayList<>();
        
        if (language == Language.BEN) {
            URL urlউৎসব = getClass().getResource("ben-761.epub");
            logger.info("urlউৎসব: " + urlউৎসব);
            StoryBook storyBookউৎসব = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlউৎসব.getFile()));
            Image coverImageউৎসব = imageDao.read("99e3d3af620881991813482fb602a1f6", language);
            storyBookউৎসব.setCoverImage(coverImageউৎসব);
            storyBookউৎসব.setGradeLevel(GradeLevel.LEVEL1);
            storyBookউৎসব.setLanguage(language);
            storyBookউৎসব.setTimeLastUpdate(Calendar.getInstance());
            storyBooks.add(storyBookউৎসব);
            
            URL urlBen790 = getClass().getResource("ben-790.epub");
            logger.info("urlBen790: " + urlBen790);
            StoryBook storyBookBen790 = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlBen790.getFile()));
            Image coverImageBen790 = imageDao.read("15e689585b33730512e002e532f57ea9", language);
            storyBookBen790.setCoverImage(coverImageBen790);
            storyBookBen790.setGradeLevel(GradeLevel.LEVEL3);
            storyBookBen790.setLanguage(language);
            storyBookBen790.setTimeLastUpdate(Calendar.getInstance());
            storyBooks.add(storyBookBen790);
            
            URL urlBen767 = getClass().getResource("ben-767.epub");
            logger.info("urlBen767: " + urlBen767);
            StoryBook storyBookBen767 = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlBen767.getFile()));
            Image coverImageBen767 = imageDao.read("344cb250fee33bdabf4aff6603eb3506", language);
            storyBookBen767.setCoverImage(coverImageBen767);
            storyBookBen767.setGradeLevel(GradeLevel.LEVEL3);
            storyBookBen767.setLanguage(language);
            storyBookBen767.setTimeLastUpdate(Calendar.getInstance());
            storyBooks.add(storyBookBen767);
            
            URL urlBen770 = getClass().getResource("ben-770.epub");
            logger.info("urlBen770: " + urlBen770);
            StoryBook storyBookBen770 = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlBen770.getFile()));
            Image coverImageBen770 = imageDao.read("e27c3643f7ba51abda1f02f07549577c", language);
            storyBookBen770.setCoverImage(coverImageBen770);
            storyBookBen770.setGradeLevel(GradeLevel.LEVEL4);
            storyBookBen770.setLanguage(language);
            storyBookBen770.setTimeLastUpdate(Calendar.getInstance());
            storyBooks.add(storyBookBen770);
        } else if (language == Language.ENG) {
            StoryBook storyBook = new StoryBook();
            storyBook.setLanguage(language);
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBook.setTitle("Too Small");
            Image coverImage = imageDao.read("M_ASP_55_Too_small_Page_02_Image_0001", language);
            storyBook.setCoverImage(coverImage);
            storyBook.setGradeLevel(GradeLevel.LEVEL3);
            List<String> paragraphs = new ArrayList<>();
            paragraphs.add("\"Mom,\" called Lebo. \"Come and look. These clothes are all too small for me!\"");
            paragraphs.add("\"Let me see,\" said Mom.");
            paragraphs.add("\"Look at my skirt. It's too small,\" said Lebo.");
            paragraphs.add("\"Yes, it is,\" said Mom. \"Nomsa can have your skirt.\"");
            paragraphs.add("\"Look at my jeans. They are too small,\" said Lebo.");
            paragraphs.add("\"Yes, they are,\" said Mom. \"Nomsa can have your jeans.");
            paragraphs.add("\"Look at my T-shirt. It's too small,\" said Lebo.");
            paragraphs.add("\"Yes, it is,\" said Mom. \"Nomsa can have your T-shirt.");
            paragraphs.add("\"Look at my jersey. It is too small,\" said Lebo.");
            paragraphs.add("\"Yes, it is,\" said Mom. \"We will give your jersey to Nomsa.\"");
            paragraphs.add("\"Look at my raincoat. It's too small,\" said Lebo.");
            paragraphs.add("\"Yes, it is. Nomsa can have your raincoat,\" said Mom.");
            paragraphs.add("\"Look at my socks. They are too small,\" said Lebo.");
            paragraphs.add("\"Yes, they certainly are,\" said Mom. \"Nomsa can have your socks.\"");
            paragraphs.add("\"Look at my shoes. They are too small,\" said Lebo.");
            paragraphs.add("\"Yes, they are,\" said Mom. \"Nomsa can have your shoes.\"");
            paragraphs.add("\"Now you have lots of clothes,\" said Lebo.");
            paragraphs.add("\"Oh, no, I don't,\" said Nomsa.");
            paragraphs.add("\"These clothes are all too BIG for me!\"");
            storyBook.setParagraphs(paragraphs);
            storyBooks.add(storyBook);

            URL urlGraceInSpace = getClass().getResource("Grace_in_Space.epub");
            logger.info("urlGraceInSpace: " + urlGraceInSpace);
            StoryBook storyBookGraceInSpace = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlGraceInSpace.getFile()));
            Image coverImageGraceInSpace = imageDao.read("39e5eb1614ea195e9e377f63f561aa8c", language);
            storyBookGraceInSpace.setCoverImage(coverImageGraceInSpace);
            storyBookGraceInSpace.setGradeLevel(GradeLevel.LEVEL2);
            storyBookGraceInSpace.setLanguage(language);
            storyBookGraceInSpace.setTimeLastUpdate(Calendar.getInstance());
            storyBooks.add(storyBookGraceInSpace);

            URL urlWhatIf_ = getClass().getResource("What_If_.epub");
            logger.info("urlWhatIf_: " + urlWhatIf_);
            StoryBook storyBookWhatIf_ = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlWhatIf_.getFile()));
            Image coverImageWhatIf_ = imageDao.read("badd7122aa68d2a339e359f03c03cc51", language);
            storyBookWhatIf_.setCoverImage(coverImageWhatIf_);
            storyBookWhatIf_.setGradeLevel(GradeLevel.LEVEL1);
            storyBookWhatIf_.setLanguage(language);
            storyBookWhatIf_.setTimeLastUpdate(Calendar.getInstance());
            storyBooks.add(storyBookWhatIf_);
        } else if (language == Language.FIL) {
//            URL urlHindiNaAkoNatatakot = getClass().getResource("Hindi_na_Ako_natatakot.epub");
//            logger.info("urlHindiNaAkoNatatakot: " + urlHindiNaAkoNatatakot);
//            StoryBook storyBookHindiNaAkoNatatakot = EpubToStoryBookConverter.getStoryBookFromEpub(new File(urlHindiNaAkoNatatakot.getFile()));
//            Image coverImageWhatIf_ = imageDao.read("Hindi_na_Ako_natatakot_coverImage", language);
//            storyBookHindiNaAkoNatatakot.setCoverImage(coverImageWhatIf_);
//            storyBookHindiNaAkoNatatakot.setGradeLevel(GradeLevel.LEVEL1);
//            storyBookHindiNaAkoNatatakot.setLanguage(language);
//            storyBookHindiNaAkoNatatakot.setTimeLastUpdate(Calendar.getInstance());
//            storyBooks.add(storyBookHindiNaAkoNatatakot);
        } else if (language == Language.HIN) {
            // TOOD
        } else if (language == Language.SWA) {
            // TOOD
        }
        
        return storyBooks;
    }
}
