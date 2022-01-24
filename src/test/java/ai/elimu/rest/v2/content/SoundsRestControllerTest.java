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
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/sounds");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray soundsJSONArray = new JSONArray(jsonResponse);
        logger.info("soundsJSONArray.length(): " + soundsJSONArray.length());
        assertThat(soundsJSONArray.length() > 0, is(true));
        
        JSONObject soundJsonObject = soundsJSONArray.getJSONObject(0);
        assertThat(soundJsonObject.getLong("id"), not(nullValue()));
        assertThat(soundJsonObject.getString("valueIpa"), not(nullValue()));
    }
}
