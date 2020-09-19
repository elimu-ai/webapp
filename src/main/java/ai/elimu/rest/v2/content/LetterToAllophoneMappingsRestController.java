package ai.elimu.rest.v2.content;

import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.v2.gson.content.LetterToAllophoneMappingGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/letter-to-allophone-mappings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LetterToAllophoneMappingsRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray letterToAllophoneMappingsJsonArray = new JSONArray();
        for (LetterToAllophoneMapping letterToAllophoneMapping : letterToAllophoneMappingDao.readAllOrderedByLetterText()) {
            LetterToAllophoneMappingGson letterToAllophoneMappingGson = JpaToGsonConverter.getLetterToAllophoneMappingGson(letterToAllophoneMapping);
            String json = new Gson().toJson(letterToAllophoneMappingGson);
            letterToAllophoneMappingsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = letterToAllophoneMappingsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
