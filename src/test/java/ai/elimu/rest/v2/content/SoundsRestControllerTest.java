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
public class SoundsRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/sounds");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray soundsJSONArray = new JSONArray(jsonResponse);
        log.info("soundsJSONArray.length(): " + soundsJSONArray.length());
        assertFalse(soundsJSONArray.isEmpty());
        
        JSONObject soundJsonObject = soundsJSONArray.getJSONObject(0);
        assertNotNull(soundJsonObject.getLong("id"));
        assertNotNull(soundJsonObject.getString("valueIpa"));
    }
}
