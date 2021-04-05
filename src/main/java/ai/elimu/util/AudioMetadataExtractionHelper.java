package ai.elimu.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tritonus.share.sampled.file.TAudioFileFormat;

/**
 * Utility class for extraction information from audio files.
 */
public class AudioMetadataExtractionHelper {
    
    private static Logger logger = LogManager.getLogger();
    
    /**
     * Extracts the duration of an audio file.
     */
    public static Long getDurationInMilliseconds(InputStream inputStream) {
        logger.info("getDurationInMilliseconds");
        
        Long duration = null;
        try {
            AudioFileFormat audioFileFormat = AudioSystem.getAudioFileFormat(inputStream);
            logger.info("audioFileFormat: " + audioFileFormat);
            logger.info("audioFileFormat.getType(): " + audioFileFormat.getType());
            if (!(audioFileFormat instanceof TAudioFileFormat)) {
                throw new UnsupportedAudioFileException();
            } else {
                Map<String, Object> properties = ((TAudioFileFormat) audioFileFormat).properties();
                if (properties.containsKey("duration")) {
                    Long durationMicroseconds = (Long) properties.get("duration");
                    Long durationMilliseconds = durationMicroseconds / 1000;
                    duration = durationMilliseconds;
                }
            }
        } catch (UnsupportedAudioFileException | IOException ex) {
            logger.error(ex);
        }
        
        return duration;
    }
}
