package ai.elimu.rest.v2.content;

import ai.elimu.model.content.Allophone;
import ai.elimu.model.v2.gson.content.AllophoneGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ai.elimu.dao.SoundDao;

@RestController
@RequestMapping(value = "/rest/v2/content/allophones", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SoundsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private SoundDao soundDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray soundsJsonArray = new JSONArray();
        for (Allophone sound : soundDao.readAllOrdered()) {
            AllophoneGson soundGson = JpaToGsonConverter.getAllophoneGson(sound);
            String json = new Gson().toJson(soundGson);
            soundsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = soundsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
