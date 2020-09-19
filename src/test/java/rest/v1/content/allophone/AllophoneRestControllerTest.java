package rest.v1.content.allophone;

import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import selenium.DomainHelper;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@Deprecated
public class AllophoneRestControllerTest {
    
    private Logger logger = LogManager.getLogger();

    @Test(expected = JSONException.class)
    public void testList_missingParameters() {
    	String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/content/allophone/list");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
    }
    
    @Test
    public void testList_success() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/content/allophone/list" +
                "?deviceId=abc123");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("success"));
        assertThat(jsonObject.has("allophones"), is(true));
    }
}
