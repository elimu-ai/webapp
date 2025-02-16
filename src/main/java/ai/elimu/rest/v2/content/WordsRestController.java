package ai.elimu.rest.v2.content;

import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/v2/content/words", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WordsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @GetMapping
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
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
