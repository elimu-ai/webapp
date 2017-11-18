package rest.v1.project;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;
import ai.elimu.util.JsonLoader;
import selenium.DomainHelper;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LicenseRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Test
    public void testRead() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/project/licenses" +
                "?licenseEmail=info%2Btest@elimu.ai" + 
                "&licenseNumber=b3fb-5e67-98f8-984a");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("success"));
        assertThat(jsonObject.has("appCollectionId"), is(true));
        assertThat(jsonObject.get("appCollectionId"), is(2));
    }
    
    @Test
    public void testRead_invalid() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/project/licenses" +
                "?licenseEmail=info@elimu.ai" + 
                "&licenseNumber=bddf-d8f4-2adf-a365");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("error"));
        assertThat(jsonObject.has("description"), is(true));
        assertThat(jsonObject.get("description"), is("Invalid license"));
    }
}
