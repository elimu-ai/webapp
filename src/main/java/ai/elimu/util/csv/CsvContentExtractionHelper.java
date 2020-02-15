package ai.elimu.util.csv;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.model.enums.content.allophone.SoundType;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.content.allophone.AllophoneCsvExportController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import org.apache.log4j.Logger;

public class CsvContentExtractionHelper {
    
    private static final Logger logger = Logger.getLogger(CsvContentExtractionHelper.class);
    
    /**
     * For information on how the CSV files were generated, see {@link AllophoneCsvExportController#handleRequest}.
     */
    public static List<Allophone> getAllophonesFromCsvBackup(File csvFile) {
        logger.info("getAllophonesFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<Allophone> allophones = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowNumber = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowNumber++;
                
                if (rowNumber == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,value_ipa,value_sampa,audio_id,diacritic,sound_type,usage_count
                // Expected row format: 1,"dʒ","dZ",null,false,null,-1
                
                // Prevent "dʒ" from being stored as ""dʒ""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // "id"
                Long id = Long.valueOf(rowValues[0]);
                logger.info("id: " + id);
                
                // "value_ipa"
                String valueIpa = String.valueOf(rowValues[1]);
                logger.info("valueIpa: \"" + valueIpa + "\"");
                
                // "value_sampa"
                String valueSampa = String.valueOf(rowValues[2]);
                logger.info("valueSampa: \"" + valueSampa + "\"");
                
                // "audio_id"
                Long audioId = null;
                if (!"null".equals(rowValues[3])) {
                    audioId = Long.valueOf(rowValues[3]);
                }
                logger.info("audioId: " + audioId);
                
                // "diacritic"
                boolean diacritic = Boolean.valueOf(rowValues[4]);
                logger.info("diacritic: " + diacritic);
                
                // "sound_type"
                SoundType soundType = null;
                if (!"null".equals(rowValues[5])) {
                    soundType = SoundType.valueOf(rowValues[5]);
                }
                logger.info("soundType: " + soundType);
                
                // "usage_count"
                int usageCount = Integer.valueOf(rowValues[6]);
                logger.info("usageCount: " + usageCount);
                
                Allophone allophone = new Allophone();
                // allophone.setId(id); // TODO: enable lookup of Allophones by ID
                allophone.setValueIpa(valueIpa);
                allophone.setValueSampa(valueSampa);
                // allophone.setAudio(); // TODO: enable lookup of Audios by ID
                allophone.setDiacritic(diacritic);
                allophone.setSoundType(soundType);
                allophone.setUsageCount(usageCount);
                
                allophones.add(allophone);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return allophones;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link LetterCsvExportController#handleRequest}.
     */
    public static List<Letter> getLettersFromCsvBackup(File csvFile, AllophoneDao allophoneDao) {
        logger.info("getLettersFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<Letter> letters = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowNumber = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowNumber++;
                
                if (rowNumber == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,text,allophone_values_ipa,allophone_ids,usage_count
                // Expected row format: 1,"অ",[ɔ],[4],-1
                
                // Prevent "অ" from being stored as ""অ""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                // Prevent "java.lang.NumberFormatException: For input string: " 6]""
                // TODO: find more robust solution
                row = row.replace(", ", "|");
                logger.info("row (after removing ', '): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // "id"
                Long id = Long.valueOf(rowValues[0]);
                logger.info("id: " + id);
                
                // "text"
                String text = String.valueOf(rowValues[1]);
                logger.info("text: \"" + text + "\"");
                
                // "allophone_values_ipa"
                String allophoneValuesIpa = String.valueOf(rowValues[2]);
                logger.info("allophoneValuesIpa: \"" + allophoneValuesIpa + "\"");
                allophoneValuesIpa = allophoneValuesIpa.replace("[", "");
                logger.info("allophoneValuesIpa: \"" + allophoneValuesIpa + "\"");
                allophoneValuesIpa = allophoneValuesIpa.replace("]", "");
                logger.info("allophoneValuesIpa: \"" + allophoneValuesIpa + "\"");
                String[] allophoneValuesIpaArray = allophoneValuesIpa.split("\\|");
                logger.info("Arrays.toString(allophoneValuesIpaArray): " + Arrays.toString(allophoneValuesIpaArray));
                
                List<Allophone> allophones = new ArrayList<>();
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                for (String allophoneValueIpa : allophoneValuesIpaArray) {
                    logger.info("Looking up Allophone with IPA value /" + allophoneValueIpa + "/");
                    Allophone allophone = allophoneDao.readByValueIpa(language, allophoneValueIpa);
                    logger.info("allophone.getId(): \"" + allophone.getId() + "\"");
                    allophones.add(allophone);
                }
                
                // "allophone_ids"
                String allophoneIds = String.valueOf(rowValues[3]);
                logger.info("allophoneIds: \"" + allophoneIds + "\"");
                allophoneIds = allophoneIds.replace("[", "");
                logger.info("allophoneIds: \"" + allophoneIds + "\"");
                allophoneIds = allophoneIds.replace("]", "");
                logger.info("allophoneIds: \"" + allophoneIds + "\"");
                String[] allophoneIdsArray = allophoneIds.split("\\|");
                logger.info("Arrays.toString(allophoneIdsArray): " + Arrays.toString(allophoneIdsArray));
                
                // "usage_count"
                int usageCount = Integer.valueOf(rowValues[4]);
                logger.info("usageCount: " + usageCount);
                
                Letter letter = new Letter();
                // letter.setId(id); // TODO: to enable later lookup of the same Letter by its ID
                letter.setText(text);
                letter.setAllophones(allophones);
                letter.setUsageCount(usageCount);
                
                letters.add(letter);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return letters;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link WordCsvExportController#handleRequest}.
     */
    public static List<Word> getWordsFromCsvBackup(File csvFile, AllophoneDao allophoneDao) {
        logger.info("getWordsFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<Word> words = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowNumber = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowNumber++;
                
                if (rowNumber == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,text,phonetics,allophone_values_ipa,allophone_ids,usage_count,word_type,spelling_consistency
                // Expected row format: 7,"anim","ɑnɪm",[ɑ, n, ɪ, m],[28, 14, 36, 13],0,null,null
                
                // Prevent "anim" from being stored as ""anim""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                // Prevent "java.lang.NumberFormatException: For input string: " 6]""
                // TODO: find more robust solution
                row = row.replace(", ", "|");
                logger.info("row (after removing ', '): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // "id"
                Long id = Long.valueOf(rowValues[0]);
                logger.info("id: " + id);
                
                // "text"
                String text = String.valueOf(rowValues[1]);
                logger.info("text: \"" + text + "\"");
                
                // phonetics
                String phonetics = String.valueOf(rowValues[2]);
                logger.info("phonetics: /" + text + "/");
                
                // "allophone_values_ipa"
                String allophoneValuesIpa = String.valueOf(rowValues[3]);
                logger.info("allophoneValuesIpa: \"" + allophoneValuesIpa + "\"");
                allophoneValuesIpa = allophoneValuesIpa.replace("[", "");
                logger.info("allophoneValuesIpa: \"" + allophoneValuesIpa + "\"");
                allophoneValuesIpa = allophoneValuesIpa.replace("]", "");
                logger.info("allophoneValuesIpa: \"" + allophoneValuesIpa + "\"");
                String[] allophoneValuesIpaArray = allophoneValuesIpa.split("\\|");
                logger.info("Arrays.toString(allophoneValuesIpaArray): " + Arrays.toString(allophoneValuesIpaArray));
                
                List<Allophone> allophones = new ArrayList<>();
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                for (String allophoneValueIpa : allophoneValuesIpaArray) {
                    logger.info("Looking up Allophone with IPA value /" + allophoneValueIpa + "/");
                    Allophone allophone = allophoneDao.readByValueIpa(language, allophoneValueIpa);
                    logger.info("allophone.getId(): \"" + allophone.getId() + "\"");
                    allophones.add(allophone);
                }
                
                // "allophone_ids"
                String allophoneIds = String.valueOf(rowValues[4]);
                logger.info("allophoneIds: \"" + allophoneIds + "\"");
                allophoneIds = allophoneIds.replace("[", "");
                logger.info("allophoneIds: \"" + allophoneIds + "\"");
                allophoneIds = allophoneIds.replace("]", "");
                logger.info("allophoneIds: \"" + allophoneIds + "\"");
                String[] allophoneIdsArray = allophoneIds.split("\\|");
                logger.info("Arrays.toString(allophoneIdsArray): " + Arrays.toString(allophoneIdsArray));
                
                // "usage_count"
                int usageCount = Integer.valueOf(rowValues[5]);
                logger.info("usageCount: " + usageCount);
                
                // "word_type"
                WordType wordType = null;
                if (!"null".equals(rowValues[6])) {
                    wordType = WordType.valueOf(rowValues[6]);
                }
                logger.info("wordType: " + wordType);
                
                // spelling_consistency
                SpellingConsistency spellingConsistency = null;
                if (!"null".equals(rowValues[7])) {
                    spellingConsistency = SpellingConsistency.valueOf(rowValues[7]);
                }
                logger.info("spellingConsistency: " + spellingConsistency);
                
                Word word = new Word();
                // word.setId(id); // TODO: to enable later lookup of the same Word by its ID
                word.setText(text);
                word.setPhonetics(phonetics);
                word.setAllophones(allophones);
                word.setUsageCount(usageCount);
                word.setWordType(wordType);
                word.setSpellingConsistency(spellingConsistency);
                words.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return words;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link NumberCsvExportController#handleRequest}.
     */
    public static List<Number> getNumbersFromCsvBackup(File csvFile, WordDao wordDao) {
        logger.info("getNumbersFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<Number> numbers = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowNumber = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowNumber++;
                
                if (rowNumber == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,value,symbol,word_texts,word_ids
                // Expected row format: 1,0,"null",["zero"],[1]
                
                // Prevent "null" from being stored as ""null""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                // Prevent "java.lang.NumberFormatException: For input string: " 6]""
                // TODO: find more robust solution
                row = row.replace(", ", "|");
                logger.info("row (after removing ', '): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // "id"
                Long id = Long.valueOf(rowValues[0]);
                logger.info("id: " + id);
                
                // "value"
                Integer value = Integer.valueOf(rowValues[1]);
                logger.info("value: " + value);
                
                // "symbol"
                String symbol = null;
                if (!"null".equals(rowValues[2])) {
                    symbol = String.valueOf(rowValues[2]);
                }
                logger.info("symbol: \"" + symbol + "\"");
                
                // "word_texts"
                String wordTexts = String.valueOf(rowValues[3]);
                logger.info("wordTexts: \"" + wordTexts + "\"");
                wordTexts = wordTexts.replace("[", "");
                logger.info("wordTexts: \"" + wordTexts + "\"");
                wordTexts = wordTexts.replace("]", "");
                logger.info("wordTexts: \"" + wordTexts + "\"");
                String[] wordTextsArray = wordTexts.split("\\|");
                logger.info("Arrays.toString(wordTextsArray): " + Arrays.toString(wordTextsArray));
                        
                List<Word> words = new ArrayList<>();
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                for (String wordText : wordTextsArray) {
                    logger.info("Looking up Word with text \"" + wordText + "\"");
                    Word word = wordDao.readByText(language, wordText);
                    logger.info("word.getId(): \"" + word.getId() + "\"");
                    words.add(word);
                }
                
                // "word_ids"
                String wordIds = String.valueOf(rowValues[4]);
                logger.info("wordIds: \"" + wordIds + "\"");
                wordIds = wordIds.replace("[", "");
                logger.info("wordIds: \"" + wordIds + "\"");
                wordIds = wordIds.replace("]", "");
                logger.info("wordIds: \"" + wordIds + "\"");
                String[] wordIdsArray = wordIds.split("\\|");
                logger.info("Arrays.toString(wordIdsArray): " + Arrays.toString(wordIdsArray));
                
                Number number = new Number();
                // number.setId(id); // TODO: to enable later lookup of the same Number by its ID
                number.setValue(value);
                number.setSymbol(symbol);
                number.setWords(words);
                numbers.add(number);
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return numbers;
    }
}
