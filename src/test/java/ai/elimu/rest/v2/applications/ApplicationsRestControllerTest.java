package ai.elimu.rest.v2.applications;

import ai.elimu.util.JsonLoader;
import lombok.extern.slf4j.Slf4j;
import selenium.util.DomainHelper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class ApplicationsRestControllerTest {
    
    @Test
    public void testHandleGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/applications");
        log.info("jsonResponse: " + jsonResponse);
        
        JSONArray applicationsJSONArray = new JSONArray(jsonResponse);
        log.info("applicationsJSONArray.length(): " + applicationsJSONArray.length());
        assertFalse(applicationsJSONArray.isEmpty());
        
        JSONObject applicationJsonObject = applicationsJSONArray.getJSONObject(0);
        assertNotNull(applicationJsonObject.getString("packageName"));
    }
}
