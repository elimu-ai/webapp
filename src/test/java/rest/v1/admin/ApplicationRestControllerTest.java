package rest.v1.admin;

import org.apache.log4j.Logger;
import static org.hamcrest.CoreMatchers.is;
import org.json.JSONException;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.rest.v1.VersionRestController;
import org.literacyapp.util.JsonLoader;
import selenium.DomainHelper;

public class ApplicationRestControllerTest {
    
    private Logger logger = Logger.getLogger(getClass());

    @Test(expected = JSONException.class)
    public void testList_missingParameters() {
    	String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/admin/application/list");
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
    }
    
    @Test
    public void testList_success() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV1() + "/admin/application/list" +
                "?deviceId=abcdef123456" + 
                "&locale=" + Locale.EN + 
                "&deviceModel=Google+Pixel+C" + 
                "&osVersion=" + VersionRestController.MINIMUM_OS_VERSION +  
                "&appVersionCode=" + VersionRestController.NEWEST_VERSION_APPSTORE);
        logger.info("jsonResponse: " + jsonResponse);
        JSONObject jsonObject = new JSONObject(jsonResponse);
        assertThat(jsonObject.has("result"), is(true));
        assertThat(jsonObject.get("result"), is("success"));
        assertThat(jsonObject.has("applications"), is(true));
    }
}
