package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import lombok.extern.slf4j.Slf4j;
import selenium.util.DomainHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class WordsRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/words");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray wordsJSONArray = new JSONArray(jsonResponse);
        log.info("wordsJSONArray.length(): " + wordsJSONArray.length());
        assertFalse(wordsJSONArray.isEmpty());
        
        JSONObject wordJsonObject = wordsJSONArray.getJSONObject(0);
        assertNotNull(wordJsonObject.getLong("id"));
        assertNotNull(wordJsonObject.getString("text"));
    }
}
