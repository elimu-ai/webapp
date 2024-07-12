package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import selenium.DomainHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the {@link AudioPeerReviewsRestController}.
 */
public class AudioPeerReviewsRestControllerTest {
    
    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testGetWordRecordingsPendingPeerReview_error() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/crowdsource/audio-peer-reviews/words");
        logger.info("jsonResponse: " + jsonResponse);
        
        JSONObject errorResponseJSONObject = new JSONObject(jsonResponse);
        assertEquals("error", errorResponseJSONObject.get("result"));
        assertEquals("Missing providerIdGoogle", errorResponseJSONObject.get("errorMessage"));
    }
    
    // TODO: test uploadAudioPeerReview
}
