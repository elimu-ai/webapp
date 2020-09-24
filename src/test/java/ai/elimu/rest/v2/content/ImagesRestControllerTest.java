package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import selenium.DomainHelper;

public class ImagesRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/images");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray imagesJSONArray = new JSONArray(jsonResponse);
        logger.info("imagesJSONArray.length(): " + imagesJSONArray.length());
        assertThat(imagesJSONArray.length() > 0, is(true));
        
        JSONObject imageJsonObject = imagesJSONArray.getJSONObject(0);
        assertThat(imageJsonObject.getString("title"), not(nullValue()));
    }
}
