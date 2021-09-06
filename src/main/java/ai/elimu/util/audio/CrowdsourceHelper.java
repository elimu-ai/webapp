package ai.elimu.util.audio;

import ai.elimu.model.v2.enums.content.AudioFormat;

public class CrowdsourceHelper {
    
    /**
     * E.g. "word_5.mp3" --> 5
     */
    public static Long extractWordIdFromFilename(String filename) {
       filename = filename.replace("word_", "");
       filename = filename.substring(0, filename.length() - 4);
       Long wordId = Long.valueOf(filename);
       return wordId;
    }
    
    /**
     * E.g. "word_5.mp3" --> MP3
     */
    public static AudioFormat extractAudioFormatFromFilename(String filename) {
        String audioFormatLowerCase = filename.substring(filename.length() - 3, filename.length());
        String audioFormatUpperCase = audioFormatLowerCase.toUpperCase();
        AudioFormat audioFormat = AudioFormat.valueOf(audioFormatUpperCase);
        return audioFormat;
    }
}
