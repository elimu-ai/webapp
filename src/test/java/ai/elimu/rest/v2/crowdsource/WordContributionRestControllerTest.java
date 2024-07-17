package ai.elimu.rest.v2.crowdsource;

import ai.elimu.util.JsonLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import selenium.DomainHelper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordContributionRestControllerTest {

    private Logger logger = LogManager.getLogger();
    
    @Test
    public void testGetLetterSounds() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/word-contributions/letter-sound-correspondences");

        logger.info("jsonResponse: " + jsonResponse);

        JSONArray letterSoundsJSONArray = new JSONArray(jsonResponse);
        logger.info("letterSoundsJSONArray.length(): " + letterSoundsJSONArray.length());
        assertFalse(letterSoundsJSONArray.isEmpty());

        JSONObject letterSoundJsonObject = letterSoundsJSONArray.getJSONObject(0);
        assertNotNull(letterSoundJsonObject.getLong("id"));
        assertTrue(letterSoundJsonObject.has("letters"));
        assertTrue(letterSoundJsonObject.has("sounds"));
    }

    // TODO: test POST requests.
}
