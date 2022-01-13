package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.v2.enums.content.AudioFormat;
import ai.elimu.model.v2.gson.content.WordGson;
import ai.elimu.rest.v2.JpaToGsonConverter;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.SlackHelper;
import ai.elimu.util.audio.AudioMetadataExtractionHelper;
import ai.elimu.util.audio.CrowdsourceHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/audio-contributions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AudioContributionsRestController {
 
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    /**
     * Get {@link Word}s pending {@link Audio} recording for the current {@link Contributor}.
     */
    @RequestMapping(value = "/words", method = RequestMethod.GET)
    public String getWordsPendingRecording(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        logger.info("getWordsPendingRecording");
        
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
    
    @RequestMapping(value = "/words", method = RequestMethod.POST)
    public String handleUploadWordRecordingRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam("file") MultipartFile multipartFile
    ) {
        logger.info("handleUploadWordRecordingRequest");
        
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
        
        String timeSpentMsAsString = request.getHeader("timeSpentMs");
        logger.info("timeSpentMsAsString: " + timeSpentMsAsString);
        if (StringUtils.isBlank(timeSpentMsAsString)) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "Missing timeSpentMs");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            
            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }
        Long timeSpentMs = Long.valueOf(timeSpentMsAsString);
        
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
        
        // Expected format: "word_5.mp3"
        String originalFilename = multipartFile.getOriginalFilename();
        logger.info("originalFilename: " + originalFilename);
        
        AudioFormat audioFormat = CrowdsourceHelper.extractAudioFormatFromFilename(originalFilename);
        logger.info("audioFormat: " + audioFormat);
        
        Long wordIdExtractedFromFilename = CrowdsourceHelper.extractWordIdFromFilename(originalFilename);
        logger.info("wordIdExtractedFromFilename: " + wordIdExtractedFromFilename);
        Word word = wordDao.read(wordIdExtractedFromFilename);
        logger.info("word: " + word);
        if (word == null) {
            jsonObject.put("result", "error");
            jsonObject.put("errorMessage", "A Word with ID " + wordIdExtractedFromFilename + " was not found.");
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
            
            String jsonResponse = jsonObject.toString();
            logger.info("jsonResponse: " + jsonResponse);
            return jsonResponse;
        }
        
        String contentType = multipartFile.getContentType();
        logger.info("contentType: " + contentType);
        
        try {
            byte[] bytes = multipartFile.getBytes();
            logger.info("bytes.length: " + bytes.length);
            
            // Store a backup of the original CSV file on the filesystem (in case it will be needed for debugging)
            // TODO
            
            // Convert from MultipartFile to File, and extract audio duration
            String tmpDir = System.getProperty("java.io.tmpdir");
            File tmpDirElimuAi = new File(tmpDir, "elimu-ai");
            tmpDirElimuAi.mkdir();
            File file = new File(tmpDirElimuAi, multipartFile.getOriginalFilename());
            logger.info("file: " + file);
            multipartFile.transferTo(file);
            Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(file);
            logger.info("durationMs: " + durationMs);

            // Store the audio recording in the database
            Audio audio = new Audio();
            audio.setTimeLastUpdate(Calendar.getInstance());
            audio.setContentType(contentType);
            audio.setWord(word);
            audio.setTitle(word.getText().toLowerCase());
            audio.setTranscription(word.getText().toLowerCase());
            audio.setBytes(bytes);
            audio.setDurationMs(durationMs);
            audio.setAudioFormat(audioFormat);
            audioDao.create(audio);
            
            AudioContributionEvent audioContributionEvent = new AudioContributionEvent();
            audioContributionEvent.setContributor(contributor);
            audioContributionEvent.setTime(Calendar.getInstance());
            audioContributionEvent.setTimeSpentMs(timeSpentMs);
            audioContributionEvent.setPlatform(Platform.CROWDSOURCE_APP);
            audioContributionEvent.setAudio(audio);
            audioContributionEvent.setRevisionNumber(audio.getRevisionNumber());
            audioContributionEventDao.create(audioContributionEvent);
            
            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/audio/edit/" + audio.getId();
            SlackHelper.postChatMessage("Audio created: " + contentUrl);
            DiscordHelper.postChatMessage("Audio created: " + contentUrl);
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
