package ai.elimu.web.content.storybook;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.v2.gson.content.ImageGson;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.json.JSONArray;
import org.json.JSONObject;

@Controller
@RequestMapping("/content/storybook/list")
public class StoryBookCsvExportController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @GetMapping(value="/storybooks.csv")
    public void handleRequest(
            HttpServletResponse response,
            OutputStream outputStream
    ) throws IOException {
        logger.info("handleRequest");
        
        List<StoryBook> storyBooks = storyBookDao.readAllOrderedById();
        logger.info("storyBooks.size(): " + storyBooks.size());
        
        CSVFormat csvFormat = CSVFormat.DEFAULT
                .withHeader(
                        "id",
                        "title",
                        "description",
                        "content_license",
                        "attribution_url",
                        "reading_level",
                        "cover_image_id",
                        "chapters"
                );
        StringWriter stringWriter = new StringWriter();
        CSVPrinter csvPrinter = new CSVPrinter(stringWriter, csvFormat);
        
        for (StoryBook storyBook : storyBooks) {
            logger.info("storyBook.getTitle(): \"" + storyBook.getTitle() + "\"");
            
            Long coverImageId = null;
            if (storyBook.getCoverImage() != null) {
                coverImageId = storyBook.getCoverImage().getId();
            }
            
            // Store chapters as JSON objects
            JSONArray chaptersJsonArray = new JSONArray();
            List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
            logger.info("storyBookChapters.size(): " + storyBookChapters.size());
            for (StoryBookChapter storyBookChapter : storyBookChapters) {
                logger.info("storyBookChapter.getId(): " + storyBookChapter.getId());
                
                StoryBookChapterGson storyBookChapterGson = JpaToGsonConverter.getStoryBookChapterGson(storyBookChapter);
                
                // Remove duplicate image content
                // TODO: move this code block to JpaToGsonConverter?
                if (storyBookChapterGson.getImage() != null) {
                    ImageGson imageGsonWithIdOnly = new ImageGson();
                    imageGsonWithIdOnly.setId(storyBookChapterGson.getImage().getId());
                    storyBookChapterGson.setImage(imageGsonWithIdOnly);
                }
                
                // Store paragraphs as JSON objects
                List<StoryBookParagraphGson> storyBookParagraphs = new ArrayList<>();
                logger.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
                for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll(storyBookChapter)) {
                    logger.info("storyBookParagraph.getId(): " + storyBookParagraph.getId());
                    
                    StoryBookParagraphGson storyBookParagraphGson = JpaToGsonConverter.getStoryBookParagraphGson(storyBookParagraph);
                    storyBookParagraphGson.setWords(null);
                    storyBookParagraphs.add(storyBookParagraphGson);
                }
                storyBookChapterGson.setStoryBookParagraphs(storyBookParagraphs);
                
                String json = new Gson().toJson(storyBookChapterGson);
                JSONObject jsonObject = new JSONObject(json);
                logger.info("jsonObject: " + jsonObject);
                chaptersJsonArray.put(jsonObject);
            }
            logger.info("chaptersJsonArray: " + chaptersJsonArray);
            
            csvPrinter.printRecord(
                    storyBook.getId(),
                    storyBook.getTitle(),
                    storyBook.getDescription(),
                    storyBook.getContentLicense(),
                    storyBook.getAttributionUrl(),
                    storyBook.getReadingLevel(),
                    coverImageId,
                    chaptersJsonArray
            );
            
            csvPrinter.flush();
        }
        csvPrinter.close();
        
        String csvFileContent = stringWriter.toString();
        
        response.setContentType("text/csv");
        byte[] bytes = csvFileContent.getBytes();
        response.setContentLength(bytes.length);
        try {
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            logger.error(ex);
        }
    }
}
