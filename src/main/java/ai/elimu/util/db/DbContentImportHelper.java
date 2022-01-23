package ai.elimu.util.db;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundCorrespondenceContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.model.enums.Role;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.util.WordExtractionHelper;
import ai.elimu.util.csv.CsvContentExtractionHelper;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import ai.elimu.dao.LetterSoundCorrespondenceDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import ai.elimu.model.contributor.LetterContributionEvent;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.enums.Platform;
import ai.elimu.util.csv.CsvAnalyticsExtractionHelper;

public class DbContentImportHelper {
    
    private Logger logger = LogManager.getLogger();
    
    private LetterDao letterDao;
    
    private LetterContributionEventDao letterContributionEventDao;
    
    private AllophoneDao allophoneDao;
    
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
    private LetterSoundCorrespondenceContributionEventDao letterSoundCorrespondenceContributionEventDao;
    
    private WordDao wordDao;
    
    private WordContributionEventDao wordContributionEventDao;
    
    private NumberDao numberDao;
    
    private EmojiDao emojiDao;
    
    private StoryBookDao storyBookDao;
    
    private StoryBookLearningEventDao storyBookLearningEventDao;
    
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    private StoryBookChapterDao storyBookChapterDao;
    
    private StoryBookParagraphDao storyBookParagraphDao;
    
    private ContributorDao contributorDao;
    
    private ApplicationDao applicationDao;
    
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
        
        String contentDirectoryPath = "db" + File.separator + "content_" + environment + File.separator + language.toString().toLowerCase();
        logger.info("contentDirectoryPath: \"" + contentDirectoryPath + "\"");
        URL contentDirectoryURL = getClass().getClassLoader().getResource(contentDirectoryPath);
        logger.info("contentDirectoryURL: " + contentDirectoryURL);
        if (contentDirectoryURL == null) {
            logger.warn("The content directory was not found. Aborting content import.");
            return;
        }
        File contentDirectory = new File(contentDirectoryURL.getPath());
        logger.info("contentDirectory: " + contentDirectory);
        
        contributorDao = (ContributorDao) webApplicationContext.getBean("contributorDao");
        Contributor contributor = new Contributor();
        contributor.setEmail("dev@elimu.ai");
        contributor.setFirstName("Dev");
        contributor.setLastName("Contributor");
        contributor.setRoles(new HashSet<>(Arrays.asList(Role.CONTRIBUTOR, Role.EDITOR, Role.ANALYST, Role.ADMIN)));
        contributor.setRegistrationTime(Calendar.getInstance());
        contributorDao.create(contributor);
        
        // Extract and import Letters from CSV file in src/main/resources/
        File lettersCsvFile = new File(contentDirectory, "letters.csv");
        List<Letter> letters = CsvContentExtractionHelper.getLettersFromCsvBackup(lettersCsvFile, allophoneDao);
        logger.info("letters.size(): " + letters.size());
        letterDao = (LetterDao) webApplicationContext.getBean("letterDao");
        letterContributionEventDao = (LetterContributionEventDao) webApplicationContext.getBean("letterContributionEventDao");
        for (Letter letter : letters) {
            letterDao.create(letter);
            
            LetterContributionEvent letterContributionEvent = new LetterContributionEvent();
            letterContributionEvent.setContributor(contributor);
            letterContributionEvent.setLetter(letter);
            letterContributionEvent.setRevisionNumber(1);
            letterContributionEvent.setTime(Calendar.getInstance());
            letterContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            letterContributionEvent.setPlatform(Platform.WEBAPP);
            letterContributionEventDao.create(letterContributionEvent);
        }
        
        // Extract and import Allophones from CSV file in src/main/resources/
        File allophonesCsvFile = new File(contentDirectory, "allophones.csv");
        List<Allophone> allophones = CsvContentExtractionHelper.getAllophonesFromCsvBackup(allophonesCsvFile);
        logger.info("allophones.size(): " + allophones.size());
        allophoneDao = (AllophoneDao) webApplicationContext.getBean("allophoneDao");
        for (Allophone allophone : allophones) {
            allophoneDao.create(allophone);
        }
        
