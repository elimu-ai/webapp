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

public class LettersRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/letters");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray lettersJSONArray = new JSONArray(jsonResponse);
        logger.info("lettersJSONArray.length(): " + lettersJSONArray.length());
        assertThat(lettersJSONArray.length() > 0, is(true));
        
        JSONObject letterJsonObject = lettersJSONArray.getJSONObject(0);
        assertThat(letterJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterJsonObject.getString("text"), not(nullValue()));
    }
}
