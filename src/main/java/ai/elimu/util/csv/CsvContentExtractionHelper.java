package ai.elimu.util.csv;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import ai.elimu.model.v2.enums.content.allophone.SoundType;
import ai.elimu.model.v2.gson.content.StoryBookChapterGson;
import ai.elimu.model.v2.gson.content.StoryBookGson;
import ai.elimu.model.v2.gson.content.StoryBookParagraphGson;
import ai.elimu.web.content.sound.SoundCsvExportController;
import ai.elimu.web.content.emoji.EmojiCsvExportController;
import ai.elimu.web.content.letter.LetterCsvExportController;
import ai.elimu.web.content.letter_sound_correspondence.LetterSoundCorrespondenceCsvExportController;
import ai.elimu.web.content.number.NumberCsvExportController;
import ai.elimu.web.content.storybook.StoryBookCsvExportController;
import ai.elimu.web.content.word.WordCsvExportController;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import ai.elimu.dao.LetterSoundCorrespondenceDao;
import ai.elimu.dao.SoundDao;

public class CsvContentExtractionHelper {
    
    private static final Logger logger = LogManager.getLogger();
    
    /**
     * For information on how the CSV files were generated, see {@link SoundCsvExportController#handleRequest}.
     */
    public static List<Allophone> getSoundsFromCsvBackup(File csvFile) {
        logger.info("getSoundsFromCsvBackup");
        
        List<Allophone> sounds = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id", 
                            "value_ipa", 
                            "value_sampa", 
                            "audio_id", 
                            "diacritic", 
                            "sound_type", 
                            "usage_count"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                Allophone sound = new Allophone();
                
                String valueIpa = csvRecord.get("value_ipa");
                sound.setValueIpa(valueIpa);
                
                String valueSampa = csvRecord.get("value_sampa");
                sound.setValueSampa(valueSampa);
                
                boolean diacritic = Boolean.valueOf(csvRecord.get("diacritic"));
                sound.setDiacritic(diacritic);
                
                if (StringUtils.isNotBlank(csvRecord.get("sound_type"))) {
                    SoundType soundType = SoundType.valueOf(csvRecord.get("sound_type"));
                    sound.setSoundType(soundType);
                }
                
                Integer usageCount = Integer.valueOf(csvRecord.get("usage_count"));
                sound.setUsageCount(usageCount);
                
                sounds.add(sound);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return sounds;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link LetterCsvExportController#handleRequest}.
     */
    public static List<Letter> getLettersFromCsvBackup(File csvFile, SoundDao soundDao) {
        logger.info("getLettersFromCsvBackup");
        
        List<Letter> letters = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "text",
                            "diacritic",
                            "usage_count"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                Letter letter = new Letter();
                
                String text = csvRecord.get("text");
                letter.setText(text);

                boolean diacritic = Boolean.valueOf(csvRecord.get("diacritic"));
                letter.setDiacritic(diacritic);
                
                Integer usageCount = Integer.valueOf(csvRecord.get("usage_count"));
                letter.setUsageCount(usageCount);
                
                letters.add(letter);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return letters;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link LetterSoundCorrespondenceCsvExportController#handleRequest}.
     */
    public static List<LetterSoundCorrespondence> getLetterSoundCorrespondencesFromCsvBackup(File csvFile, LetterDao letterDao, SoundDao soundDao, LetterSoundCorrespondenceDao letterSoundCorrespondenceDao) {
        logger.info("getLetterSoundCorrespondencesFromCsvBackup");
        
        List<LetterSoundCorrespondence> letterSoundCorrespondences = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "letter_ids",
                            "letter_texts",
                            "sound_ids",
                            "sound_values_ipa",
                            "usage_count"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                LetterSoundCorrespondence letterSoundCorrespondence = new LetterSoundCorrespondence();
                
                JSONArray letterIdsJsonArray = new JSONArray(csvRecord.get("letter_ids"));
                logger.info("letterIdsJsonArray: " + letterIdsJsonArray);
                
                JSONArray letterTextsJsonArray = new JSONArray(csvRecord.get("letter_texts"));
                logger.info("letterTextsJsonArray: " + letterTextsJsonArray);
                List<Letter> letters = new ArrayList<>();
                for (int i = 0; i < letterTextsJsonArray.length(); i++) {
                    String letterText = letterTextsJsonArray.getString(i);
                    logger.info("Looking up Letter with text '" + letterText + "'");
                    Letter letter = letterDao.readByText(letterText);
                    logger.info("letter.getId(): " + letter.getId());
                    letters.add(letter);
                }
                letterSoundCorrespondence.setLetters(letters);
                
                JSONArray soundIdsJsonArray = new JSONArray(csvRecord.get("sound_ids"));
                logger.info("soundIdsJsonArray: " + soundIdsJsonArray);
                
                JSONArray soundValuesIpaJsonArray = new JSONArray(csvRecord.get("sound_values_ipa"));
                logger.info("soundValuesIpaJsonArray: " + soundValuesIpaJsonArray);
                List<Allophone> sounds = new ArrayList<>();
                for (int i = 0; i < soundValuesIpaJsonArray.length(); i++) {
                    String soundValueIpa = soundValuesIpaJsonArray.getString(i);
                    logger.info("Looking up Sound with IPA value /" + soundValueIpa + "/");
                    Allophone sound = soundDao.readByValueIpa(soundValueIpa);
                    logger.info("sound.getId(): " + sound.getId());
                    sounds.add(sound);
                }
                letterSoundCorrespondence.setAllophones(sounds);
                
                Integer usageCount = Integer.valueOf(csvRecord.get("usage_count"));
                letterSoundCorrespondence.setUsageCount(usageCount);
                
                letterSoundCorrespondences.add(letterSoundCorrespondence);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return letterSoundCorrespondences;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link WordCsvExportController#handleRequest}.
     */
    public static List<Word> getWordsFromCsvBackup(File csvFile, LetterDao letterDao, SoundDao soundDao, LetterSoundCorrespondenceDao letterSoundCorrespondenceDao, WordDao wordDao) {
        logger.info("getWordsFromCsvBackup");
        
        List<Word> words = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "text",
                            "letter_sound_correspondences",
                            "usage_count",
                            "word_type",
                            "spelling_consistency",
                            "root_word_id",
                            "root_word_text"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                Word word = new Word();
                
                String text = csvRecord.get("text");
                word.setText(text);
                
                JSONArray letterSoundCorrespondencesJsonArray = new JSONArray(csvRecord.get("letter_sound_correspondences"));
                logger.info("letterSoundCorrespondencesJsonArray: " + letterSoundCorrespondencesJsonArray);
                List<LetterSoundCorrespondence> letterSoundCorrespondences = new ArrayList<>();
                for (int i = 0; i < letterSoundCorrespondencesJsonArray.length(); i++) {
                    JSONObject letterSoundCorrespondenceJsonObject = letterSoundCorrespondencesJsonArray.getJSONObject(i);
                    logger.info("letterSoundCorrespondenceJsonObject: " + letterSoundCorrespondenceJsonObject);
                    List<Letter> letters = new ArrayList<>();
                    JSONArray lettersJsonArray = letterSoundCorrespondenceJsonObject.getJSONArray("letters");
                    for (int j = 0; j < lettersJsonArray.length(); j++) {
                        Letter letter = letterDao.readByText(lettersJsonArray.getString(j));
                        letters.add(letter);
                    }
                    List<Allophone> sounds = new ArrayList<>();
                    JSONArray soundsJsonArray = letterSoundCorrespondenceJsonObject.getJSONArray("sounds");
                    for (int j = 0; j < soundsJsonArray.length(); j++) {
                        Allophone sound = soundDao.readByValueIpa(soundsJsonArray.getString(j));
                        sounds.add(sound);
                    }
                    LetterSoundCorrespondence letterSoundCorrespondence = letterSoundCorrespondenceDao.read(letters, sounds);
                    logger.info("letterSoundCorrespondence.getId(): " + letterSoundCorrespondence.getId());
                    letterSoundCorrespondences.add(letterSoundCorrespondence);
                }
                word.setLetterSoundCorrespondences(letterSoundCorrespondences);
                
                Integer usageCount = Integer.valueOf(csvRecord.get("usage_count"));
                word.setUsageCount(usageCount);
                
                if (StringUtils.isNotBlank(csvRecord.get("word_type"))) {
                    WordType wordType = WordType.valueOf(csvRecord.get("word_type"));
                    word.setWordType(wordType);
                }
                
                if (StringUtils.isNotBlank(csvRecord.get("spelling_consistency"))) {
                    SpellingConsistency spellingConsistency = SpellingConsistency.valueOf(csvRecord.get("spelling_consistency"));
                    word.setSpellingConsistency(spellingConsistency);
                }
                
                // TODO: Store rootWords _after_ all Words have been stored
//                if (StringUtils.isNotBlank(csvRecord.get("root_word_text"))) {
//                    String rootWordText = csvRecord.get("root_word_text");
//                    Word rootWord = wordDao.readByText(language, rootWordText);
//                    word.setRootWord(rootWord);
//                }
                
                words.add(word);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return words;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link NumberCsvExportController#handleRequest}.
     */
    public static List<Number> getNumbersFromCsvBackup(File csvFile, WordDao wordDao) {
        logger.info("getNumbersFromCsvBackup");
        
        List<Number> numbers = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id", 
                            "value", 
                            "symbol", 
                            "word_ids", 
                            "word_texts"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                Number number = new Number();
                
                Integer value = Integer.valueOf(csvRecord.get("value"));
                number.setValue(value);
                
                String symbol = csvRecord.get("symbol");
                number.setSymbol(symbol);
                
                JSONArray wordIdsJsonArray = new JSONArray(csvRecord.get("word_ids"));
                logger.info("wordIdsJsonArray: " + wordIdsJsonArray);
                
                JSONArray wordTextsJsonArray = new JSONArray(csvRecord.get("word_texts"));
                logger.info("wordTextsJsonArray: " + wordTextsJsonArray);
                List<Word> words = new ArrayList<>();
                for (int i = 0; i < wordTextsJsonArray.length(); i++) {
                    String wordText = wordTextsJsonArray.getString(i);
                    logger.info("Looking up Word with text /" + wordText + "/");
                    Word word = wordDao.readByText(wordText);
                    logger.info("word.getId(): \"" + word.getId() + "\"");
                    words.add(word);
                }
                number.setWords(words);
                
                numbers.add(number);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return numbers;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link EmojiCsvExportController#handleRequest}.
     */
    public static List<Emoji> getEmojisFromCsvBackup(File csvFile, WordDao wordDao) {
        logger.info("getEmojisFromCsvBackup");
        
        List<Emoji> emojis = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "glyph",
                            "unicode_version",
                            "unicode_emoji_version",
                            "word_ids",
                            "word_texts"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                Emoji emoji = new Emoji();
                
                String glyph = csvRecord.get("glyph");
                emoji.setGlyph(glyph);
                
                Double unicodeVersion = Double.valueOf(csvRecord.get("unicode_version"));
                emoji.setUnicodeVersion(unicodeVersion);
                
                Double unicodeEmojiVersion = Double.valueOf(csvRecord.get("unicode_emoji_version"));
                emoji.setUnicodeEmojiVersion(unicodeEmojiVersion);
                
                JSONArray wordIdsJsonArray = new JSONArray(csvRecord.get("word_ids"));
                logger.info("wordIdsJsonArray: " + wordIdsJsonArray);
                
                JSONArray wordTextsJsonArray = new JSONArray(csvRecord.get("word_texts"));
                logger.info("wordTextsJsonArray: " + wordTextsJsonArray);
                Set<Word> words = new HashSet<>();
                for (int i = 0; i < wordTextsJsonArray.length(); i++) {
                    String wordText = wordTextsJsonArray.getString(i);
                    logger.info("Looking up Word with text /" + wordText + "/");
                    Word word = wordDao.readByText(wordText);
                    logger.info("word.getId(): \"" + word.getId() + "\"");
                    words.add(word);
                }
                emoji.setWords(words);
                
                emojis.add(emoji);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return emojis;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link StoryBookCsvExportController#handleRequest}.
     * <p />
     * Also see {@link #getStoryBookChaptersFromCsvBackup}
     */
    public static List<StoryBookGson> getStoryBooksFromCsvBackup(File csvFile) {
        logger.info("getStoryBooksFromCsvBackup");
        
        List<StoryBookGson> storyBookGsons = new ArrayList<>();
        
        Path csvFilePath = Paths.get(csvFile.toURI());
        logger.info("csvFilePath: " + csvFilePath);
        try {
            Reader reader = Files.newBufferedReader(csvFilePath);
            CSVFormat csvFormat = CSVFormat.DEFAULT
                    .withHeader(
                            "id",
                            "title",
                            "description",
                            "content_license",
                            "attribution_url",
                            "reading_level",
                            "cover_image_id",
                            "chapters"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                // Convert from CSV to GSON
                
                StoryBookGson storyBookGson = new StoryBookGson();
                
                String title = csvRecord.get("title");
                storyBookGson.setTitle(title);
                
                String description = csvRecord.get("description");
                storyBookGson.setDescription(description);
                
                if (StringUtils.isNotBlank(csvRecord.get("content_license"))) {
                    ContentLicense contentLicense = ContentLicense.valueOf(csvRecord.get("content_license"));
//                    storyBookGson.setContentLicense(contentLicense);
                }
                
                String attributionUrl = csvRecord.get("attribution_url");
//                storyBookGson.setAttributionUrl(attributionUrl);
                
                if (StringUtils.isNotBlank(csvRecord.get("reading_level"))) {
                    ReadingLevel readingLevel = ReadingLevel.valueOf(csvRecord.get("reading_level"));
                    storyBookGson.setReadingLevel(readingLevel);
                }
                
                if (StringUtils.isNotBlank(csvRecord.get("cover_image_id"))) {
                    Long coverImageId = Long.valueOf(csvRecord.get("cover_image_id"));
//                    storyBookGson.setCoverImage();
                }
                
                List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
                JSONArray chaptersJsonArray = new JSONArray(csvRecord.get("chapters"));
                logger.info("chaptersJsonArray: " + chaptersJsonArray);
                for (int i = 0; i < chaptersJsonArray.length(); i++) {
                    JSONObject chapterJsonObject = chaptersJsonArray.getJSONObject(i);
                    logger.info("chapterJsonObject: " + chapterJsonObject);
                    
                    StoryBookChapterGson storyBookChapterGson = new StoryBookChapterGson();
                    storyBookChapterGson.setSortOrder(chapterJsonObject.getInt("sortOrder"));
                    
                    List<StoryBookParagraphGson> storyBookParagraphGsons = new ArrayList<>();
                    JSONArray paragraphsJsonArray = chapterJsonObject.getJSONArray("storyBookParagraphs");
                    logger.info("paragraphsJsonArray: " + paragraphsJsonArray);
                    for (int j = 0; j < paragraphsJsonArray.length(); j++) {
                        JSONObject paragraphJsonObject = paragraphsJsonArray.getJSONObject(j);
                        logger.info("paragraphJsonObject: " + paragraphJsonObject);

                        StoryBookParagraphGson storyBookParagraphGson = new StoryBookParagraphGson();
                        storyBookParagraphGson.setSortOrder(paragraphJsonObject.getInt("sortOrder"));
                        storyBookParagraphGson.setOriginalText(paragraphJsonObject.getString("originalText"));
                        // TODO: setWords
                        
                        storyBookParagraphGsons.add(storyBookParagraphGson);
                    }
                    storyBookChapterGson.setStoryBookParagraphs(storyBookParagraphGsons);
                    
                    storyBookChapterGsons.add(storyBookChapterGson);
                }
                storyBookGson.setStoryBookChapters(storyBookChapterGsons);
                
                storyBookGsons.add(storyBookGson);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return storyBookGsons;
    }
}
