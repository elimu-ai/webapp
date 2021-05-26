package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.*;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.gson.content.AllophoneGson;
import ai.elimu.model.v2.gson.content.LetterToAllophoneMappingGson;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
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
import java.util.Calendar;
import java.util.List;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 * <p>
 * This controller is responsible for handling the creation of words contributed through the crowdsource App
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
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;

    /**
     * Handles the creation of a new word & the corresponding word contribution event.
     *
     * @param request
     * @param response
     * @param requestBody JSON should contain fields required for the creation of a WordGson object and
     *                   the fields required for the creation of WordContributionEventGson object
     * @return
     */
    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public String handleWordContributionRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String requestBody) {

        logger.info("handleWordContributionRequest");

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
        WordGson wordGson = new Gson().fromJson(requestBody, WordGson.class);
        logger.info("wordGson: " + wordGson);

        // Check if the word is already existing.
        Word existingWord = wordDao.readByText(wordGson.getText());
        if (existingWord != null) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "NonUnique");
            response.setStatus(HttpStatus.CONFLICT.value());

            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }

        try {
            // Convert the JSON String to WordGson Object.
            Word word = new Word();
            word.setWordType(wordGson.getWordType());
            word.setText(wordGson.getText());
            List<LetterToAllophoneMappingGson> letterToAllophoneMappingsGsons = wordGson.getLetterToAllophoneMappings();
            List<LetterToAllophoneMapping> letterToAllophoneMappings = new ArrayList<>();
            for (LetterToAllophoneMappingGson letterToAllophoneMappingGson : letterToAllophoneMappingsGsons) {
                LetterToAllophoneMapping letterToAllophoneMapping = letterToAllophoneMappingDao.read(letterToAllophoneMappingGson.getId());
                letterToAllophoneMappings.add(letterToAllophoneMapping);
            }
            word.setLetterToAllophoneMappings(letterToAllophoneMappings);
            wordDao.create(word);

            // Convert Request Body (String) to JSON Object.
            JSONObject requestJson = new JSONObject(requestBody);

            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor(contributor);
            wordContributionEvent.setTime(Calendar.getInstance());
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
            wordContributionEvent.setComment(requestJson.getString("contributionComment"));
            wordContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(requestJson.getString("timeStart")));
            wordContributionEventDao.create(wordContributionEvent);

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


