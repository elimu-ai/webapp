package ai.elimu.util.db;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Language;
import ai.elimu.util.csv.CsvContentExtractionHelper;
import java.io.File;
import java.net.URL;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

public class DbContentImportHelper {
    
    private Logger logger = Logger.getLogger(getClass());
    
    private AllophoneDao allophoneDao;
    
    private LetterDao letterDao;
    
    private WordDao wordDao;
    
    private NumberDao numberDao;
    
    /**
     * Extracts educational content from the CSV files in {@code src/main/resources/db/content_TEST/<Language>/} and 
     * stores it in the database.
     * 
     * @param environment The environment from which to import the database content.
     * @param language The language to use during the import.
     * @param webApplicationContext Context needed to access DAOs.
     */
    public synchronized void performDatabaseContentImport(Environment environment, Language language, WebApplicationContext webApplicationContext) {
        logger.info("performDatabaseContentImport");
        
        logger.info("environment: " + environment + ", language: " + language);
        
        if (!((environment == Environment.TEST) || (environment == Environment.PROD))) {
            throw new IllegalArgumentException("Database content can only be imported from the TEST environment or from the PROD environment");
        }
        
        // Extract and import Allophones from CSV file in src/main/resources/
        URL allophonesCsvFileUrl = getClass().getClassLoader()
                .getResource("db/content_" + environment + "/" + language.toString().toLowerCase() + "/allophones.csv");
        File allophonesCsvFile = new File(allophonesCsvFileUrl.getFile());
        List<Allophone> allophones = CsvContentExtractionHelper.getAllophonesFromCsvBackup(allophonesCsvFile);
        logger.info("allophones.size(): " + allophones.size());
        allophoneDao = (AllophoneDao) webApplicationContext.getBean("allophoneDao");
        for (Allophone allophone : allophones) {
            allophone.setLanguage(language);
            allophoneDao.create(allophone);
        }
        
        // Extract and import Letters from CSV file in src/main/resources/
        URL lettersCsvFileUrl = getClass().getClassLoader()
                .getResource("db/content_" + environment + "/" + language.toString().toLowerCase() + "/letters.csv");
        File lettersCsvFile = new File(lettersCsvFileUrl.getFile());
        List<Letter> letters = CsvContentExtractionHelper.getLettersFromCsvBackup(lettersCsvFile, allophoneDao);
        logger.info("letters.size(): " + letters.size());
        letterDao = (LetterDao) webApplicationContext.getBean("letterDao");
        for (Letter letter : letters) {
            letter.setLanguage(language);
            letterDao.create(letter);
        }
        
        // Extract and import Words from CSV file in src/main/resources/
        URL wordsCsvFileUrl = getClass().getClassLoader()
                .getResource("db/content_" + environment + "/" + language.toString().toLowerCase() + "/words.csv");
        File wordsCsvFile = new File(wordsCsvFileUrl.getFile());
        List<Word> words = CsvContentExtractionHelper.getWordsFromCsvBackup(wordsCsvFile, allophoneDao);
        logger.info("words.size(): " + words.size());
        wordDao = (WordDao) webApplicationContext.getBean("wordDao");
        for (Word word : words) {
            word.setLanguage(language);
            wordDao.create(word);
        }
        
        // Extract and import Numbers from CSV file in src/main/resources/
        URL numbersCsvFileUrl = getClass().getClassLoader()
                .getResource("db/content_" + environment + "/" + language.toString().toLowerCase() + "/numbers.csv");
        File numbersCsvFile = new File(numbersCsvFileUrl.getFile());
        List<Number> numbers = CsvContentExtractionHelper.getNumbersFromCsvBackup(numbersCsvFile, wordDao);
        logger.info("numbers.size(): " + numbers.size());
        numberDao = (NumberDao) webApplicationContext.getBean("numberDao");
        for (Number number : numbers) {
            number.setLanguage(language);
            numberDao.create(number);
        }
        
        // Extract and import Syllables
        // TODO
        
        // Extract and import Emojis
        // TODO
        
        // Extract and import Images
        // TODO
        
        // Extract and import StoryBooks
        // TODO
        
        logger.info("Content import complete");
    }
}
