package ai.elimu.util.csv;

import static ai.elimu.util.csv.CsvSoundExtractionHelper.getSoundsFromCsvBackup;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ai.elimu.model.content.Sound;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class CsvSoundExtractionHelperTest {

  private static final String HEADERS_LINE = "id,value_ipa,audio_id,diacritic,sound_type,usage_count";

  private static <T> void verifySoundField(
      List<Sound> sounds,
      T expectedValue,
      Function<Sound, T> fieldValueSupplier
  ) {
    assertFalse(sounds.isEmpty(), "Expecting extracted Sounds are not empty");
    assertEquals(1, sounds.size(), "Assertion expect only one Sound object to check");

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

  @TempDir
  public File folder;

  private File soundsCsv;

  @BeforeEach
  public void setUp() throws Exception {
    soundsCsv = File.createTempFile("sounds.csv", null, folder);
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

  @Test
  public void try_extract_sounds_for_missing_file() throws Exception {
    writeSoundValuesToCsv("5,æ,{,,false,VOWEL,1");

    List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(
        Paths.get("not_existing_file.txt")
            .toFile()
    );

    assertEquals(emptyList(), soundsFromCsvBackup);
  }

  @Test
  public void extracted_sound_from_a_test_sounds_csv_resource() throws URISyntaxException {
    String soundsCsvResourcePath = "db/content_TEST/eng/sounds.csv";

    URI soundsCsvUrl = ClassLoader.getSystemResource(soundsCsvResourcePath).toURI();

    assertNotNull(
        soundsCsvUrl,
        "Test resource with CSV data not found for path: " + soundsCsvResourcePath
    );

    List<Sound> soundsFromCsvBackup = getSoundsFromCsvBackup(
        Paths.get(soundsCsvUrl)
            .toFile()
    );

    assertFalse(
        soundsFromCsvBackup.isEmpty(),
        "Expecting that resource: '" + soundsCsvResourcePath + "' has lines to extract"
    );

    Sound sound = soundsFromCsvBackup.get(0);
    assertEquals("æ", sound.getValueIpa());
    assertEquals("{", sound.getValueSampa());
    assertEquals(Boolean.FALSE, sound.isDiacritic());
    assertEquals(SoundType.VOWEL, sound.getSoundType());
    assertEquals((Integer) 616, sound.getUsageCount());
  }
}