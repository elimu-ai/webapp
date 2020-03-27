package ai.elimu.rest.v2.content;

import ai.elimu.util.JsonLoader;
import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import selenium.DomainHelper;

public class ImagesRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/content/images");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("success"));
        
        assertThat(jsonObject.has("images"), is(true));
        assertThat(jsonObject.getJSONArray("images").length() > 0, is(true));
        
        JSONObject imageJsonObject = jsonObject.getJSONArray("images").getJSONObject(0);
        assertThat(imageJsonObject.getString("title"), not(nullValue()));
    }
}
