package ai.elimu.util.audio;

import java.io.File;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for extraction information from audio files.
 */
@Slf4j
public class AudioMetadataExtractionHelper {

    /**
     * Extracts the duration of an audio file.
     */
    public static Long getDurationInMilliseconds(File file) {
        log.info("getDurationInMilliseconds");

        Long duration = null;
        try {
            AudioFile audioFile = AudioFileIO.read(file);
            AudioHeader audioHeader = audioFile.getAudioHeader();
            // Convert duration to milliseconds.
            duration = (long) audioHeader.getTrackLength() * 1000;
        } catch (IOException | CannotReadException | TagException |
                ReadOnlyFileException | InvalidAudioFrameException ex) {
            log.error(ex.getMessage());
        }

        return duration;
    }
}
