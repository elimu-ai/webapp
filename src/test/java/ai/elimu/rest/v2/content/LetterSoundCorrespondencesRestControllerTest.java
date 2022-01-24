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

public class LetterSoundCorrespondencesRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/letter-sound-correspondences");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray letterSoundCorrespondencesJSONArray = new JSONArray(jsonResponse);
        logger.info("letterSoundCorrespondencesJSONArray.length(): " + letterSoundCorrespondencesJSONArray.length());
        assertThat(letterSoundCorrespondencesJSONArray.length() > 0, is(true));
        
        JSONObject letterSoundCorrespondenceJsonObject = letterSoundCorrespondencesJSONArray.getJSONObject(0);
        assertThat(letterSoundCorrespondenceJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterSoundCorrespondenceJsonObject.has("letters"), is(true));
        assertThat(letterSoundCorrespondenceJsonObject.has("sounds"), is(true));
    }
}