        // Extract and import letter-sound correspondences in src/main/resources/
        File letterToAllophioneMappingsCsvFile = new File(contentDirectory, "letter-sound-correspondences.csv");
        List<LetterSoundCorrespondence> letterSoundCorrespondences = CsvContentExtractionHelper.getLetterSoundCorrespondencesFromCsvBackup(letterToAllophioneMappingsCsvFile, letterDao, allophoneDao, letterSoundCorrespondenceDao);
        logger.info("letterSoundCorrespondences.size(): " + letterSoundCorrespondences.size());
        letterSoundCorrespondenceDao = (LetterSoundCorrespondenceDao) webApplicationContext.getBean("letterSoundCorrespondenceDao");
        letterSoundCorrespondenceContributionEventDao = (LetterSoundCorrespondenceContributionEventDao) webApplicationContext.getBean("letterSoundCorrespondenceContributionEventDao");
        for (LetterSoundCorrespondence letterSoundCorrespondence : letterSoundCorrespondences) {
            letterSoundCorrespondenceDao.create(letterSoundCorrespondence);
            
            LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent = new LetterSoundCorrespondenceContributionEvent();
            letterSoundCorrespondenceContributionEvent.setContributor(contributor);
            letterSoundCorrespondenceContributionEvent.setLetterSoundCorrespondence(letterSoundCorrespondence);
            letterSoundCorrespondenceContributionEvent.setRevisionNumber(1);
            letterSoundCorrespondenceContributionEvent.setTime(Calendar.getInstance());
            letterSoundCorrespondenceContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            letterSoundCorrespondenceContributionEvent.setPlatform(Platform.WEBAPP);
            letterSoundCorrespondenceContributionEventDao.create(letterSoundCorrespondenceContributionEvent);
        }
        
        // Extract and import Words from CSV file in src/main/resources/
        File wordsCsvFile = new File(contentDirectory, "words.csv");
        List<Word> words = CsvContentExtractionHelper.getWordsFromCsvBackup(wordsCsvFile, letterDao, allophoneDao, letterSoundCorrespondenceDao, wordDao);
        logger.info("words.size(): " + words.size());
        wordDao = (WordDao) webApplicationContext.getBean("wordDao");
        wordContributionEventDao = (WordContributionEventDao) webApplicationContext.getBean("wordContributionEventDao");
        for (Word word : words) {
            wordDao.create(word);
            
            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor(contributor);
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(1);
            wordContributionEvent.setTime(Calendar.getInstance());
            wordContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            wordContributionEvent.setPlatform(Platform.WEBAPP);
            wordContributionEventDao.create(wordContributionEvent);
        }
        
        // Extract and import Numbers from CSV file in src/main/resources/
        File numbersCsvFile = new File(contentDirectory, "numbers.csv");
        List<Number> numbers = CsvContentExtractionHelper.getNumbersFromCsvBackup(numbersCsvFile, wordDao);
        logger.info("numbers.size(): " + numbers.size());
        numberDao = (NumberDao) webApplicationContext.getBean("numberDao");
        for (Number number : numbers) {
            numberDao.create(number);
        }
        
        // Extract and import Syllables from CSV file in src/main/resources/
        // TODO
        
        // Extract and import Emojis from CSV file in src/main/resources/
        File emojisCsvFile = new File(contentDirectory, "emojis.csv");
        List<Emoji> emojis = CsvContentExtractionHelper.getEmojisFromCsvBackup(emojisCsvFile, wordDao);
        logger.info("emojis.size(): " + emojis.size());
        emojiDao = (EmojiDao) webApplicationContext.getBean("emojiDao");
        for (Emoji emoji : emojis) {
            emojiDao.create(emoji);
        }
        
        // Extract and import Images from CSV file in src/main/resources/
        // TODO
        
        // Extract and import Audios from CSV file in src/main/resources/
        // TODO
        
