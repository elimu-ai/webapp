package ai.elimu.util.csv;

import ai.elimu.model.content.Letter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;

import static ai.elimu.util.csv.CsvLetterExtractionHelper.getLettersFromCsvBackup;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class CsvLetterExtractionHelperTest {

    private static final String HEADERS_LINE = "id,text,diacritic,usage_count";

    private static <T> void verifyLetterField(
        List<Letter> letters,
        T expectedValue,
        Function<Letter, T> fieldValueSupplier
    ) {
        assertFalse("Expecting extracted Letters are not empty", letters.isEmpty());
        assertEquals("Assertion expect only one Letter object to check", 1, letters.size());

        Letter letter = letters.get(0);

        assertEquals(expectedValue, fieldValueSupplier.apply(letter));
    }

    private void writeLetterValuesToCsv(String letterValuesCsvRow) throws IOException {
        Files.write(
            lettersCsv.toPath(),
            List.of(
                HEADERS_LINE,
                letterValuesCsvRow
            )
        );
    }

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    private File lettersCsv;

    @Before
    public void setUp() throws Exception {
        lettersCsv = folder.newFile("letters.csv");
    }

    @Test
    public void extracted_empty_sounds_for_csv_file_with_empty_content() {
        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        assertEquals(emptyList(), lettersFromCsvBackup);
    }

    @Test
    public void extracted_empty_sounds_for_csv_file_only_with_headers() throws Exception {
        writeLetterValuesToCsv("");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        assertEquals(emptyList(), lettersFromCsvBackup);
    }

    @Test
    public void extracted_letter_without_text() throws Exception {
        writeLetterValuesToCsv("5,,false,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, "", Letter::getText);
    }

    @Test
    public void extracted_letter_with_text() throws Exception {
        writeLetterValuesToCsv("5,e,false,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, "e", Letter::getText);
    }

    @Test
    public void extracted_letter_without_diacritic() throws Exception {
        writeLetterValuesToCsv("5,e,,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, false, Letter::isDiacritic);
    }

    @Test
    public void extracted_letter_with_diacritic() throws Exception {
        writeLetterValuesToCsv("5,e,true,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, true, Letter::isDiacritic);
    }

    @Test
    public void extracted_letter_without_diacritic_in_case_csv_value_was_not_boolean() throws Exception {
        writeLetterValuesToCsv("5,e,not_boolean_value,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, false, Letter::isDiacritic);
    }

    @Test
    public void extracted_letter_without_usageCount() throws Exception {
        writeLetterValuesToCsv("5,e,true,");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, 0, Letter::getUsageCount);
    }

    @Test
    public void extracted_letter_with_usageCount() throws Exception {
        writeLetterValuesToCsv("5,e,true,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup,1271, Letter::getUsageCount);
    }

    @Test
    public void extracted_letter_without_usageCount_in_case_csv_value_is_NULL() throws Exception {
        writeLetterValuesToCsv("5,e,true,NULL");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, 0, Letter::getUsageCount);
    }

    @Test
    public void extracted_letter_without_usageCount_in_case_csv_value_is_word() throws Exception {
        writeLetterValuesToCsv("5,e,true,One");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(lettersCsv);

        verifyLetterField(lettersFromCsvBackup, 0, Letter::getUsageCount);
    }

    @Test
    public void try_extract_letters_for_missing_file() throws Exception {
        writeLetterValuesToCsv("5,e,true,1271");

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(
            Paths.get("not_existing_file.txt")
                .toFile()
        );

        assertEquals(emptyList(), lettersFromCsvBackup);
    }

    @Test
    public void extracted_letter_from_a_test_letters_csv_resource() throws URISyntaxException {
        String lettersCsvResourcePath = "db/content_TEST/eng/letters.csv";

        URI lettersCsvUrl = ClassLoader.getSystemResource(lettersCsvResourcePath).toURI();

        assertNotNull(
            "Test resource with CSV data not found for path: " + lettersCsvResourcePath,
            lettersCsvUrl
        );

        List<Letter> lettersFromCsvBackup = getLettersFromCsvBackup(
            Paths.get(lettersCsvUrl)
                .toFile()
        );

        assertFalse(
            "Expecting that resource: '" + lettersCsvResourcePath + "' has lines to extract",
            lettersFromCsvBackup.isEmpty()
        );

        Letter letter = lettersFromCsvBackup.get(0);
        assertEquals("e", letter.getText());
        assertEquals(Boolean.FALSE, letter.isDiacritic());
        assertEquals((Integer) 1271, letter.getUsageCount());
    }
}