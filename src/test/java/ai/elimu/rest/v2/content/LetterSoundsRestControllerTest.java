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

public class LetterSoundsRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/letter-sounds");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray letterSoundsJSONArray = new JSONArray(jsonResponse);
        logger.info("letterSoundsJSONArray.length(): " + letterSoundsJSONArray.length());
        assertThat(letterSoundsJSONArray.length() > 0, is(true));
        
        JSONObject letterSoundJsonObject = letterSoundsJSONArray.getJSONObject(0);
        assertThat(letterSoundJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterSoundJsonObject.has("letters"), is(true));
        assertThat(letterSoundJsonObject.has("sounds"), is(true));
    }
}
