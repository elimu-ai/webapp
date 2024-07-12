package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import selenium.DomainHelper;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordPeerReviewsRestControllerTest {

    private Logger logger = LogManager.getLogger();

    @Test
    public void testGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/word-peer-reviews");

        logger.info("jsonResponse: " + jsonResponse);

        JSONObject errorResponseJSONObject = new JSONObject(jsonResponse);
        assertEquals("error", errorResponseJSONObject.get("result"));
        assertEquals("Missing providerIdGoogle", errorResponseJSONObject.get("errorMessage"));
    }
}
