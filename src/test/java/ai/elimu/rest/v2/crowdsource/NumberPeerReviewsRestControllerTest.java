package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;
import selenium.DomainHelper;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NumberPeerReviewsRestControllerTest {

    private Logger logger = LogManager.getLogger();

    @Test
    public void testGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/number-peer-reviews");

        logger.info("jsonResponse: " + jsonResponse);

        JSONObject errorResponseJSONObject = new JSONObject(jsonResponse);
        assertThat(errorResponseJSONObject.get("result"), is("error"));
        assertThat(errorResponseJSONObject.get("errorMessage"), is("Missing providerIdGoogle"));
    }
}
