package ai.elimu.util.csv;

import ai.elimu.model.content.Sound;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

import static ai.elimu.util.csv.CsvSoundExtractionHelper.getSoundsFromCsvBackup;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CsvSoundExtractionHelperTest {

    private static final String HEADERS_LINE = "id,value_ipa,audio_id,diacritic,sound_type,usage_count";

    private static <T> void verifySoundField(
        List<Sound> sounds,
        T expectedValue,
        Function<Sound, T> fieldValueSupplier
    ) {
        assertFalse("Expecting extracted Sounds are not empty", sounds.isEmpty());
        assertEquals("Assertion expect only one Sound object to check", 1, sounds.size());

        Sound sound = sounds.get(0);

        assertEquals(expectedValue, fieldValueSupplier.apply(sound));
    }

    private void writeSoundValuesToCsv(String soundValuesCsvRow) throws IOException {
        Files.write(
            soundsCsv.toPath(),
            List.of(
                HEADERS_LINE,
                soundValuesCsvRow
            )
        );
    }

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
        writeSoundValuesToCsv("");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        assertEquals(emptyList(), soundsFromCsvBackup);
    }

    @Test
    public void extracted_sound_without_valueIpa() throws Exception {
        writeSoundValuesToCsv("5,,{,,false,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, "", Sound::getValueIpa);
    }

    @Test
    public void extracted_sound_with_valueIpa() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, "æ", Sound::getValueIpa);
    }

    @Test
    public void extracted_sound_without_valueSampa() throws Exception {
        writeSoundValuesToCsv("5,æ,,,false,VOWEL,616\n");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, "", Sound::getValueSampa);
    }

    @Test
    public void extracted_sound_with_valueSampa() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, "{", Sound::getValueSampa);
    }

}