package ai.elimu.rest.v2.content;

import ai.elimu.dao.AllophoneDao;
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

@RestController
@RequestMapping(value = "/rest/v2/content/allophones", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SoundsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        JSONArray allophonesJsonArray = new JSONArray();
        for (Allophone allophone : allophoneDao.readAllOrdered()) {
            AllophoneGson allophoneGson = JpaToGsonConverter.getAllophoneGson(allophone);
            String json = new Gson().toJson(allophoneGson);
            allophonesJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = allophonesJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
