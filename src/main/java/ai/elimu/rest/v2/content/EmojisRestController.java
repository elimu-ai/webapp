package ai.elimu.rest.v2.content;

import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.v2.gson.content.EmojiGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
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
@RequestMapping(value = "/rest/v2/content/emojis", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class EmojisRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private EmojiDao emojiDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpServletRequest request) {
        logger.info("handleGetRequest");
        
        JSONArray emojisJsonArray = new JSONArray();
        for (Emoji emoji : emojiDao.readAllOrdered()) {
            EmojiGson emojiGson = JpaToGsonConverter.getEmojiGson(emoji);
            
            String json = new Gson().toJson(emojiGson);
            emojisJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = emojisJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
