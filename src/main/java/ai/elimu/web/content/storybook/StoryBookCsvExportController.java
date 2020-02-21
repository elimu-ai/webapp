package ai.elimu.web.content.storybook;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/list")
public class StoryBookCsvExportController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @RequestMapping(value="/storybooks.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,title,description,content_license,attribution_url,grade_level,chapter_ids" + "\n";
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<StoryBook> storyBooks = storyBookDao.readAllOrdered(language);
        logger.info("storyBooks.size(): " + storyBooks.size());
        for (StoryBook storyBook : storyBooks) {
            logger.info("storyBook.getTitle(): \"" + storyBook.getTitle() + "\"");
            
            List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
            logger.info("storyBookChapters.size(): " + storyBookChapters.size());
            long[] chapterIdsArray = new long[storyBookChapters.size()];
            int index = 0;
            for (StoryBookChapter storyBookChapter : storyBookChapters) {
                chapterIdsArray[index] = storyBookChapter.getId();
                index++;
            }
            
            csvFileContent += storyBook.getId() + ","
                    + "\"" + storyBook.getTitle() + "\","
                    + "\"" + storyBook.getDescription() + "\","
                    + storyBook.getContentLicense()+ ","
                    + "\"" + storyBook.getAttributionUrl() + "\","
                    + storyBook.getGradeLevel() + ","
                    + Arrays.toString(chapterIdsArray) + "\n";
                    // TODO: add paragraphs
        }
        
        response.setContentType("text/csv");
        byte[] bytes = csvFileContent.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            logger.error(null, ex);
        }
    }
}
