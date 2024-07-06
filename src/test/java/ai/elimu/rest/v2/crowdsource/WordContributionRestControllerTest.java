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
    public void testGetLetterSounds() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/word-contributions/letter-sound-correspondences");

        logger.info("jsonResponse: " + jsonResponse);

        JSONArray letterSoundsJSONArray = new JSONArray(jsonResponse);
        logger.info("letterSoundsJSONArray.length(): " + letterSoundsJSONArray.length());
        assertThat(letterSoundsJSONArray.length() > 0, is(true));

        JSONObject letterSoundJsonObject = letterSoundsJSONArray.getJSONObject(0);
        assertThat(letterSoundJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterSoundJsonObject.has("letters"), is(true));
        assertThat(letterSoundJsonObject.has("sounds"), is(true));
    }

    // TODO: test POST requests.
}
