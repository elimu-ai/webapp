package ai.elimu.rest.v1.content.multimedia;

import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.dao.VideoDao;
import ai.elimu.model.content.multimedia.Video;
import ai.elimu.model.enums.Locale;
import ai.elimu.model.gson.content.multimedia.VideoGson;
import ai.elimu.rest.v1.JavaToGsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v1/content/multimedia/video", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VideoRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private VideoDao videoDao;
    
    @RequestMapping("/list")
    public String list(
            HttpServletRequest request,
            @RequestParam String deviceId,
            // TODO: checksum,
            @RequestParam Locale locale
    ) {
        logger.info("list");
        
        logger.info("request.getQueryString(): " + request.getQueryString());
        
        JSONArray jsonArray = new JSONArray();
        for (Video video : videoDao.readAllOrdered(locale)) {
            VideoGson videoGson = JavaToGsonConverter.getVideoGson(video);
            String json = new Gson().toJson(videoGson);
            jsonArray.put(new JSONObject(json));
        }
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "success");
        jsonObject.put("videos", jsonArray);
        logger.info("jsonObject: " + jsonObject);
        return jsonObject.toString();
    }
}
