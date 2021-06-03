package ai.elimu.util.audio;

import ai.elimu.util.audio.AudioMetadataExtractionHelper;
import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class AudioMetadataExtractionHelperTest {

    private Logger logger = LogManager.getLogger();

    @Test
    public void testGetDurationInMillisecondsMP3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fourteen.mp3");
        File audioFile = resource.getFile();
        logger.debug("audioFile: " + audioFile);

        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertThat(durationMs, not(nullValue()));
        assertThat(durationMs, is(1000L)); // 1s
    }

    @Test
    public void testGetDurationInMillisecondsWAV() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fourteen.wav");
        File audioFile = resource.getFile();
        logger.debug("audioFile: " + audioFile);

        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertThat(durationMs, not(nullValue()));
        assertThat(durationMs, is(1000L)); // 1s
    }

    @Test
    public void testGetDurationInMillisecondsOGG() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("example.ogg");
        File audioFile = resource.getFile();
        logger.debug("audioFile: " + audioFile);

        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertThat(durationMs, not(nullValue()));
        assertThat(durationMs, is(6000L)); // 6s
    }
}
