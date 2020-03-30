package ai.elimu.util.csv;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.ReadingLevel;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.model.enums.content.allophone.SoundType;
import ai.elimu.model.gson.content.StoryBookChapterGson;
import ai.elimu.model.gson.content.StoryBookGson;
import ai.elimu.model.gson.content.StoryBookParagraphGson;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.content.allophone.AllophoneCsvExportController;
import ai.elimu.web.content.emoji.EmojiCsvExportController;
import ai.elimu.web.content.letter.LetterCsvExportController;
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
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CsvContentExtractionHelper {
    
    private static final Logger logger = Logger.getLogger(CsvContentExtractionHelper.class);
    
    /**
     * For information on how the CSV files were generated, see {@link AllophoneCsvExportController#handleRequest}.
     */
    public static List<Allophone> getAllophonesFromCsvBackup(File csvFile) {
        logger.info("getAllophonesFromCsvBackup");
        
        List<Allophone> allophones = new ArrayList<>();
        
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
                
                Allophone allophone = new Allophone();
                
                String valueIpa = csvRecord.get("value_ipa");
                allophone.setValueIpa(valueIpa);
                
                String valueSampa = csvRecord.get("value_sampa");
                allophone.setValueSampa(valueSampa);
                
                boolean diacritic = Boolean.valueOf(csvRecord.get("diacritic"));
                allophone.setDiacritic(diacritic);
                
                if (StringUtils.isNotBlank(csvRecord.get("sound_type"))) {
                    SoundType soundType = SoundType.valueOf(csvRecord.get("sound_type"));
                    allophone.setSoundType(soundType);
                }
                
                Integer usageCount = Integer.valueOf(csvRecord.get("usage_count"));
                allophone.setUsageCount(usageCount);
                
                allophones.add(allophone);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return allophones;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link LetterCsvExportController#handleRequest}.
     */
    public static List<Letter> getLettersFromCsvBackup(File csvFile, AllophoneDao allophoneDao) {
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
                            "allophone_ids", 
                            "allophone_values_ipa", 
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
                
                JSONArray allophoneIdsJsonArray = new JSONArray(csvRecord.get("allophone_ids"));
                logger.info("allophoneIdsJsonArray: " + allophoneIdsJsonArray);
                
                JSONArray allophoneValuesIpaJsonArray = new JSONArray(csvRecord.get("allophone_values_ipa"));
                logger.info("allophoneValuesIpaJsonArray: " + allophoneValuesIpaJsonArray);
                List<Allophone> allophones = new ArrayList<>();
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                for (int i = 0; i < allophoneValuesIpaJsonArray.length(); i++) {
                    String allophoneValueIpa = allophoneValuesIpaJsonArray.getString(i);
                    logger.info("Looking up Allophone with IPA value /" + allophoneValueIpa + "/");
                    Allophone allophone = allophoneDao.readByValueIpa(language, allophoneValueIpa);
                    logger.info("allophone.getId(): \"" + allophone.getId() + "\"");
                    allophones.add(allophone);
                }
                letter.setAllophones(allophones);
                
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
     * For information on how the CSV files were generated, see {@link WordCsvExportController#handleRequest}.
     */
    public static List<Word> getWordsFromCsvBackup(File csvFile, AllophoneDao allophoneDao) {
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
                            "allophone_ids", 
                            "allophone_values_ipa", 
                            "usage_count",
                            "word_type",
                            "spelling_consistency"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                Word word = new Word();
                
                String text = csvRecord.get("text");
                word.setText(text);
                
                JSONArray allophoneIdsJsonArray = new JSONArray(csvRecord.get("allophone_ids"));
                logger.info("allophoneIdsJsonArray: " + allophoneIdsJsonArray);
                
                JSONArray allophoneValuesIpaJsonArray = new JSONArray(csvRecord.get("allophone_values_ipa"));
                logger.info("allophoneValuesIpaJsonArray: " + allophoneValuesIpaJsonArray);
                List<Allophone> allophones = new ArrayList<>();
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                for (int i = 0; i < allophoneValuesIpaJsonArray.length(); i++) {
                    String allophoneValueIpa = allophoneValuesIpaJsonArray.getString(i);
                    logger.info("Looking up Allophone with IPA value /" + allophoneValueIpa + "/");
                    Allophone allophone = allophoneDao.readByValueIpa(language, allophoneValueIpa);
                    logger.info("allophone.getId(): \"" + allophone.getId() + "\"");
                    allophones.add(allophone);
                }
                word.setAllophones(allophones);
                
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
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
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
                    Word word = wordDao.readByText(language, wordText);
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
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
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
                    Word word = wordDao.readByText(language, wordText);
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
                
//                if (StringUtils.isNotBlank(csvRecord.get("content_license"))) {
//                    ContentLicense contentLicense = ContentLicense.valueOf(csvRecord.get("content_license"));
//                    storyBookGson.setContentLicense(contentLicense);
//                }
                
                String attributionUrl = csvRecord.get("attribution_url");
                storyBookGson.setAttributionUrl(attributionUrl);
                
                if (StringUtils.isNotBlank(csvRecord.get("reading_level"))) {
                    ReadingLevel readingLevel = ReadingLevel.valueOf(csvRecord.get("reading_level"));
                    storyBookGson.setReadingLevel(readingLevel);
                }
                
                // TODO: set cover image
                
                List<StoryBookChapterGson> storyBookChapterGsons = new ArrayList<>();
                JSONArray chaptersJsonArray = new JSONArray(csvRecord.get("chapters"));
                logger.info("chaptersJsonArray: " + chaptersJsonArray);
                for (int i = 0; i < chaptersJsonArray.length(); i++) {
                    JSONObject chapterJsonObject = chaptersJsonArray.getJSONObject(i);
                    logger.info("chapterJsonObject: " + chapterJsonObject);
                    
                    StoryBookChapterGson storyBookChapterGson = new StoryBookChapterGson();
                    // storyBookChapterGson.setStoryBook();
                    storyBookChapterGson.setSortOrder(chapterJsonObject.getInt("sortOrder"));
                    
                    List<StoryBookParagraphGson> storyBookParagraphGsons = new ArrayList<>();
                    JSONArray paragraphsJsonArray = chapterJsonObject.getJSONArray("storyBookParagraphs");
                    logger.info("paragraphsJsonArray: " + paragraphsJsonArray);
                    for (int j = 0; j < paragraphsJsonArray.length(); j++) {
                        JSONObject paragraphJsonObject = paragraphsJsonArray.getJSONObject(j);
                        logger.info("paragraphJsonObject: " + paragraphJsonObject);

                        StoryBookParagraphGson storyBookParagraphGson = new StoryBookParagraphGson();
                        // storyBookParagraphGson.setStoryBookChapter();
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
