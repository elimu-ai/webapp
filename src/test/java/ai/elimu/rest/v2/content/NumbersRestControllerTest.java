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
public class NumbersRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/numbers");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray numbersJSONArray = new JSONArray(jsonResponse);
        log.info("numbersJSONArray.length(): " + numbersJSONArray.length());
        assertFalse(numbersJSONArray.isEmpty());
        
        JSONObject numberJsonObject = numbersJSONArray.getJSONObject(0);
        assertNotNull(numberJsonObject.getLong("id"));
        assertNotNull(numberJsonObject.getInt("value"));
    }
}
