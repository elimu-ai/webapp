package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.*;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.v2.gson.crowdsource.NumberContributionEventGson;
import ai.elimu.model.v2.gson.crowdsource.NumberPeerReviewEventGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.content.number.NumberPeerReviewsController;
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
 * This controller has similar functionality as the {@link NumberPeerReviewsController}.
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/number-peer-reviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NumberPeerReviewsRestController {

    private Logger logger = LogManager.getLogger();

    @Autowired
    private NumberContributionEventDao numberContributionEventDao;

    @Autowired
    private ContributorDao contributorDao;

    @Autowired
    private NumberPeerReviewEventDao numberPeerReviewEventDao;

    @Autowired
    private NumberDao numberDao;

    /**
     * Get {@link NumberContributionEvent}s pending a {@link NumberPeerReviewEvent} for the current {@link Contributor}.
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

        // Get the most recent NumberContributionEvent for each Number, including those made by the current Contributor
        List<NumberContributionEvent> mostRecentNumberContributionEvents = numberContributionEventDao.readMostRecentPerNumber();
        logger.info("mostRecentNumberContributionEvents.size(): " + mostRecentNumberContributionEvents.size());

        // For each NumberContributionEvent, check if the Contributor has already performed a peer-review.
        // If not, add it to the list of pending peer reviews.
        JSONArray numberContributionEventsPendingPeerReviewJsonArray = new JSONArray();
        for (NumberContributionEvent mostRecentNumberContributionEvent : mostRecentNumberContributionEvents) {
            // Ignore NumberContributionEvents made by the current Contributor
            if (mostRecentNumberContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }

            // Check if the current Contributor has already peer-reviewed this Number contribution
            List<NumberPeerReviewEvent> numberPeerReviewEvents = numberPeerReviewEventDao.
                    readAll(mostRecentNumberContributionEvent, contributor);
            if (numberPeerReviewEvents.isEmpty()) {
                NumberContributionEventGson mostRecentNumberContributionEventGson = JpaToGsonConverter.
                        getNumberContributionEventGson(mostRecentNumberContributionEvent);

                String json = new Gson().toJson(mostRecentNumberContributionEventGson);
                numberContributionEventsPendingPeerReviewJsonArray.put(new JSONObject(json));
            }
        }
        logger.info("numberContributionEventsPendingPeerReviewJsonArray.size(): " +
                numberContributionEventsPendingPeerReviewJsonArray.length());

        String jsonResponse = numberContributionEventsPendingPeerReviewJsonArray.toString();
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
            NumberPeerReviewEventGson numberPeerReviewEventGson = new Gson().fromJson(requestBody, NumberPeerReviewEventGson.class);
            NumberContributionEvent numberContributionEvent = numberContributionEventDao.read(numberPeerReviewEventGson
                    .getNumberContributionEvent().getId());

            NumberPeerReviewEvent numberPeerReviewEvent = new NumberPeerReviewEvent();
            numberPeerReviewEvent.setContributor(contributor);
            numberPeerReviewEvent.setNumberContributionEvent(numberContributionEvent);
            numberPeerReviewEvent.setApproved(numberPeerReviewEventGson.isApproved());
            numberPeerReviewEvent.setComment(numberPeerReviewEventGson.getComment());
            numberPeerReviewEvent.setTime(Calendar.getInstance());
            numberPeerReviewEvent.setPlatform(Platform.CROWDSOURCE_APP);
            numberPeerReviewEventDao.create(numberPeerReviewEvent);

            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/number/edit/" + numberContributionEvent.getNumber().getId();
            DiscordHelper.sendChannelMessage(
                    "Number peer-reviewed: " + contentUrl, 
                    "\"" + numberContributionEvent.getNumber().getValue() + "\"",
                    "Comment: \"" + numberPeerReviewEvent.getComment() + "\"",
                    numberPeerReviewEvent.isApproved(),
                    null
            );

            // Update the number's peer review status
            int approvedCount = 0;
            int notApprovedCount = 0;
            for (NumberPeerReviewEvent peerReviewEvent : numberPeerReviewEventDao.readAll(numberContributionEvent)) {
                if (peerReviewEvent.isApproved()) {
                    approvedCount++;
                } else {
                    notApprovedCount++;
                }
            }
            logger.info("approvedCount: " + approvedCount);
            logger.info("notApprovedCount: " + notApprovedCount);
            Number number = numberContributionEvent.getNumber();
            if (approvedCount >= notApprovedCount) {
                number.setPeerReviewStatus(PeerReviewStatus.APPROVED);
            } else {
                number.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
            }
            numberDao.update(number);

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
