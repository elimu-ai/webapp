package ai.elimu.util.csv;

import ai.elimu.model.content.Sound;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static ai.elimu.util.csv.CsvSoundExtractionHelper.getSoundsFromCsvBackup;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

public class CsvSoundExtractionHelperTest {

    private static final String HEADERS_LINE = "id,value_ipa,audio_id,diacritic,sound_type,usage_count";

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File soundsCsv;

    @Before
    public void setUp() throws Exception {
        soundsCsv = folder.newFile("sounds.csv");
    }

    @Test
    public void extracted_empty_sounds_for_csv_file_with_empty_content() {
        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        assertEquals(emptyList(), soundsFromCsvBackup);
    }

    @Test
    public void extracted_empty_sounds_for_csv_file_only_with_headers() throws Exception {
        Files.writeString(
            soundsCsv.toPath(),
            HEADERS_LINE
        );

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        assertEquals(emptyList(), soundsFromCsvBackup);
    }

}