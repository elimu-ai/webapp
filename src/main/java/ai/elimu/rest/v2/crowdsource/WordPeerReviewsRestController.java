package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.*;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.v2.gson.crowdsource.WordContributionEventGson;
import ai.elimu.model.v2.gson.crowdsource.WordPeerReviewEventGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.content.word.WordPeerReviewsController;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
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
import java.util.Calendar;
import java.util.List;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 * <p>
 * <p>
 * This controller has similar functionality as the {@link WordPeerReviewsController}.
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/word-peer-reviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WordPeerReviewsRestController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private WordContributionEventDao wordContributionEventDao;

    @Autowired
    private ContributorDao contributorDao;

    @Autowired
    private WordPeerReviewEventDao wordPeerReviewEventDao;

    @Autowired
    private WordDao wordDao;

    /**
     * Get {@link WordContributionEvent}s pending a {@link WordPeerReviewEvent} for the current {@link Contributor}.
     * <p>
     * Note: Currently The list of Emojis are not delivered in this method compared to the handleGetRequest method in
     * {@link WordPeerReviewsController}
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpServletRequest request,
                                   HttpServletResponse response) {
        logger.info("handleGetRequest");


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

        // Get the most recent WordContributionEvent for each Word, including those made by the current Contributor
        List<WordContributionEvent> mostRecentWordContributionEvents = wordContributionEventDao.readMostRecentPerWord();
        logger.info("mostRecentWordContributionEvents.size(): " + mostRecentWordContributionEvents.size());

        // For each WordContributionEvent, check if the Contributor has already performed a peer-review.
        // If not, add it to the list of pending peer reviews.
        JSONArray wordContributionEventsPendingPeerReviewJsonArray = new JSONArray();
        for (WordContributionEvent mostRecentWordContributionEvent : mostRecentWordContributionEvents) {
            // Ignore WordContributionEvents made by the current Contributor
            if (mostRecentWordContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }

            // Check if the current Contributor has already peer-reviewed this Word contribution
            List<WordPeerReviewEvent> wordPeerReviewEvents = wordPeerReviewEventDao.
                    readAll(mostRecentWordContributionEvent, contributor);
            if (wordPeerReviewEvents.isEmpty()) {
                WordContributionEventGson mostRecentWordContributionEventGson = JpaToGsonConverter.
                        getWordContributionEventGson(mostRecentWordContributionEvent);

                String json = new Gson().toJson(mostRecentWordContributionEventGson);
                wordContributionEventsPendingPeerReviewJsonArray.put(new JSONObject(json));
            }
        }
        logger.info("wordContributionEventsPendingPeerReviewJsonArray.size(): " +
                wordContributionEventsPendingPeerReviewJsonArray.length());

        String jsonResponse = wordContributionEventsPendingPeerReviewJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String handlePostRequest(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestBody String requestBody) {
        logger.info("handlePostRequest");

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

        try {
            // Convert from Gson (POJO) to JPA/Hibernate
            logger.info("requestBody: " + requestBody);
            WordPeerReviewEventGson wordPeerReviewEventGson = new Gson().fromJson(requestBody, WordPeerReviewEventGson.class);
            WordContributionEvent wordContributionEvent = wordContributionEventDao.read(wordPeerReviewEventGson
                    .getWordContributionEvent().getId());

            WordPeerReviewEvent wordPeerReviewEvent = new WordPeerReviewEvent();
            wordPeerReviewEvent.setContributor(contributor);
            wordPeerReviewEvent.setWordContributionEvent(wordContributionEvent);
            wordPeerReviewEvent.setApproved(wordPeerReviewEventGson.isApproved());
            wordPeerReviewEvent.setComment(wordPeerReviewEventGson.getComment());
            wordPeerReviewEvent.setTime(Calendar.getInstance());
            wordPeerReviewEventDao.create(wordPeerReviewEvent);

            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + wordContributionEvent.getWord().getId();
            DiscordHelper.postChatMessage(
                    "Word peer-reviewed: " + contentUrl, 
                    "\"" + wordContributionEvent.getWord().getText() + "\"",
                    "Comment: \"" + wordPeerReviewEvent.getComment() + "\"",
                    wordPeerReviewEvent.isApproved()
            );

            // Update the word's peer review status
            int approvedCount = 0;
            int notApprovedCount = 0;
            for (WordPeerReviewEvent peerReviewEvent : wordPeerReviewEventDao.readAll(wordContributionEvent)) {
                if (peerReviewEvent.isApproved()) {
                    approvedCount++;
                } else {
                    notApprovedCount++;
                }
            }
            logger.info("approvedCount: " + approvedCount);
            logger.info("notApprovedCount: " + notApprovedCount);
            Word word = wordContributionEvent.getWord();
            if (approvedCount >= notApprovedCount) {
                word.setPeerReviewStatus(PeerReviewStatus.APPROVED);
            } else {
                word.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
            }
            wordDao.update(word);

        } catch (Exception ex) {
            logger.error(ex);

            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", ex.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }


        String jsonResponse = jsonObject.toString();
        logger.info("jsonResponse: " + jsonResponse);
        response.setStatus(HttpStatus.CREATED.value());
        return jsonResponse;
    }
}
