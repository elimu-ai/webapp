package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import org.json.JSONArray;
import org.json.JSONObject;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import selenium.DomainHelper;

public class WordContributionRestControllerTest {

    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testGetLetterToAllophoneMappings() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/word-contributions/letter-to-allophone-mappings");

        logger.info("jsonResponse: " + jsonResponse);

        JSONArray letterToAllophoneMappingsJSONArray = new JSONArray(jsonResponse);
        logger.info("letterToAllophoneMappingsJSONArray.length(): " + letterToAllophoneMappingsJSONArray.length());
        assertThat(letterToAllophoneMappingsJSONArray.length() > 0, is(true));

        JSONObject letterToAllophoneMappingJsonObject = letterToAllophoneMappingsJSONArray.getJSONObject(0);
        assertThat(letterToAllophoneMappingJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterToAllophoneMappingJsonObject.has("letters"), is(true));
        assertThat(letterToAllophoneMappingJsonObject.has("allophones"), is(true));
    }

    // TODO: test POST requests.
}
