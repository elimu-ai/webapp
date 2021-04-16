package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import selenium.DomainHelper;

/**
 * Tests the {@link AudioContributionsRestController}.
 */
public class AudioContributionsRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testHandleGetWordsRequest_error() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/crowdsource/audio-contributions/words");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONObject errorResponseJSONObject = new JSONObject(jsonResponse);
        assertThat(errorResponseJSONObject.get("result"), is("error"));
        assertThat(errorResponseJSONObject.get("errorMessage"), is("Missing providerIdGoogle"));
    }
    
    // TODO: test handleUploadWordRecordingRequest
}
