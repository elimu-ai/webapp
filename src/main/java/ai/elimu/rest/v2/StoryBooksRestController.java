package ai.elimu.rest.v2;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.enums.Language;
import ai.elimu.model.gson.content.StoryBookChapterGson;
import ai.elimu.model.gson.content.StoryBookGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
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
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        JSONArray storyBooksJsonArray = new JSONArray();
        for (StoryBook storyBook : storyBookDao.readAllOrdered(language)) {
            StoryBookGson storyBookGson = JavaToGsonConverter.getStoryBookGson(storyBook);
            
            List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
            for (StoryBookChapter storyBookChapter : storyBookChapterDao.readAll(storyBook)) {
                StoryBookChapterGson storyBookChapterGson = JavaToGsonConverter.getStoryBookChapter(storyBookChapter);
                storyBookChapterGson.setStoryBook(null);
                storyBookChapterGsons.add(storyBookChapterGson);
            }
            storyBookGson.setStoryBookChapters(storyBookChapterGsons);
            
            String json = new Gson().toJson(storyBookGson);
            storyBooksJsonArray.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("storyBooks", storyBooksJsonArray);
        
        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
