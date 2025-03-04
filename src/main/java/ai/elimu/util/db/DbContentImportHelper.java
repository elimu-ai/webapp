package ai.elimu.util.db;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.SoundDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterContributionEvent;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.Role;
import ai.elimu.model.v2.enums.Environment;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.util.WordExtractionHelper;
import ai.elimu.util.csv.CsvAnalyticsExtractionHelper;
import ai.elimu.util.csv.CsvContentExtractionHelper;
import ai.elimu.util.csv.CsvLetterExtractionHelper;
import ai.elimu.util.csv.CsvSoundExtractionHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

@Slf4j
public class DbContentImportHelper {

    private LetterDao letterDao;

    private LetterContributionEventDao letterContributionEventDao;

    private SoundDao soundDao;

    private LetterSoundDao letterSoundDao;

    private LetterSoundContributionEventDao letterSoundContributionEventDao;

    private WordDao wordDao;

    private WordContributionEventDao wordContributionEventDao;

    private NumberDao numberDao;

    private NumberContributionEventDao numberContributionEventDao;

    private EmojiDao emojiDao;

    private ImageDao imageDao;

    private StoryBookDao storyBookDao;

    private StoryBookLearningEventDao storyBookLearningEventDao;

    private StoryBookContributionEventDao storyBookContributionEventDao;

    private StoryBookChapterDao storyBookChapterDao;

    private StoryBookParagraphDao storyBookParagraphDao;

    private ContributorDao contributorDao;

    private ApplicationDao applicationDao;

