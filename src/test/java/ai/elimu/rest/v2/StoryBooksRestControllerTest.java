package ai.elimu.rest.v2;

import ai.elimu.util.JsonLoader;
import org.apache.log4j.Logger;
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
        JSONObject jsonObject = new JSONObject(jsonResponse);
        
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("success"));
        
        assertThat(jsonObject.has("storyBooks"), is(true));
        assertThat(jsonObject.getJSONArray("storyBooks").length() > 0, is(true));
        
        JSONObject storyBookJsonObject = jsonObject.getJSONArray("storyBooks").getJSONObject(0);
        assertThat(storyBookJsonObject.getString("title"), not(nullValue()));
        
        JSONArray chaptersJsonArray = storyBookJsonObject.getJSONArray("storyBookChapters");
        assertThat(chaptersJsonArray.length() > 0, is(true));
    }
}
