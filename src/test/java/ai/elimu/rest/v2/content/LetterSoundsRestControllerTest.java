package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import lombok.extern.slf4j.Slf4j;
import selenium.util.DomainHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class LetterSoundsRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/letter-sounds");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray letterSoundsJSONArray = new JSONArray(jsonResponse);
        log.info("letterSoundsJSONArray.length(): " + letterSoundsJSONArray.length());
        assertFalse(letterSoundsJSONArray.isEmpty());
        
        JSONObject letterSoundJsonObject = letterSoundsJSONArray.getJSONObject(0);
        assertNotNull(letterSoundJsonObject.getLong("id"));
        assertTrue(letterSoundJsonObject.has("letters"));
        assertTrue(letterSoundJsonObject.has("sounds"));
    }
}
