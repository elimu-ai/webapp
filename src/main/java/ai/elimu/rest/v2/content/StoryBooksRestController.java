package ai.elimu.rest.v2.content;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.enums.Language;
import ai.elimu.model.gson.v2.content.StoryBookChapterGson;
import ai.elimu.model.gson.v2.content.StoryBookGson;
import ai.elimu.model.gson.v2.content.StoryBookParagraphGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import ai.elimu.util.ConfigHelper;
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
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        JSONArray storyBooksJsonArray = new JSONArray();
        for (StoryBook storyBook : storyBookDao.readAllOrdered(language)) {
            StoryBookGson storyBookGson = JpaToGsonConverter.getStoryBookGson(storyBook);
            
            // Add chapters
            List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
            for (StoryBookChapter storyBookChapter : storyBookChapterDao.readAll(storyBook)) {
                StoryBookChapterGson storyBookChapterGson = JpaToGsonConverter.getStoryBookChapterGson(storyBookChapter);
                
                // Add paragraphs
                List<StoryBookParagraphGson> storyBookParagraphGsons = new ArrayList<>();
                for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll(storyBookChapter)) {
                    StoryBookParagraphGson storyBookParagraphGson = JpaToGsonConverter.getStoryBookParagraphGson(storyBookParagraph);
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
