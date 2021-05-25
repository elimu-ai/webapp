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

public class WordContributionRestControllerTest {

    private Logger logger = LogManager.getLogger();

    @Test
    public void testGetWordDataForCrowdSourcing() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() + "/crowdsource/word-contributions/word-data");
        logger.info("word-dataResponse: " + jsonResponse);

        JSONArray allophoneJSONArray = new JSONArray(jsonResponse);
        logger.info("wordsJSONArray.length(): " + allophoneJSONArray.length());
        assertThat(allophoneJSONArray.length() > 0, is(true));

        JSONObject allophoneJsonObject = allophoneJSONArray.getJSONObject(0);
        assertThat(allophoneJsonObject.getLong("id"), not(nullValue()));
        assertThat(allophoneJsonObject.getString("valueIpa"), not(nullValue()));
    }

}
