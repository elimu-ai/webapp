package ai.elimu.rest.v2.content;

import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.v2.gson.content.LetterSoundGson;
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
import ai.elimu.dao.LetterSoundDao;

@RestController
@RequestMapping(value = "/rest/v2/content/letter-sounds", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LetterSoundsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray letterSoundsJsonArray = new JSONArray();
        for (LetterSoundCorrespondence letterSound : letterSoundDao.readAllOrderedByUsage()) {
            LetterSoundGson letterSoundGson = JpaToGsonConverter.getLetterSoundGson(letterSound);
            String json = new Gson().toJson(letterSoundGson);
            letterSoundsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = letterSoundsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
