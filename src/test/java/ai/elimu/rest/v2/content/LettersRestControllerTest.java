package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import selenium.util.DomainHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LettersRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/letters");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray lettersJSONArray = new JSONArray(jsonResponse);
        logger.info("lettersJSONArray.length(): " + lettersJSONArray.length());
        assertFalse(lettersJSONArray.isEmpty());
        
        JSONObject letterJsonObject = lettersJSONArray.getJSONObject(0);
        assertNotNull(letterJsonObject.getLong("id"));
        assertNotNull(letterJsonObject.getString("text"));
    }
}
