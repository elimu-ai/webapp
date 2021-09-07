package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.v2.gson.crowdsource.AudioContributionEventGson;
import ai.elimu.model.v2.gson.crowdsource.AudioPeerReviewEventGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import ai.elimu.util.SlackHelper;
import ai.elimu.web.content.peer_review.AudioPeerReviewEventCreateController;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
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
import org.springframework.web.bind.annotation.RequestBody;
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
    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public String listWordRecordingsPendingPeerReview(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info("listWordRecordingsPendingPeerReview");
        
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
    
    /**
     * Note: The logic in this method is similar to the one used at {@link AudioPeerReviewEventCreateController#handleSubmit}
     */
    @RequestMapping(value = "/words", method = RequestMethod.POST)
    public String uploadAudioPeerReview(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody String requestBody
    ) {
        logger.info("uploadAudioPeerReview");
        
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
            AudioPeerReviewEventGson audioPeerReviewEventGson = new Gson().fromJson(requestBody, AudioPeerReviewEventGson.class);
            logger.info("audioPeerReviewEventGson: " + audioPeerReviewEventGson);
            AudioContributionEventGson audioContributionEventGson = audioPeerReviewEventGson.getAudioContributionEvent();
            logger.info("audioContributionEventGson: " + audioContributionEventGson);
            Long audioContributionEventGsonId = audioContributionEventGson.getId();
            logger.info("audioContributionEventGsonId: " + audioContributionEventGsonId);
            AudioContributionEvent audioContributionEvent = audioContributionEventDao.read(audioContributionEventGsonId);
            logger.info("audioContributionEvent: " + audioContributionEvent);

            AudioPeerReviewEvent audioPeerReviewEvent = new AudioPeerReviewEvent();
            audioPeerReviewEvent.setContributor(contributor);
            audioPeerReviewEvent.setAudioContributionEvent(audioContributionEvent);
            audioPeerReviewEvent.setApproved(audioPeerReviewEventGson.isApproved());
            audioPeerReviewEvent.setComment(StringUtils.abbreviate(audioPeerReviewEventGson.getComment(), 1000));
            audioPeerReviewEvent.setTime(audioPeerReviewEventGson.getTime());
            audioPeerReviewEvent.setPlatform(Platform.CROWDSOURCE_APP);
            audioPeerReviewEventDao.create(audioPeerReviewEvent);
            
            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/audio/edit/" + audioContributionEvent.getAudio().getId();
            SlackHelper.postChatMessage("Audio peer-reviewed: " + contentUrl);

            // Update the audio's peer review status
            int approvedCount = 0;
            int notApprovedCount = 0;
            for (AudioPeerReviewEvent peerReviewEvent : audioPeerReviewEventDao.readAll(audioContributionEvent)) {
                if (peerReviewEvent.isApproved()) {
                    approvedCount++;
                } else {
                    notApprovedCount++;
                }
            }
            logger.info("approvedCount: " + approvedCount);
            logger.info("notApprovedCount: " + notApprovedCount);
            Audio audio = audioContributionEvent.getAudio();
            if (approvedCount >= notApprovedCount) {
                audio.setPeerReviewStatus(PeerReviewStatus.APPROVED);
            } else {
                audio.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
            }
            audioDao.update(audio);
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
