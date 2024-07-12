package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import selenium.DomainHelper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WordsRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/words");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray wordsJSONArray = new JSONArray(jsonResponse);
        logger.info("wordsJSONArray.length(): " + wordsJSONArray.length());
        assertFalse(wordsJSONArray.isEmpty());
        
        JSONObject wordJsonObject = wordsJSONArray.getJSONObject(0);
        assertNotNull(wordJsonObject.getLong("id"));
        assertNotNull(wordJsonObject.getString("text"));
    }
}
