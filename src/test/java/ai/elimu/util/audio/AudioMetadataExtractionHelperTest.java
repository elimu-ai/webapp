package ai.elimu.util.audio;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import lombok.extern.slf4j.Slf4j;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class AudioMetadataExtractionHelperTest {

    @Test
    public void testGetDurationInMillisecondsMP3() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fourteen.mp3");
        File audioFile = resource.getFile();
        log.debug("audioFile: " + audioFile);

        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertNotNull(durationMs);
        assertEquals(1000L, durationMs); // 1s
    }

    @Test
    public void testGetDurationInMillisecondsWAV() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fourteen.wav");
        File audioFile = resource.getFile();
        log.debug("audioFile: " + audioFile);

        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertNotNull(durationMs);
        assertEquals(1000L, durationMs); // 1s
    }

    @Test
    public void testGetDurationInMillisecondsOGG() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("example.ogg");
        File audioFile = resource.getFile();
        log.debug("audioFile: " + audioFile);

        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertNotNull(durationMs);
        assertEquals(6000L, durationMs); // 6s
    }
}
