package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import selenium.DomainHelper;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class LetterToAllophoneMappingsRestControllerTest {

    private Logger logger = LogManager.getLogger();

    @Test
    public void testGetRequest() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/letter-to-allophone-mappings");

        logger.info("jsonResponse: " + jsonResponse);

        JSONArray letterToAllophoneMappingsJSONArray = new JSONArray(jsonResponse);
        logger.info("letterToAllophoneMappingsJSONArray.length(): " + letterToAllophoneMappingsJSONArray.length());
        assertThat(letterToAllophoneMappingsJSONArray.length() > 0, is(true));

        JSONObject letterToAllophoneMappingJsonObject = letterToAllophoneMappingsJSONArray.getJSONObject(0);
        assertThat(letterToAllophoneMappingJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterToAllophoneMappingJsonObject.has("letters"), is(true));
        assertThat(letterToAllophoneMappingJsonObject.has("allophones"), is(true));
    }
}
