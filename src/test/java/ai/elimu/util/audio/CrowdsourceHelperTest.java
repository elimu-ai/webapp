package ai.elimu.util.audio;

import ai.elimu.model.v2.enums.content.AudioFormat;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrowdsourceHelperTest {

    @Test
    public void testExtractWordIdFromFilename() {
        String filename = "word_5.mp3";
        assertEquals(5L, CrowdsourceHelper.extractWordIdFromFilename(filename));

        filename = "word_55.mp3";
        assertEquals(55L, CrowdsourceHelper.extractWordIdFromFilename(filename));

        filename = "word_555.mp3";
        assertEquals(555L, CrowdsourceHelper.extractWordIdFromFilename(filename));
    }

    @Test
    public void testExtractAudioFormatFromFilename() {
        String filename = "word_5.mp3";
        assertEquals(AudioFormat.MP3, CrowdsourceHelper.extractAudioFormatFromFilename(filename));

        filename = "word_5.wav";
        assertEquals(AudioFormat.WAV, CrowdsourceHelper.extractAudioFormatFromFilename(filename));

        filename = "word_5.ogg";
        assertEquals(AudioFormat.OGG, CrowdsourceHelper.extractAudioFormatFromFilename(filename));
    }
}
