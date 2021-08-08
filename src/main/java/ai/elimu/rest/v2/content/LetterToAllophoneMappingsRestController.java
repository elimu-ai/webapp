package ai.elimu.rest.v2.content;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.v2.gson.content.LetterToAllophoneMappingGson;
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

@RestController
@RequestMapping(value = "/rest/v2/content/letter-sound-correspondences", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LetterToAllophoneMappingsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterToAllophoneMappingDao letterSoundCorrespondenceDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray letterSoundCorrespondencesJsonArray = new JSONArray();
        for (LetterToAllophoneMapping letterSoundCorrespondence : letterSoundCorrespondenceDao.readAllOrderedByUsage()) {
            LetterToAllophoneMappingGson letterSoundCorrespondenceGson = JpaToGsonConverter.getLetterToAllophoneMappingGson(letterSoundCorrespondence);
            String json = new Gson().toJson(letterSoundCorrespondenceGson);
            letterSoundCorrespondencesJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = letterSoundCorrespondencesJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
