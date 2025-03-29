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
public class LettersRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/letters");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray lettersJSONArray = new JSONArray(jsonResponse);
        log.info("lettersJSONArray.length(): " + lettersJSONArray.length());
        assertFalse(lettersJSONArray.isEmpty());
        
        JSONObject letterJsonObject = lettersJSONArray.getJSONObject(0);
        assertNotNull(letterJsonObject.getLong("id"));
        assertNotNull(letterJsonObject.getString("text"));
    }
}
