package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.v2.gson.content.WordGson;
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

@RestController
@RequestMapping(value = "/rest/v2/crowdsource/audio-contributions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AudioContributionsRestController {
 
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    /**
     * Get {@link Word}s pending {@link Audio} recording for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info("handleGetRequest");
        
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
        
        // Get the IDs of Words that have already been recorded by the Contributor
        List<AudioContributionEvent> audioContributionEvents = audioContributionEventDao.readAll(contributor);
        logger.info("audioContributionEvents.size(): " + audioContributionEvents.size());
        HashMap<Long, Void> idsOfRecordedWordsHashMap = new HashMap<>();
        for (AudioContributionEvent audioContributionEvent : audioContributionEvents) {
            Audio audio = audioContributionEvent.getAudio();
            Word word = audio.getWord();
            if (word != null) {
                idsOfRecordedWordsHashMap.put(word.getId(), null);
            }
        }
        logger.info("idsOfRecordedWordsHashMap.size(): " + idsOfRecordedWordsHashMap.size());
        
        // For each Word, check if the Contributor has already contributed a 
        // corresponding Audio recording. If not, add it to the list of pending recordings.
        List<Word> wordsPendingAudioRecording = new ArrayList<>();
        for (Word word : wordDao.readAllOrderedByUsage()) {
            if (!idsOfRecordedWordsHashMap.containsKey(word.getId())) {
                wordsPendingAudioRecording.add(word);
            }
        }
        logger.info("wordsPendingAudioRecording.size(): " + wordsPendingAudioRecording.size());
        
        // Convert to JSON
        JSONArray wordsJsonArray = new JSONArray();
        for (Word word : wordsPendingAudioRecording) {
            WordGson wordGson = JpaToGsonConverter.getWordGson(word);
            String json = new Gson().toJson(wordGson);
            wordsJsonArray.put(new JSONObject(json));
        }
        
        String jsonResponse = wordsJsonArray.toString();
        logger.info("jsonResponse: " + jsonResponse);
        return jsonResponse;
    }
}
