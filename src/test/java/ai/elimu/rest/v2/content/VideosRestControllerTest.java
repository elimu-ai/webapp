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
public class VideosRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/videos");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray videosJSONArray = new JSONArray(jsonResponse);
        log.info("videosJSONArray.length(): " + videosJSONArray.length());
        assertFalse(videosJSONArray.isEmpty());
        
        JSONObject videoJsonObject = videosJSONArray.getJSONObject(0);
        assertNotNull(videoJsonObject.getString("title"));
    }
}
