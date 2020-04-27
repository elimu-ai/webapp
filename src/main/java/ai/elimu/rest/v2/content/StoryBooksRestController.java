package ai.elimu.rest.v2.content;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.v2.gson.content.ImageGson;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/storybooks", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoryBooksRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray storyBooksJsonArray = new JSONArray();
        for (StoryBook storyBook : storyBookDao.readAllOrdered()) {
            StoryBookGson storyBookGson = JpaToGsonConverter.getStoryBookGson(storyBook);
            
            // Remove duplicate cover image content
            // TODO: move this code block to JpaToGsonConverter?
            ImageGson coverImageGson = storyBookGson.getCoverImage();
            if (coverImageGson != null) {
                ImageGson imageGsonWithIdOnly = new ImageGson();
                imageGsonWithIdOnly.setId(coverImageGson.getId());
                storyBookGson.setCoverImage(imageGsonWithIdOnly);
            }
            
            // Add chapters
            List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
            for (StoryBookChapter storyBookChapter : storyBookChapterDao.readAll(storyBook)) {
                StoryBookChapterGson storyBookChapterGson = JpaToGsonConverter.getStoryBookChapterGson(storyBookChapter);
                
                // Remove duplicate image content
                // TODO: move this code block to JpaToGsonConverter?
                if (storyBookChapterGson.getImage() != null) {
                    ImageGson imageGsonWithIdOnly = new ImageGson();
                    imageGsonWithIdOnly.setId(storyBookChapterGson.getImage().getId());
                    storyBookChapterGson.setImage(imageGsonWithIdOnly);
                }
                
                // Add paragraphs
                List<StoryBookParagraphGson> storyBookParagraphGsons = new ArrayList<>();
                for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll(storyBookChapter)) {
                    StoryBookParagraphGson storyBookParagraphGson = JpaToGsonConverter.getStoryBookParagraphGson(storyBookParagraph);
                    
                    // Remove duplicate word content
                    // TODO: move this code block to JpaToGsonConverter?
                    List<WordGson> wordGsons = storyBookParagraphGson.getWords();
                    List<WordGson> wordGsonsWithIdOnly = new ArrayList<>();
                    for (WordGson wordGson : wordGsons) {
                        WordGson wordGsonWithIdOnly = new WordGson();
                        if (wordGson != null) {
                            wordGsonWithIdOnly.setId(wordGson.getId());
                        }
                        wordGsonsWithIdOnly.add(wordGsonWithIdOnly);
                    }
                    storyBookParagraphGson.setWords(wordGsonsWithIdOnly);
                    
                    storyBookParagraphGsons.add(storyBookParagraphGson);
                }
                storyBookChapterGson.setStoryBookParagraphs(storyBookParagraphGsons);
                
                storyBookChapterGsons.add(storyBookChapterGson);
            }
            storyBookGson.setStoryBookChapters(storyBookChapterGsons);
            
            String json = new Gson().toJson(storyBookGson);
            storyBooksJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = storyBooksJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
