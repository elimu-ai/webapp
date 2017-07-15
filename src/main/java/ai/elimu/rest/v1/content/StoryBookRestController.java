package ai.elimu.rest.v1.content;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.content.StoryBookGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/content/storybook", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StoryBookRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        JSONArray storyBooks = new JSONArray();
        for (StoryBook storyBook : storyBookDao.readAllOrdered(locale)) {
            StoryBookGson storyBookGson = JavaToGsonConverter.getStoryBookGson(storyBook);
            String json = new Gson().toJson(storyBookGson);
            storyBooks.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("storyBooks", storyBooks);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