        // Extract and import StoryBooks from CSV file in src/main/resources/
        File storyBooksCsvFile = new File(contentDirectory, "storybooks.csv");
        List<StoryBookGson> storyBookGsons = CsvContentExtractionHelper.getStoryBooksFromCsvBackup(storyBooksCsvFile);
        logger.info("storyBookGsons.size(): " + storyBookGsons.size());
        storyBookDao = (StoryBookDao) webApplicationContext.getBean("storyBookDao");
        storyBookChapterDao = (StoryBookChapterDao) webApplicationContext.getBean("storyBookChapterDao");
        storyBookParagraphDao = (StoryBookParagraphDao) webApplicationContext.getBean("storyBookParagraphDao");
        storyBookContributionEventDao = (StoryBookContributionEventDao) webApplicationContext.getBean("storyBookContributionEventDao");
        for (StoryBookGson storyBookGson : storyBookGsons) {
            // Convert from GSON to JPA
            StoryBook storyBook = new StoryBook();
            storyBook.setTitle(storyBookGson.getTitle());
            storyBook.setDescription(storyBookGson.getDescription());
//            TODO: storyBook.setContentLicense();
//            TODO: storyBook.setAttributionUrl();
            storyBook.setReadingLevel(storyBookGson.getReadingLevel());
            storyBookDao.create(storyBook);
            
            for (StoryBookChapterGson storyBookChapterGson : storyBookGson.getStoryBookChapters()) {
                // Convert from GSON to JPA
                StoryBookChapter storyBookChapter = new StoryBookChapter();
                storyBookChapter.setStoryBook(storyBook);
                storyBookChapter.setSortOrder(storyBookChapterGson.getSortOrder());
                // TODO: storyBookChapter.setImage();
                storyBookChapterDao.create(storyBookChapter);
                
                for (StoryBookParagraphGson storyBookParagraphGson : storyBookChapterGson.getStoryBookParagraphs()) {
                    // Convert from GSON to JPA
                    StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
                    storyBookParagraph.setStoryBookChapter(storyBookChapter);
                    storyBookParagraph.setSortOrder(storyBookParagraphGson.getSortOrder());
                    storyBookParagraph.setOriginalText(storyBookParagraphGson.getOriginalText());
                    
                    List<String> wordsInOriginalText = WordExtractionHelper.getWords(storyBookParagraph.getOriginalText(), language);
                    logger.info("wordsInOriginalText.size(): " + wordsInOriginalText.size());
                    List<Word> paragraphWords = new ArrayList<>();
                    logger.info("paragraphWords.size(): " + paragraphWords.size());
                    for (String wordInOriginalText : wordsInOriginalText) {
                        logger.info("wordInOriginalText: \"" + wordInOriginalText + "\"");
                        wordInOriginalText = wordInOriginalText.toLowerCase();
                        logger.info("wordInOriginalText (lower-case): \"" + wordInOriginalText + "\"");
                        Word word = wordDao.readByText(wordInOriginalText);
                        logger.info("word: " + word);
                        paragraphWords.add(word);
                    }
                    storyBookParagraph.setWords(paragraphWords);
                    
                    storyBookParagraphDao.create(storyBookParagraph);
                }
            }
            
            StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
            storyBookContributionEvent.setContributor(contributor);
            storyBookContributionEvent.setStoryBook(storyBook);
            storyBookContributionEvent.setRevisionNumber(1);
            storyBookContributionEvent.setTime(Calendar.getInstance());
            storyBookContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            storyBookContributionEvent.setPlatform(Platform.WEBAPP);
            storyBookContributionEventDao.create(storyBookContributionEvent);
        }
        
        // Extract and import Videos from CSV file in src/main/resources/
        // TODO
        
        
        String analyticsDirectoryPath = "db" + File.separator + "analytics_" + environment + File.separator + language.toString().toLowerCase();
        logger.info("analyticsDirectoryPath: \"" + analyticsDirectoryPath + "\"");
        URL analyticsDirectoryURL = getClass().getClassLoader().getResource(analyticsDirectoryPath);
        logger.info("analyticsDirectoryURL: " + analyticsDirectoryURL);
        if (analyticsDirectoryURL == null) {
            logger.warn("The analytics directory was not found. Aborting analytics import.");
            return;
        }
        File analyticsDirectory = new File(analyticsDirectoryURL.getPath());
        logger.info("analyticsDirectory: " + analyticsDirectory);
        
        // Extract and import LetterLearningEvents from CSV file in src/main/resources/
        // TODO
        
        // Extract and import WordLearningEvents from CSV file in src/main/resources/
        // TODO
        
        // Extract and import StoryBookLearningEvents from CSV file in src/main/resources/
        File storyBookLearningEventsCsvFile = new File(analyticsDirectory, "storybook-learning-events.csv");
        applicationDao = (ApplicationDao) webApplicationContext.getBean("applicationDao");
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.getStoryBookLearningEventsFromCsvBackup(storyBookLearningEventsCsvFile, applicationDao, storyBookDao);
        logger.info("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
        storyBookLearningEventDao = (StoryBookLearningEventDao) webApplicationContext.getBean("storyBookLearningEventDao");
        for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
            storyBookLearningEventDao.create(storyBookLearningEvent);
        }
        
        logger.info("Content import complete");
    }
}
