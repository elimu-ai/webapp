package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import selenium.DomainHelper;

public class WordsRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/words");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray wordsJSONArray = new JSONArray(jsonResponse);
        logger.info("wordsJSONArray.length(): " + wordsJSONArray.length());
        assertThat(wordsJSONArray.length() > 0, is(true));
        
        JSONObject wordJsonObject = wordsJSONArray.getJSONObject(0);
        assertThat(wordJsonObject.getLong("id"), not(nullValue()));
        assertThat(wordJsonObject.getString("text"), not(nullValue()));
    }
}
