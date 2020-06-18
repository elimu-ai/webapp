package ai.elimu.rest.v2.crowdsource.peer_review;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/crowdsource/peer-review/words", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WordReviewRestController {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        // TODO: list Word creation events (see ContentCreationEvent)
        
        JSONArray wordsJsonArray = new JSONArray();
        for (Word word : wordDao.readAllOrdered()) {
            WordGson wordGson = JpaToGsonConverter.getWordGson(word);
            String json = new Gson().toJson(wordGson);
            wordsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = wordsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
