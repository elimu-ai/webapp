package ai.elimu.util.csv;

import ai.elimu.model.content.Letter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

}