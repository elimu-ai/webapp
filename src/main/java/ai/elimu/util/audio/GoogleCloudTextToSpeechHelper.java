package ai.elimu.util.audio;

import ai.elimu.model.v2.enums.Language;
import com.google.cloud.texttospeech.v1beta1.AudioConfig;
import com.google.cloud.texttospeech.v1beta1.AudioEncoding;
import com.google.cloud.texttospeech.v1beta1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1beta1.SynthesisInput;
import com.google.cloud.texttospeech.v1beta1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1beta1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1beta1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import java.io.IOException;
import javax.transaction.NotSupportedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for connecting to Google Cloud Text-to-Speech (https://cloud.google.com/text-to-speech/) 
 * to synthesize an audio file from text.
 * <p>
 * Supported voices and languages: https://cloud.google.com/text-to-speech/docs/voices
 * <p>
 * Supported audio formats: https://cloud.google.com/speech-to-text/docs/encoding
 * <p>
 * Google Cloud Console: https://console.cloud.google.com/apis/api/texttospeech.googleapis.com
 * <p>
 * See sample code at https://github.com/googleapis/java-texttospeech/blob/master/samples/snippets/src/main/java/com/example/texttospeech/SynthesizeTextBeta.javas
 */
public class GoogleCloudTextToSpeechHelper {
    
    private static Logger logger = LogManager.getLogger();
    
    public static byte[] synthesizeText(String text, Language language) throws NotSupportedException, IOException {
        logger.info("synthesizeText");
        
        byte[] byteArray = null;
        
        logger.info("text: \"" + text + "\"");
        
        if ((language != Language.BEN)
                && (language != Language.ENG)
                && (language != Language.FIL)
                && (language != Language.HIN)) {
            throw new NotSupportedException("This language (" + language + ") is not yet supported: https://cloud.google.com/text-to-speech/docs/voices");
        }
        String languageCode = null;
        if (language == Language.BEN) {
            languageCode = "bn";
        } else if (language == Language.ENG) {
            languageCode = "en";
        } else if (language == Language.FIL) {
            languageCode = "fil";
        } else if (language == Language.HIN) {
            languageCode = "hi";
        }
        logger.info("languageCode: " + languageCode);
        
        // For the Google Cloud TextToSpeechClient to work, an environment variable GOOGLE_APPLICATION_CREDENTIALS needs to exist.
        // To enable this during development, download the JSON file from https://console.cloud.google.com/iam-admin/serviceaccounts 
        // and run the following command:
        //   export GOOGLE_APPLICATION_CREDENTIALS=/path/to/google-cloud-service-account-key.json
        logger.info("System.getenv(\"GOOGLE_APPLICATION_CREDENTIALS\"): \"" + System.getenv("GOOGLE_APPLICATION_CREDENTIALS") + "\"");
        TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();
        logger.info("textToSpeechClient: " + textToSpeechClient);
        
        // Set the text input to be synthesized
        SynthesisInput synthesisInput = SynthesisInput.newBuilder()
                .setText(text)
                .build();
        logger.info("synthesisInput: " + synthesisInput);
        
        // Build the voice request
        VoiceSelectionParams voiceSelectionParams = VoiceSelectionParams.newBuilder()
                .setLanguageCode(languageCode) // TODO: fetch from Language enum
                .setSsmlGender(SsmlVoiceGender.FEMALE)
                .build();
        logger.info("voiceSelectionParams: " + voiceSelectionParams);
        
        // Select the type of audio file to be returned
        AudioConfig audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.MP3)
                .build();
        logger.info("audioConfig: " + audioConfig);
        
        // Perform Text-to-Speech request
        SynthesizeSpeechResponse synthesizeSpeechResponse = textToSpeechClient.synthesizeSpeech(synthesisInput, voiceSelectionParams, audioConfig);
        logger.info("synthesizeSpeechResponse: " + synthesizeSpeechResponse);
        
        // Get the audio contents from the response
        ByteString audioContentsByteString = synthesizeSpeechResponse.getAudioContent();
        
        // Convert to byte array
        byteArray = audioContentsByteString.toByteArray();
        
        return byteArray;
    }
}
