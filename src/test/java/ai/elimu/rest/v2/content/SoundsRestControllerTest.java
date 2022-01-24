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

public class SoundsRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/allophones");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray allophonesJSONArray = new JSONArray(jsonResponse);
        logger.info("allophonesJSONArray.length(): " + allophonesJSONArray.length());
        assertThat(allophonesJSONArray.length() > 0, is(true));
        
        JSONObject allophoneJsonObject = allophonesJSONArray.getJSONObject(0);
        assertThat(allophoneJsonObject.getLong("id"), not(nullValue()));
        assertThat(allophoneJsonObject.getString("valueIpa"), not(nullValue()));
    }
}
