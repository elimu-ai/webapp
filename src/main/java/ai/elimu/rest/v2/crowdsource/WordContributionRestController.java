package ai.elimu.rest.v2.crowdsource;
import ai.elimu.dao.*;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.v2.gson.content.LetterSoundCorrespondenceGson;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.model.v2.gson.crowdsource.WordContributionEventGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 * <p>
 * 
 * This controller is responsible for handling the creation of words contributed through the Crowdsource app
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/word-contributions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WordContributionRestController {

    private final Logger logger = LogManager.getLogger();

    @Autowired
    private WordDao wordDao;

    @Autowired
    private WordContributionEventDao wordContributionEventDao;

    @Autowired
    private ContributorDao contributorDao;

    @Autowired
    private LetterSoundDao letterSoundDao;
    
    /**
     * Returns a list of {@link LetterSoundCorrespondence}s that will be used to construct a {@link Word}.
     */
    @RequestMapping(value = "/letter-sound-correspondences", method = RequestMethod.GET)
    public String getLetterSoundCorrespondences(HttpServletRequest request, HttpServletResponse response) {
        logger.info("getLetterSoundCorrespondences");

        JSONArray letterSoundCorrespondencesJsonArray = new JSONArray();
        for (LetterSoundCorrespondence letterSoundCorrespondence : letterSoundDao.readAllOrderedByUsage()) {
            LetterSoundCorrespondenceGson letterSoundCorrespondenceGson =
                    JpaToGsonConverter.getLetterSoundCorrespondenceGson(letterSoundCorrespondence);
            String json = new Gson().toJson(letterSoundCorrespondenceGson);
            letterSoundCorrespondencesJsonArray.put(new JSONObject(json));
        }

        String jsonResponse = letterSoundCorrespondencesJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }

    /**
     * Handles the creation of a new {@link Word} & the corresponding {@link WordContributionEvent}.
     *
     * @param requestBody JSON should contain fields required for the creation of a {@link WordContributionEventGson} 
     * object.
     */
    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public String postWordContribution(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String requestBody
    ) {
        logger.info("postWordContribution");

        // Validate the Contributor.
        JSONObject jsonObject = new JSONObject();

        String providerIdGoogle = request.getHeader("providerIdGoogle");
        logger.info("providerIdGoogle: " + providerIdGoogle);
        if (StringUtils.isBlank(providerIdGoogle)) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "Missing providerIdGoogle");
            response.setStatus(HttpStatus.BAD_REQUEST.value());

            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }

        // Lookup the Contributor by ID
        Contributor contributor = contributorDao.readByProviderIdGoogle(providerIdGoogle);
        logger.info("contributor: " + contributor);
        if (contributor == null) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "The Contributor was not found.");
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }

        logger.info("requestBody: " + requestBody);

        // Convert the request body to a WordContributionEventGson
        WordContributionEventGson wordContributionEventGson =
                new Gson().fromJson(requestBody, WordContributionEventGson.class);
        logger.info("wordContributionEventGson: " + wordContributionEventGson);

        // Extract the WordGson from the WordContributionEventGson
        WordGson wordGson = wordContributionEventGson.getWord();
        logger.info("wordGson: " + wordGson);

        // Check if the word is already existing.
        Word existingWord = wordDao.readByTextAndType(wordGson.getText().toLowerCase(), wordGson.getWordType());
        if (existingWord != null) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "NonUnique");
            response.setStatus(HttpStatus.CONFLICT.value());

            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }

        try {
            // Convert the WordGson to Word POJO.
            Word word = new Word();
            word.setWordType(wordGson.getWordType());
            word.setText(wordGson.getText().toLowerCase());
            List<LetterSoundCorrespondenceGson> letterSoundCorrespondencesGsons = wordGson.getLetterSoundCorrespondences();
            List<LetterSoundCorrespondence> letterSoundCorrespondences = new ArrayList<>();
            for (LetterSoundCorrespondenceGson letterSoundCorrespondenceGson : letterSoundCorrespondencesGsons) {
                LetterSoundCorrespondence letterSoundCorrespondence =
                        letterSoundDao.read(letterSoundCorrespondenceGson.getId());
                letterSoundCorrespondences.add(letterSoundCorrespondence);
            }
            word.setLetterSoundCorrespondences(letterSoundCorrespondences);
            wordDao.create(word);

            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor(contributor);
            wordContributionEvent.setTime(wordContributionEventGson.getTime());
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
            wordContributionEvent.setComment(StringUtils.abbreviate(wordContributionEventGson.getComment(), 1000));
            wordContributionEvent.setTimeSpentMs(System.currentTimeMillis() -
                    wordContributionEvent.getTime().getTimeInMillis());
            // TODO: wordContributionEvent.setTimeSpentMs(wordContributionEventGson.getTimeSpentMs());
            //  refer to: https://github.com/elimu-ai/webapp/pull/1289#discussion_r642024541
            wordContributionEvent.setPlatform(Platform.CROWDSOURCE_APP);
            wordContributionEventDao.create(wordContributionEvent);
            
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + word.getId();
            DiscordHelper.sendChannelMessage(
                    "Word created: " + contentUrl,
                    "\"" + wordContributionEvent.getWord().getText() + "\"",
                    "Comment: \"" + wordContributionEvent.getComment() + "\"",
                    null,
                    null
            );

            response.setStatus(HttpStatus.CREATED.value());
        } catch (Exception ex) {
            logger.error(ex);

            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
