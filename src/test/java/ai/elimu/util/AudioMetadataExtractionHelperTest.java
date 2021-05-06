package ai.elimu.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;;
import org.junit.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class AudioMetadataExtractionHelperTest {

    private Logger logger = LogManager.getLogger();

    @Test
    public void testGetDurationInMilliseconds() throws IOException {
        ResourceLoader resourceLoader = new ClassRelativeResourceLoader(AudioMetadataExtractionHelper.class);
        Resource resource = resourceLoader.getResource("fourteen.mp3");
        File audioFile = resource.getFile();
        logger.debug("audioFile: " + audioFile);

        InputStream inputStream = new FileInputStream(audioFile);
        Long durationMs = AudioMetadataExtractionHelper.getDurationInMilliseconds(audioFile);
        assertThat(durationMs, not(nullValue()));
        assertThat(durationMs, is(1000L)); // 00:00.961
    }
}
