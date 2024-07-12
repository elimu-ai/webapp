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

public class StoryBooksRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/storybooks");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray storyBooksJSONArray = new JSONArray(jsonResponse);
        logger.info("storyBooksJSONArray.length(): " + storyBooksJSONArray.length());
        assertFalse(storyBooksJSONArray.isEmpty());
        
        JSONObject storyBookJsonObject = storyBooksJSONArray.getJSONObject(0);
        assertNotNull(storyBookJsonObject.getString("title"));
        
        JSONArray chaptersJsonArray = storyBookJsonObject.getJSONArray("storyBookChapters");
        logger.info("chaptersJsonArray.length(): " + chaptersJsonArray.length());
        assertFalse(chaptersJsonArray.isEmpty());
    }
}
