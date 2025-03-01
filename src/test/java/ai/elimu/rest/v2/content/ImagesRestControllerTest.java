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
public class ImagesRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/images");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray imagesJSONArray = new JSONArray(jsonResponse);
        log.info("imagesJSONArray.length(): " + imagesJSONArray.length());
        assertFalse(imagesJSONArray.isEmpty());
        
        JSONObject imageJsonObject = imagesJSONArray.getJSONObject(0);
        assertNotNull(imageJsonObject.getString("title"));
    }
}
