package ai.elimu.util.csv;

import ai.elimu.model.content.Sound;
import ai.elimu.model.v2.enums.content.sound.SoundType;
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


    @Test
    public void extracted_sound_without_diacritic() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, false, Sound::isDiacritic);
    }

    @Test
    public void extracted_sound_with_diacritic() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,true,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, true, Sound::isDiacritic);
    }

    @Test
    public void extracted_sound_without_diacritic_in_case_csv_value_was_not_boolean() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,not_boolean_value,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, false, Sound::isDiacritic);
    }

    @Test
    public void extracted_sound_without_soundType() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, null, Sound::getSoundType);
    }

    @Test
    public void extracted_sound_with_soundType() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, SoundType.VOWEL, Sound::getSoundType);
    }

    @Test
    public void extracted_sound_without_soundType_in_case_csv_value_was_not_in_enum_declaration() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,NULL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, null, Sound::getSoundType);
    }

    @Test
    public void extracted_sound_without_usageCount() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, 0, Sound::getUsageCount);
    }

    @Test
    public void extracted_sound_with_usageCount() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,616");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, 616, Sound::getUsageCount);
    }

    @Test
    public void extracted_sound_without_usageCount_in_case_csv_value_is_NULL() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,NULL");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, 0, Sound::getUsageCount);
    }

    @Test
    public void extracted_sound_without_usageCount_in_case_csv_value_is_word() throws Exception {
        writeSoundValuesToCsv("5,æ,{,,false,VOWEL,One");

        List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(soundsCsv);

        verifySoundField(soundsFromCsvBackup, 0, Sound::getUsageCount);
    }

}