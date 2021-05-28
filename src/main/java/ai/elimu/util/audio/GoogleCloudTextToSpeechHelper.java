package ai.elimu.util.audio;

import ai.elimu.model.enums.Language;
import com.google.cloud.texttospeech.v1beta1.SynthesisInput;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechClient;
import java.io.File;
import java.io.IOException;
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
 * <p />
 * 
 * See sample code at https://github.com/googleapis/java-texttospeech/blob/master/samples/snippets/src/main/java/com/example/texttospeech/SynthesizeTextBeta.javas
 */
public class GoogleCloudTextToSpeechHelper {
    
    private static final String GOOGLE_API_REQUEST_URL = "https://cloud.google.com/text-to-speech/docs/basics";
    
    private static Logger logger = LogManager.getLogger();
    
    public static File synthesizeText(String text, Language language) throws NotSupportedException, IOException {
        logger.info("synthesizeText");
        
        File audioFile = null;
        
        if ((language != Language.BEN)
                && (language != Language.ENG)
                && (language != Language.HIN)
                && (language != Language.FIL)) {
            throw new NotSupportedException("This language (" + language + ") is not yet supported: https://cloud.google.com/text-to-speech/docs/voices");
        }
        
        // Instantiate the Google Cloud Text-to-Speech client
        // Prerequisite:
        //   export GOOGLE_APPLICATION_CREDENTIALS=/path/to/google-cloud-service-account-key.json
        TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();
        logger.info("textToSpeechClient: " + textToSpeechClient);
        
        // Set the text input to be synthesized
        SynthesisInput synthesisInput = SynthesisInput.newBuilder().setText(text).build();
        
        // Build the voice request
        // TODO
        
        return audioFile;
    }
}
