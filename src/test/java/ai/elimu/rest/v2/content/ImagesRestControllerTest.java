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

public class ImagesRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/images");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray imagesJSONArray = new JSONArray(jsonResponse);
        logger.info("imagesJSONArray.length(): " + imagesJSONArray.length());
        assertFalse(imagesJSONArray.isEmpty());
        
        JSONObject imageJsonObject = imagesJSONArray.getJSONObject(0);
        assertNotNull(imageJsonObject.getString("title"));
    }
}
