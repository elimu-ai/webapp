package ai.elimu.util.csv;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Himadri Mandal
 * Create a test case for CsvContentExtractionHelper.java
 * The test case should test the getWordsFromCsvBackup method
 */
class CsvContentExtractionHelperTest {

    private static final String HEADERS_LINE = "id,text,letter_sound_correspondences,usage_count,word_type,spelling_consistency,root_word_id,root_word_text";


    @TempDir
    public File folder;

    private File lettersCsv;

    @BeforeEach
    public void setUp() throws Exception {
        lettersCsv = File.createTempFile("contentBackup.csv", null, folder);
    }


    /**
     * Write the letter values to the CSV file
     * Create a private method, so this can be reused in multiple methods. If the usecase spread we can move this to a static utils as well.
     * @see CsvLetterExtractionHelperTest#writeLetterValuesToCsv(String)
     * @param letterValuesCsvRow
     * @throws IOException
     */
    private void writeLetterValuesToCsv(String letterValuesCsvRow) throws IOException {
        Files.write(
                lettersCsv.toPath(),
                List.of(
                        HEADERS_LINE,
                        letterValuesCsvRow
                )
        );
    }


    @Test
    void testGetWordsFromCsvBackup() throws IOException {
        // Create a temporary CSV file
        Path tempFile = Files.createTempFile("temp", ".csv");

        //This is sample lines from the CSV file
        writeLetterValuesToCsv("21,a,\"[{\"\"sounds\"\":[\"\"ə\"\"],\"\"id\"\":28,\"\"letters\"\":[\"\"a\"\"],\"\"usageCount\"\":0}]\",0,,HIGHLY_NON_PHONEMIC,,");

        //Mocking all DAO layers
        LetterDao letterDao = mock(LetterDao.class);
        when(letterDao.readByText(any())).thenReturn(new Letter());

        SoundDao soundDao = mock(SoundDao.class);
        when(soundDao.readByValueIpa(any())).thenReturn(new Sound());


        LetterSoundDao letterSoundDao = mock(LetterSoundDao.class);
        when(letterSoundDao.read(any(), any())).thenReturn(new LetterSound());

        // Call the method to test
        List<Word> words = CsvContentExtractionHelper.getWordsFromCsvBackup(lettersCsv.toPath().toFile(), letterDao, soundDao, letterSoundDao, any(WordDao.class));

        // Verify the results
        assertEquals(1, words.size());

        // Delete the temporary file
        Files.delete(tempFile);
    }

}