package ai.elimu.web.content.storybook;

import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import java.io.IOException;
import java.io.OutputStream;
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
    
    @RequestMapping(value="/storybooks.csv", method = RequestMethod.GET)
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream) {
        logger.info("handleRequest");
        
        // Generate CSV file
        String csvFileContent = "id,title,description,content_license,attribution_url,grade_level" + "\n";
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<StoryBook> storyBooks = storyBookDao.readAllOrdered(language);
        logger.info("storyBooks.size(): " + storyBooks.size());
        for (StoryBook storyBook : storyBooks) {
            csvFileContent += storyBook.getId() + ","
                    + "\"" + storyBook.getTitle() + "\","
                    + "\"" + storyBook.getDescription() + "\","
                    + storyBook.getContentLicense()+ ","
                    + "\"" + storyBook.getAttributionUrl() + "\","
                    + storyBook.getGradeLevel() + "\n";
                    // TOOD: add chapters
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
