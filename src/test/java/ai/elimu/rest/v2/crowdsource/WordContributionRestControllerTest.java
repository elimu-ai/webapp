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
    public void testGetLetterSoundCorrespondences() {
        String jsonResponse = JsonLoader.loadJson(DomainHelper.getRestUrlV2() +
                "/crowdsource/word-contributions/letter-sound-correspondences");

        logger.info("jsonResponse: " + jsonResponse);

        JSONArray letterSoundCorrespondencesJSONArray = new JSONArray(jsonResponse);
        logger.info("letterSoundCorrespondencesJSONArray.length(): " + letterSoundCorrespondencesJSONArray.length());
        assertThat(letterSoundCorrespondencesJSONArray.length() > 0, is(true));

        JSONObject letterSoundCorrespondenceJsonObject = letterSoundCorrespondencesJSONArray.getJSONObject(0);
        assertThat(letterSoundCorrespondenceJsonObject.getLong("id"), not(nullValue()));
        assertThat(letterSoundCorrespondenceJsonObject.has("letters"), is(true));
        assertThat(letterSoundCorrespondenceJsonObject.has("sounds"), is(true));
    }

    // TODO: test POST requests.
}