    /**
     * Extracts educational content from the CSV files in {@code src/main/resources/db/content_PROD/<Language>/} and
     * stores it in the database.
     *
     * @param environment The environment from which to import the database content.
     * @param language The language to use during the import.
     * @param webApplicationContext Context needed to access DAOs.
     */
    public synchronized void performDatabaseContentImport(Environment environment, Language language, WebApplicationContext webApplicationContext) {
        log.info("performDatabaseContentImport");

        log.info("environment: " + environment + ", language: " + language);

        if (environment != Environment.PROD) {
            throw new IllegalArgumentException("Database content can only be imported from the PROD environment");
        }

        String contentDirectoryPath = "db/content_" + environment + "/" + language.toString().toLowerCase();
        log.info("contentDirectoryPath: \"" + contentDirectoryPath + "\"");
        URL contentDirectoryURL = getClass().getClassLoader().getResource(contentDirectoryPath);
        log.info("contentDirectoryURL: " + contentDirectoryURL);
        if (contentDirectoryURL == null) {
            log.warn("The content directory was not found. Aborting content import.");
            return;
        }
        File contentDirectory = new File(contentDirectoryURL.getPath());
        log.info("contentDirectory: " + contentDirectory);

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
        List<Letter> letters = CsvLetterExtractionHelper.getLettersFromCsvBackup(lettersCsvFile);
        log.info("letters.size(): " + letters.size());
        letterDao = (LetterDao) webApplicationContext.getBean("letterDao");
        letterContributionEventDao = (LetterContributionEventDao) webApplicationContext.getBean("letterContributionEventDao");
        for (Letter letter : letters) {
            letterDao.create(letter);

            LetterContributionEvent letterContributionEvent = new LetterContributionEvent();
            letterContributionEvent.setContributor(contributor);
            letterContributionEvent.setLetter(letter);
            letterContributionEvent.setRevisionNumber(1);
            letterContributionEvent.setTimestamp(Calendar.getInstance());
            letterContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            letterContributionEventDao.create(letterContributionEvent);
        }

        // Extract and import Sounds from CSV file in src/main/resources/
        File soundsCsvFile = new File(contentDirectory, "sounds.csv");
        List<Sound> sounds = CsvSoundExtractionHelper.getSoundsFromCsvBackup(soundsCsvFile);
        log.info("sounds.size(): " + sounds.size());
        soundDao = (SoundDao) webApplicationContext.getBean("soundDao");
        for (Sound sound : sounds) {
            soundDao.create(sound);
        }

        // Extract and import letter-sound correspondences in src/main/resources/
        File letterSoundsCsvFile = new File(contentDirectory, "letter-sounds.csv");
        List<LetterSound> letterSounds = CsvContentExtractionHelper.getLetterSoundsFromCsvBackup(letterSoundsCsvFile, letterDao, soundDao, letterSoundDao);
        log.info("letterSounds.size(): " + letterSounds.size());
        letterSoundDao = (LetterSoundDao) webApplicationContext.getBean("letterSoundDao");
        letterSoundContributionEventDao = (LetterSoundContributionEventDao) webApplicationContext.getBean("letterSoundContributionEventDao");
        for (LetterSound letterSound : letterSounds) {
            letterSoundDao.create(letterSound);

            LetterSoundContributionEvent letterSoundContributionEvent = new LetterSoundContributionEvent();
            letterSoundContributionEvent.setContributor(contributor);
            letterSoundContributionEvent.setLetterSound(letterSound);
            letterSoundContributionEvent.setRevisionNumber(1);
            letterSoundContributionEvent.setTimestamp(Calendar.getInstance());
            letterSoundContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            letterSoundContributionEventDao.create(letterSoundContributionEvent);
        }

        // Extract and import Words from CSV file in src/main/resources/
        File wordsCsvFile = new File(contentDirectory, "words.csv");
        List<Word> words = CsvContentExtractionHelper.getWordsFromCsvBackup(wordsCsvFile, letterDao, soundDao, letterSoundDao, wordDao);
        log.info("words.size(): " + words.size());
        wordDao = (WordDao) webApplicationContext.getBean("wordDao");
        wordContributionEventDao = (WordContributionEventDao) webApplicationContext.getBean("wordContributionEventDao");
        for (Word word : words) {
            wordDao.create(word);

            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor(contributor);
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(1);
            wordContributionEvent.setTimestamp(Calendar.getInstance());
            wordContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            wordContributionEventDao.create(wordContributionEvent);
        }

        // Extract and import Numbers from CSV file in src/main/resources/
        File numbersCsvFile = new File(contentDirectory, "numbers.csv");
        List<Number> numbers = CsvContentExtractionHelper.getNumbersFromCsvBackup(numbersCsvFile, wordDao);
        log.info("numbers.size(): " + numbers.size());
        numberDao = (NumberDao) webApplicationContext.getBean("numberDao");
        numberContributionEventDao = (NumberContributionEventDao) webApplicationContext.getBean("numberContributionEventDao");
        for (Number number : numbers) {
            numberDao.create(number);

            NumberContributionEvent numberContributionEvent = new NumberContributionEvent();
            numberContributionEvent.setContributor(contributor);
            numberContributionEvent.setNumber(number);
            numberContributionEvent.setRevisionNumber(1);
            numberContributionEvent.setTimestamp(Calendar.getInstance());
            numberContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            numberContributionEventDao.create(numberContributionEvent);
        }

        // Extract and import Syllables from CSV file in src/main/resources/
        // TODO

        // Extract and import Emojis from CSV file in src/main/resources/
        File emojisCsvFile = new File(contentDirectory, "emojis.csv");
        List<Emoji> emojis = CsvContentExtractionHelper.getEmojisFromCsvBackup(emojisCsvFile, wordDao);
        log.info("emojis.size(): " + emojis.size());
        emojiDao = (EmojiDao) webApplicationContext.getBean("emojiDao");
        for (Emoji emoji : emojis) {
            emojiDao.create(emoji);
        }

        // Extract and import Images from CSV file in src/main/resources/
        File imagesCsvFile = new File(contentDirectory, "images.csv");
        List<Image> images = CsvContentExtractionHelper.getImagesFromCsvBackup(imagesCsvFile);
        log.info("images.size(): " + emojis.size());
        imageDao = (ImageDao) webApplicationContext.getBean("imageDao");
        for (Image image : images) {
            imageDao.create(image);
        }

        // Extract and import StoryBooks from CSV file in src/main/resources/
        File storyBooksCsvFile = new File(contentDirectory, "storybooks.csv");
        List<StoryBookGson> storyBookGsons = CsvContentExtractionHelper.getStoryBooksFromCsvBackup(storyBooksCsvFile);
        log.info("storyBookGsons.size(): " + storyBookGsons.size());
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
            // Image coverImage = imageDao.read(storyBookGson.getCoverImage().getId());
            Image coverImage = imageDao.read(1L);
            storyBook.setCoverImage(coverImage);
            storyBookDao.create(storyBook);

            for (StoryBookChapterGson storyBookChapterGson : storyBookGson.getStoryBookChapters()) {
                // Convert from GSON to JPA
                StoryBookChapter storyBookChapter = new StoryBookChapter();
                storyBookChapter.setStoryBook(storyBook);
                storyBookChapter.setSortOrder(storyBookChapterGson.getSortOrder());
                if (storyBookChapterGson.getImage() != null) {
                    Image chapterImage = imageDao.read(storyBookChapterGson.getImage().getId());
                    storyBookChapter.setImage(chapterImage);
                }
                storyBookChapterDao.create(storyBookChapter);

                for (StoryBookParagraphGson storyBookParagraphGson : storyBookChapterGson.getStoryBookParagraphs()) {
                    // Convert from GSON to JPA
                    StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
                    storyBookParagraph.setStoryBookChapter(storyBookChapter);
                    storyBookParagraph.setSortOrder(storyBookParagraphGson.getSortOrder());
                    storyBookParagraph.setOriginalText(storyBookParagraphGson.getOriginalText());

                    List<String> wordsInOriginalText = WordExtractionHelper.getWords(storyBookParagraph.getOriginalText(), language);
                    log.debug("wordsInOriginalText.size(): " + wordsInOriginalText.size());
                    List<Word> paragraphWords = new ArrayList<>();
                    log.debug("paragraphWords.size(): " + paragraphWords.size());
                    for (String wordInOriginalText : wordsInOriginalText) {
                        log.debug("wordInOriginalText: \"" + wordInOriginalText + "\"");
                        wordInOriginalText = wordInOriginalText.toLowerCase();
                        log.debug("wordInOriginalText (lower-case): \"" + wordInOriginalText + "\"");
                        Word word = wordDao.readByText(wordInOriginalText);
                        log.debug("word: " + word);
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
            storyBookContributionEvent.setTimestamp(Calendar.getInstance());
            storyBookContributionEvent.setTimeSpentMs((long)(Math.random() * 10) * 60000L);
            storyBookContributionEventDao.create(storyBookContributionEvent);
        }

        // Extract and import Videos from CSV file in src/main/resources/
        // TODO
        
        
        String analyticsDirectoryPath = "db/analytics_" + environment + "/" + language.toString().toLowerCase();
        log.info("analyticsDirectoryPath: \"" + analyticsDirectoryPath + "\"");
        URL analyticsDirectoryURL = getClass().getClassLoader().getResource(analyticsDirectoryPath);
        log.info("analyticsDirectoryURL: " + analyticsDirectoryURL);
        if (analyticsDirectoryURL == null) {
            log.warn("The analytics directory was not found. Aborting analytics import.");
            return;
        }
        File analyticsDirectory = new File(analyticsDirectoryURL.getPath());
        log.info("analyticsDirectory: " + analyticsDirectory);

        // Extract and import LetterLearningEvents from CSV file in src/main/resources/
        // TODO

        // Extract and import WordLearningEvents from CSV file in src/main/resources/
        // TODO

        // Extract and import StoryBookLearningEvents from CSV file in src/main/resources/
        File storyBookLearningEventsCsvFile = new File(analyticsDirectory, "storybook-learning-events.csv");
        applicationDao = (ApplicationDao) webApplicationContext.getBean("applicationDao");
        List<StoryBookLearningEvent> storyBookLearningEvents = CsvAnalyticsExtractionHelper.getStoryBookLearningEventsFromCsvBackup(storyBookLearningEventsCsvFile, applicationDao, storyBookDao);
        log.info("storyBookLearningEvents.size(): " + storyBookLearningEvents.size());
        storyBookLearningEventDao = (StoryBookLearningEventDao) webApplicationContext.getBean("storyBookLearningEventDao");
        for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
            storyBookLearningEventDao.create(storyBookLearningEvent);
        }

        log.info("Content import complete");
    }
}
