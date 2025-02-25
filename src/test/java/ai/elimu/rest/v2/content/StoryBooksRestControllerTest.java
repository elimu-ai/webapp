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
public class StoryBooksRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/storybooks");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray storyBooksJSONArray = new JSONArray(jsonResponse);
        log.info("storyBooksJSONArray.length(): " + storyBooksJSONArray.length());
        assertFalse(storyBooksJSONArray.isEmpty());
        
        JSONObject storyBookJsonObject = storyBooksJSONArray.getJSONObject(0);
        assertNotNull(storyBookJsonObject.getString("title"));
        
        JSONArray chaptersJsonArray = storyBookJsonObject.getJSONArray("storyBookChapters");
        log.info("chaptersJsonArray.length(): " + chaptersJsonArray.length());
        assertFalse(chaptersJsonArray.isEmpty());
    }
}
