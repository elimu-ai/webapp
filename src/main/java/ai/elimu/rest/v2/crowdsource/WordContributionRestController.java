package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.*;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.gson.content.AllophoneGson;
import ai.elimu.model.v2.gson.content.LetterToAllophoneMappingGson;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.model.v2.gson.crowdsource.AudioContributionEventGson;
import ai.elimu.model.v2.gson.crowdsource.AudioPeerReviewEventGson;
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
 *
 * This controller is responsible for handling the creation and fetching of words of a particular contributor.
 */

//TODO : The AlloPhones need to be fetched before this can be used by the app

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
    private AllophoneDao allophoneDao;

    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;

    @RequestMapping(value = "/word", method = RequestMethod.POST)
    public String handleWordContribution (
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String requestBody) {

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

        //TODO convert the current wordGson to a WORD POJO
        logger.info("requestBody: " + requestBody);
        WordGson wordGson = new Gson().fromJson(requestBody, WordGson.class);
        logger.info("wordGson: " + wordGson);

        Word existingWord = wordDao.readByText(wordGson.getText());
        if (existingWord != null) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "NonUnique");
            String jsonResponse = jsonObject.toString();
            return jsonResponse;
        }

        // TODO validate word and return error response


        Word newWord = new Word();
        newWord.setWordType(wordGson.getWordType());
        newWord.setText(wordGson.getText());
        List<LetterToAllophoneMappingGson> wordGsonLetterToAllophoneMappingsGson = wordGson.getLetterToAllophoneMappings();
        List<LetterToAllophoneMapping> letterToAllophoneMappings = new ArrayList<>();
        for(LetterToAllophoneMappingGson letterToAllophoneMappingGson : wordGsonLetterToAllophoneMappingsGson){
            LetterToAllophoneMapping letterToAllophoneMapping = letterToAllophoneMappingDao.read(letterToAllophoneMappingGson.getId());
            letterToAllophoneMappings.add(letterToAllophoneMapping);
        }
        newWord.setLetterToAllophoneMappings(letterToAllophoneMappings);

        wordDao.create(newWord);


        WordContributionEvent wordContributionEvent = new WordContributionEvent();
        wordContributionEvent.setContributor(contributor);
        wordContributionEvent.setTime(Calendar.getInstance());
        wordContributionEvent.setWord(newWord);
        wordContributionEvent.setRevisionNumber(newWord.getRevisionNumber());
//        wordContributionEvent.setComment(request.getParameter("contributionComment"));
        wordContributionEvent.setTimeSpentMs(System.currentTimeMillis());
        wordContributionEventDao.create(wordContributionEvent);

//        Word word = wordGson.ge
        return "success";
    }

    /**
     * This method will return all the required information to create a word. eg: Allophones etc.
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public String getAllophonesForCrowSourcing(
            HttpServletRequest request,
            HttpServletResponse response
    ) {

        JSONArray allophonesJsonArray = new JSONArray();
        for (Allophone allophone : allophoneDao.readAllOrdered()) {
            AllophoneGson allophoneGson = JpaToGsonConverter.getAllophoneGson(allophone);
            String json = new Gson().toJson(allophoneGson);
            allophonesJsonArray.put(new JSONObject(json));
        }

        String jsonResponse = allophonesJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }

}


