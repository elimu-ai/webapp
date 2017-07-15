package rest.v1.content.storybook;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import ai.elimu.model.enums.Locale;
import ai.elimu.util.JsonLoader;
import selenium.DomainHelper;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class StoryBookRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());

    @Test(expected = JSONException.class)
    public void testList_missingParameters() {
    	String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/content/storybook/list");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
    }
    
    @Test
    public void testList_success() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/content/storybook/list" +
                "?deviceId=abc123" + 
                "&locale=" + Locale.EN);
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("success"));
        assertThat(jsonObject.has("storyBooks"), is(true));
    }
}
