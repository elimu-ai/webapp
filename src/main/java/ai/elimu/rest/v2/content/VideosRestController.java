package ai.elimu.rest.v2.content;

import ai.elimu.dao.VideoDao;
import ai.elimu.entity.content.multimedia.Video;
import ai.elimu.model.v2.gson.content.VideoGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/videos", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VideosRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private VideoDao videoDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpServletRequest request) {
        logger.info("handleGetRequest");
        
        JSONArray videosJsonArray = new JSONArray();
        for (Video video : videoDao.readAllOrdered()) {
            VideoGson videoGson = JpaToGsonConverter.getVideoGson(video);
            
            String json = new Gson().toJson(videoGson);
            videosJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = videosJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
