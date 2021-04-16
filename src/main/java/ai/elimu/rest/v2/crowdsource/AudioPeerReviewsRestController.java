package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
import ai.elimu.model.v2.gson.crowdsource.AudioContributionEventGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/audio-peer-reviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AudioPeerReviewsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    @Autowired
    private AudioPeerReviewEventDao audioPeerReviewEventDao;
    
    /**
     * Get {@link AudioContributionEvent}s pending a {@link AudioPeerReviewEvent} for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info("handleGetRequest");
        
        JSONObject jsonObject = new JSONObject();
        
        // Lookup the Contributor by ID
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
        
        List<AudioContributionEvent> allAudioContributionEvents = audioContributionEventDao.readAll();
        logger.info("allAudioContributionEvents.size(): " + allAudioContributionEvents.size());
        
        // Get the most recent AudioContributionEvent for each Audio
        List<AudioContributionEvent> mostRecentAudioContributionEvents = new ArrayList<>();
        HashMap<Long, Void> idsOfAudiosWithContributionEventHashMap = new HashMap<>();
        for (AudioContributionEvent audioContributionEvent : allAudioContributionEvents) {
            // Ignore AudioContributionEvent if one has already been added for this Audio
            if (idsOfAudiosWithContributionEventHashMap.containsKey(audioContributionEvent.getAudio().getId())) {
                continue;
            }
            
            // Keep track of the Audio ID
            idsOfAudiosWithContributionEventHashMap.put(audioContributionEvent.getAudio().getId(), null);
            
            // Ignore AudioContributionEvents made by the current Contributor
            if (audioContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }
            
            mostRecentAudioContributionEvents.add(audioContributionEvent);
        }
        logger.info("mostRecentAudioContributionEvents.size(): " + mostRecentAudioContributionEvents.size());
        
        // For each AudioContributionEvent, check if the Contributor has already performed a peer review.
        // If not, add it to the list of pending peer reviews.
        List<AudioContributionEvent> audioContributionEventsPendingPeerReview = new ArrayList<>();
        for (AudioContributionEvent mostRecentAudioContributionEvent : mostRecentAudioContributionEvents) {
            AudioPeerReviewEvent audioPeerReviewEvent = audioPeerReviewEventDao.read(mostRecentAudioContributionEvent, contributor);
            if (audioPeerReviewEvent == null) {
                audioContributionEventsPendingPeerReview.add(mostRecentAudioContributionEvent);
            }
        }
        logger.info("audioContributionEventsPendingPeerReview.size(): " + audioContributionEventsPendingPeerReview.size());
        
        // Convert to JSON
        JSONArray audioContributionEventsJsonArray = new JSONArray();
        for (AudioContributionEvent audioContributionEvent : audioContributionEventsPendingPeerReview) {
            AudioContributionEventGson audioContributionEventGson = JpaToGsonConverter.getAudioContributionEventGson(audioContributionEvent);
            String json = new Gson().toJson(audioContributionEventGson);
            audioContributionEventsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = audioContributionEventsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
