package ai.elimu.util.audio;

import ai.elimu.util.audio.CrowdsourceHelper;
import ai.elimu.model.v2.enums.content.AudioFormat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CrowdsourceHelperTest {
    
    @Test
    public void testExtractWordIdFromFilename() {
        String filename = "word_5.mp3";
        assertThat(CrowdsourceHelper.extractWordIdFromFilename(filename), is(5L));
        
        filename = "word_55.mp3";
        assertThat(CrowdsourceHelper.extractWordIdFromFilename(filename), is(55L));
        
        filename = "word_555.mp3";
        assertThat(CrowdsourceHelper.extractWordIdFromFilename(filename), is(555L));
    }
    
    @Test
    public void testExtractAudioFormatFromFilename() {
        String filename = "word_5.mp3";
        assertThat(CrowdsourceHelper.extractAudioFormatFromFilename(filename), is(AudioFormat.MP3));
        
        filename = "word_5.wav";
        assertThat(CrowdsourceHelper.extractAudioFormatFromFilename(filename), is(AudioFormat.WAV));
        
        filename = "word_5.ogg";
        assertThat(CrowdsourceHelper.extractAudioFormatFromFilename(filename), is(AudioFormat.OGG));
    }
}
