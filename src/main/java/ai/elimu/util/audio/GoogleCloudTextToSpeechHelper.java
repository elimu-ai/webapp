package ai.elimu.util.audio;

import ai.elimu.model.enums.Language;
import java.io.File;
import javax.transaction.NotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for connecting to Google Cloud Text-to-Speech (https://cloud.google.com/text-to-speech/) 
 * to synthesize an audio file from text.
 * <p />
 * 
 * Supported voices and languages: https://cloud.google.com/text-to-speech/docs/voices
 * <p />
 * 
 * Supported audio formats: https://cloud.google.com/speech-to-text/docs/encoding
 * <p />
 * 
 * Google Cloud Console: https://console.cloud.google.com/apis/api/texttospeech.googleapis.com
 */
public class GoogleCloudTextToSpeechHelper {
    
    private static final String GOOGLE_API_REQUEST_URL = "https://cloud.google.com/text-to-speech/docs/basics";
    
    private static Logger logger = LogManager.getLogger();
    
    public static File synthesizeText(String text, Language language) throws NotSupportedException {
        logger.info("synthesizeText");
        
        if ((language != Language.BEN)
                || (language != Language.ENG)
                || (language != Language.HIN)
                || (language != Language.FIL)) {
            throw new NotSupportedException("This language (" + language + ") is not yet supported: https://cloud.google.com/text-to-speech/docs/voices");
        }
        
        File audioFile = null;
        
        // TODO
        
        return audioFile;
    }
}
