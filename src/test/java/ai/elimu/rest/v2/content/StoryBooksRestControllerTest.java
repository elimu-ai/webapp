package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import selenium.DomainHelper;

public class StoryBooksRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/storybooks");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONArray storyBooksJSONArray = new JSONArray(jsonResponse);
        logger.info("storyBooksJSONArray.length(): " + storyBooksJSONArray.length());
        assertThat(storyBooksJSONArray.length() > 0, is(true));
        
        JSONObject storyBookJsonObject = storyBooksJSONArray.getJSONObject(0);
        assertThat(storyBookJsonObject.getString("title"), not(nullValue()));
        
        JSONArray chaptersJsonArray = storyBookJsonObject.getJSONArray("storyBookChapters");
        logger.info("chaptersJsonArray.length(): " + chaptersJsonArray.length());
        assertThat(chaptersJsonArray.length() > 0, is(true));
    }
}
