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

public class EmojisRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/emojis");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray emojisJSONArray = new JSONArray(jsonResponse);
        logger.info("emojisJSONArray.length(): " + emojisJSONArray.length());
        assertFalse(emojisJSONArray.isEmpty());
        
        JSONObject emojiJsonObject = emojisJSONArray.getJSONObject(0);
        assertNotNull(emojiJsonObject.getString("glyph"));
    }
}
