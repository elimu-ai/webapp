package ai.elimu.util.csv;

import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.GradeLevel;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.model.enums.content.allophone.SoundType;
import ai.elimu.util.ConfigHelper;
import ai.elimu.web.content.allophone.AllophoneCsvExportController;
import ai.elimu.web.content.emoji.EmojiCsvExportController;
import ai.elimu.web.content.letter.LetterCsvExportController;
import ai.elimu.web.content.number.NumberCsvExportController;
import ai.elimu.web.content.storybook.StoryBookCsvExportController;
import ai.elimu.web.content.word.WordCsvExportController;import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;

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
    public static List<StoryBook> getStoryBooksFromCsvBackup(File csvFile) {
        logger.info("getStoryBooksFromCsvBackup");
        
        List<StoryBook> storyBooks = new ArrayList<>();
        
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
                            "grade_level",
                            "cover_image_id",
                            "chapters"
                    )
                    .withSkipHeaderRecord();
            CSVParser csvParser = new CSVParser(reader, csvFormat);
            for (CSVRecord csvRecord : csvParser) {
                logger.info("csvRecord: " + csvRecord);
                
                StoryBook storyBook = new StoryBook();
                
                String title = csvRecord.get("title");
                storyBook.setTitle(title);
                
                String description = csvRecord.get("description");
                storyBook.setDescription(description);
                
                if (StringUtils.isNotBlank(csvRecord.get("content_license"))) {
                    ContentLicense contentLicense = ContentLicense.valueOf(csvRecord.get("content_license"));
                    storyBook.setContentLicense(contentLicense);
                }
                
                String attributionUrl = csvRecord.get("attribution_url");
                storyBook.setAttributionUrl(attributionUrl);
                
                if (StringUtils.isNotBlank(csvRecord.get("grade_level"))) {
                    GradeLevel gradeLevel = GradeLevel.valueOf(csvRecord.get("grade_level"));
                    storyBook.setGradeLevel(gradeLevel);
                }
                
                // TODO: set cover image
                
                storyBooks.add(storyBook);
            }
        } catch (IOException ex) {
            logger.error(ex);
        }
        
        return storyBooks;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link StoryBookCsvExportController#handleRequest}.
     */
    public static List<StoryBookChapter> getStoryBookChaptersFromCsvBackup(File csvFile, StoryBookDao storyBookDao) {
        logger.info("getStoryBookChaptersFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<StoryBookChapter> storyBookChapters = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowStoryBook = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowStoryBook++;
                
                if (rowStoryBook == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,description,description,content_license,attribution_url,grade_level,cover_image_id,chapter_ids,chapter_paragraph_texts
                // Expected row format: 1,"যে অটো আকাশে উড়েছিলো","দিল্লীর কঠিন ট্রাফিক জ্যামে পড়ে, একটি অটো জাদুর কাণ্ড করে। চালক ও যাত্রীদের সঙ্গে অটোতে উঠে বসো, এবং আকাশে ওড়ার অভিজ্ঞতা অর্জন করো।",null,"null",null,1,[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],[["অর্জুন এক তিন চাকাওয়ালা অটো। তার আছে একটি হেডলাইট এবং সবুজ-হলুদ কোট। পুরো দিল্লীর সবচেয়ে বড় পরিবারটি তার মালিক। অর্জুন যেখানেই যায় দেখা পায় ভাই, বোন, চাচা, চাচী ও কাজিনদের।  “সাবধানে যেও,” তারা চেঁচিয়ে বলে।"], ["অর্জুন দিনরাত কঠিন পরিশ্রম করে।  শব্দ করে যে চলে। ফট ফট ভট ভট!  সে কখনো অভিযোগ করে না, কারণ অটোর চালক সিরিশও কঠোর পরিশ্রম করে। সিরিশজির বুড়ো হাড়ে টনটনে ব্যথা। তিনি অর্জুনকে প্লাস্টিক ফুল ও চিত্র তারকাদের ছবি দিয়ে সাজিয়েছেন। তিনি জ্বালানি তেল দিয়ে অর্জুনের তৃষ্ণা মিটান। কখনো কখনো তেল নেওয়ার জন্য লম্বা লাইনে দাঁড়িয়ে থাকতে হয়। যখনই অর্জুনের আচ্ছাদন ছিঁড়ে যায়, সঙ্গে সঙ্গে তিনি তা জোড়াতালি দেওয়ার ব্যবস্থা করে্ন।"], ["অর্জুন পরিবারকে লাজপাত নগর বাজারে নিয়ে যেতে পছন্দ করে। যখন পর্যটকেরা চার চাকার গাড়ির বদলে তিন চাকার অটো ভাড়া করে, তখন তার আনন্দের সীমা থাকে না। কুতুব মিনারের বাইরে একটি গাছের ছায়ায় সিরিশজির সঙ্গে সে বিশ্রাম নিতে ভালোবাসে।"], ["অর্জুন কোন্নট প্লেসের রাতের জাঁকজমক দেখে কখনো ক্লান্ত হয় না। সে রেলওয়ে স্টেশনের বাইরে ব্যস্ততা ক্রিকেট দেখে রোমাঞ্চিত হয়। ক্রিকেট ম্যাচের পরে ফিরোজ শাহ কোটলা থেকে বেরিয়ে আসা জনতা দেখেও তার একই অনুভূতি হয়। জীবন ভালোই চলছে, এবং অর্জুন জানে তার এর চেয়ে বেশি চাওয়ার নেই।"], ["কিন্তু মনে মনে অর্জুন আরো কিছু আশা করে। সে উড়তে চায়। ওহ, এর জন্য তো হেলিকপ্টারের পাখা লাগবে! অর্জুন ভাবে। তার ছাদের ওপরে সেগুলো বাতাস কেটে ঘুরবে। সিরিশজি চাদর দিয়ে তার মাথা ঢাকবে্ন, এবং এর প্রান্তগুলো ফরফর করে বাতাসে উড়বে। তারা আকাশে দিয়ে চলবে, ফট ফট ভট ভট!"], ["কিন্তু অর্জুন জানে এটা কেবলই একটা স্বপ্ন। অটোর হেলিকপ্টারের পাখা হচ্ছে হাতির ডানা থাকার মতো। অথবা পেছনে বগির সারি নিয়ে কোনো ট্রেইনের রকেটের মতো আকাশে ছোটা।"], ["এক গরমের দিনে, অর্জুন ব্যস্ত চার রাস্তার মোড়ে ট্রাফিকের জটলার মধ্যে অপেক্ষা করছে। সিরিশজির পেছনে বিরস বদনে বসে আছেন পরিপাটি শাড়ি পরা এক পাকাচুলো মহিলা।  ফট ফট ভট ভট!"], ["মলিন কাপড় পরা এক ছেলে গাড়ি ও অটোর মধ্যে দিয়ে হেঁটে হেঁটে পানি বিক্রি করছে। তার চোখ উজ্জ্বল পাথরের মতো চকচক করছে। সে এক বোতল ঠাণ্ডা বাড়িয়ে দিলো।  “ম্যাম? খুব ঠাণ্ডা... খুব ঠান্ডা... জাদু।” মহিলা হাসলেন, “জাদু?” ছেলেটি এমন জোরসে মাথা ঝাঁকালো যে মনে হলো তা ছিঁড়ে পড়বে।"], ["“আমাদের সকলেরই জাদু দরকার,” মহিলা বললেন। তিনি ছেলেটির কাছ থেকে কিছু রুপি দিয়ে দুই বোতল পানি কিনলেন। সঙ্গে সঙ্গে তিনি একটি বোতল সিরিশজির হাতে দিলেন।"], ["সিরিশজি তার পানের দাগ পড়া দাঁত বের করে প্রশস্ত হাসি হাসলেন। ্তিনি ঢক ঢক করে পানি পান করলেন, আর তখনি ট্রাফিক নড়ে উঠলেন।  “এই মধ্যেই জাদু কাজ করা শুরু করেছে,” সিরিশজি কৌতুক করলেন।  মহিলাও পানি পান করলেন। গাড়ি হঠাৎ চলতে শুরু করায় একটু পানি ছিটকে অর্জুনের গায়ে পড়লো।  ফট ফট ভট ভট! অর্জুন তার এক ভাইকে পিপ পিপ করে হাই বলে উল্লাস প্রকাশ করে।"], ["যেইমাত্র চলতে শুরু করেছে, অর্জুন অনুভব করে তার চাকা হালকা হয়ে গেছে। সামনের গাড়িগুলো দুই দিকে সরে যায় এবং সে শা-শা করে সামনে এগিয়ে চলে।"], ["খোলা রাস্তা পেয়ে সিরিশজি আশ্চর্য। তিনি রিয়ার-ভিউ মিররে মহিলার চোখের দিকে তাকা্ন।  “হ্যাঁ, দারুণ জাদু, ম্যাম!” মহিলার শাড়ি ঝলমলে, সূক্ষ্ম সোনালি সুতায় নকশা করা। “জাদু...” তিনি নীরবে হাসলেন।"], ["অর্জুনের চাকাগুলো রাস্তা ছেড়ে ওপরে উঠে যায়। কেবলই ওপরে উঠতে থাকে।  ফট ফট ভট ভট... ওপরে, ওপরে, ওপরে! তাকে সাহায্য করার জন্য কোনো হেল্লিকপ্টারের পাখা নেই। জাদুই হবে, অর্জুন ভাবে। এটি অটোর জাদু!"], ["এক ঝাঁক পাখি ছড়িয়ে ছিটিয়ে গেলো। খুশিতে অর্জুনের হেডলাইট জ্বলে।"], ["অর্জুন জওহরলাল নেহরু স্টেডিয়াম ও ইন্ডিয়া গেইটের ওপর দিয়ে উড়ে যায়। সে হুমায়ুনের সমাধি, যমুনা নদী এবং অনিন্দ্যসুন্দর অকশরধাম মন্দিরের দিকে তাকায়।  সে দেখে রাস্তার বিশাল সংযোগ, উন্মাদ মাকড়সার জালের মতো।  চোখ বড় বড় করে সিরিশজি হ্যান্ডেলবার চেপে ধরেন। কোনো গাড়িকে আর পাশ কাটিয়ে যাওয়ার দরকার নেই।"], ["মহিলা আঙুল দিয়ে শাড়ি স্পর্শ করেন।  “জনাব,” রিয়ার-ভিউ মিররের দিকে তাকিয়ে মহিলা বলেন।  “আপনার চেহারা!”  সিরিশজি আয়নায় তার নিজের দিকে তাকিয়ে দেখেন তার চেহারা বলিউড নায়কের মতো হয়ে গেছে। তার দাঁত সাদা ও চামড়া উজ্জ্বল। “আমরা আরেকটু পানি পান করবো,” তিনি উচ্চস্বরে বলেন।"], ["কিন্তু আমরা কী করছি? অর্জুন ভাবে। আমরা কোথায় যেতে পারি? আমি কে, যদি আমি আর চালক না হই? অর্জুন কখনোই এতো মুক্ত ছিল না। কখনোই তিনি এভাবে নিজেকে হারিয়ে ফেলেননি। তিনি জানেন, দুনিয়ার প্রতিটি ভ্রমণের উদ্দেশ্য আছে, এবং প্রতিটি গন্তব্য সাময়িক।"], ["হর্ন বাজানো গাড়িগুলোর ওপরে এক সীমাহীন নীরবতার মধ্যে অর্জুন রাস্তার জীবনের কথা মনে করে। সেখানে গাড়ি, মোটরসাইকেল ও বাসের জটলা। নিচে তাকিয়ে সে তার পরিবারের সদস্যদের দেখে, হলুদ ছাউনির বিন্দুগুলো উজ্জ্বল আলো ছড়াচ্ছে। অর্জুন মানুষের ভিড় ও হুলুস্থূলের কথা মনে করে, সকলেই কোথাও না কোথাও পৌঁছার জন্য মরিয়া। স্থান থেকে স্থানে, ফট ফট ভট ভট..."], ["মহিলা নিচে তাঁকিয়ে সেখানকার আনন্দের কথা ভাবলেন। এখন তার ঝিকিমিকি শাড়ির কথা ভুলে যাওয়া সহজ। আমি যাচ্ছি আমার কন্যা ও নাতিনাতনিদের দেখতে, তিনি ভাবেন। তারা অপেক্ষা করছে। তারাই আমার জীবনের আসল জাদু।"], ["এর মধ্যে, সিরিশজি তার বলিউডি চেহারা নিয়ে ক্লান্ত হয়ে পড়েছেন। এ দিয়ে কী হবে, তিনি ভাবেন। তিনি যদি এটা চাইতেন। এখন সিরিশজি তার নিজের চামড়ার আরাম ফিরে চান।"], ["অর্জুনের হেডলাইট টিমটিম করছে। সে উদ্দেশ্যহীন বোধ করছে। সে সিরিশজির মনের কথা পড়তে পারে। সে মহিলার মনোভাবও আঁচ করতে পারে।  সে ধীরে ধীরে নিচে নামতে থাকে। শহর উষ্ণতা ছড়াচ্ছে। যতই নিচে নামছে, অর্জুন ততই শক্তি পাচ্ছে।"], ["সিরিশজি আবার ব্যস্ত হয়ে পড়েন। তিনি শহরের পরিচিত জাদুতে নিমজ্জিত হন। শহরের প্রতিটি চিহ্ন ও প্রতি মোড় তার সঙ্গে কথা বলছে। শীঘ্রই তিনি নিজেকে আবার চিনতে পারেন। তিনি বুঝতে পারেন কোথায় যেতে হবে। তিনি এর মধ্যে সেখানে পৌঁছে গেছেন। মহিলার শাড়ি ফ্যাকাসে হয়ে এসেছে, কিন্তু তার চেহারা জ্বলজ্বল করছে।  অর্জুনের চাকাগুলো পাকা রাস্তা স্পর্শ করে এবং তার ইঞ্জিন স্বস্তির নিঃশ্বাস ছাড়ে। ফট ফট ভট ভট... “সাবধানে যেও” সড়কের এক কোণা থেকে তার এক ভাই পিপপিপ করে বলে।"], ["মহিলার নাতিনাতনি ওপরের জানালা থেকে তার উদ্দেশ্য হাত নাড়ে।  তিনি অটো থেকে নেমে সিরিশজিকে তার ভাড়া মিটিয়ে দেন।  প্রতিবার চলাতেই নতুন অভিজ্ঞতা। নিরন্তর এই চলা, আশ্চর্য ঘটনায় পূর্ণ।"]]
                
                // Prevent glyph from being stored as ""glyph""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                // Prevent "java.lang.StoryBookFormatException: For input string: " 6]""
                // TODO: find more robust solution
                row = row.replace(", ", "§§§");
                logger.info("row (after removing ', '): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // "id"
                Long id = Long.valueOf(rowValues[0]);
                logger.info("id: " + id);
                
                StoryBook storyBook = storyBookDao.read(id);
                logger.info("storyBook: " + storyBook);
                
                // chapter_ids
                if (!"null".equals(rowValues[7])) {
                    // "[1§§§2§§§3]" --> [1, 2, 3]
                    String chapterIdArrayAsString = rowValues[7];
                    logger.info("chapterIdArrayAsString: " + chapterIdArrayAsString);
                    chapterIdArrayAsString = chapterIdArrayAsString.replace("[", "");
                    logger.info("chapterIdArrayAsString (after removing '['): " + chapterIdArrayAsString);
                    chapterIdArrayAsString = chapterIdArrayAsString.replace("]", "");
                    logger.info("chapterIdArrayAsString (after removing ']'): " + chapterIdArrayAsString);
                    String[] chapterIdArray = chapterIdArrayAsString.split("§§§");
                    logger.info("Arrays.toString(chapterIdArray) (after splitting by '§§§'): " + Arrays.toString(chapterIdArray));
                    for (int i = 0; i < chapterIdArray.length; i++) {
                        Long storyBookChapterId = Long.valueOf(chapterIdArray[i]);
                        
                        StoryBookChapter storyBookChapter = new StoryBookChapter();
                        // storyBook.setId(storyBookChapterId); // TODO: to enable later lookup of the same StoryBook by its ID
                        storyBookChapter.setStoryBook(storyBook);
                        storyBookChapter.setSortOrder(i);
                        storyBookChapters.add(storyBookChapter);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return storyBookChapters;
    }
    
    /**
     * For information on how the CSV files were generated, see {@link StoryBookCsvExportController#handleRequest}.
     */
    public static List<StoryBookParagraph> getStoryBookParagraphsFromCsvBackup(File csvFile, StoryBookChapterDao storyBookChapterDao) {
        logger.info("getStoryBookParagraphsFromCsvBackup");
        
        logger.info("csvFile: " + csvFile);
        
        List<StoryBookParagraph> storyBookParagraphs = new ArrayList<>();
        
        try {
            Scanner scanner = new Scanner(csvFile);
            int rowStoryBook = 0;
            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                logger.info("row: " + row);
                
                rowStoryBook++;
                
                if (rowStoryBook == 1) {
                    // Skip the header row
                    continue;
                }
                
                // Expected header format: id,description,description,content_license,attribution_url,grade_level,cover_image_id,chapter_ids,chapter_paragraph_texts
                // Expected row format: 1,"যে অটো আকাশে উড়েছিলো","দিল্লীর কঠিন ট্রাফিক জ্যামে পড়ে, একটি অটো জাদুর কাণ্ড করে। চালক ও যাত্রীদের সঙ্গে অটোতে উঠে বসো, এবং আকাশে ওড়ার অভিজ্ঞতা অর্জন করো।",null,"null",null,1,[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],[["অর্জুন এক তিন চাকাওয়ালা অটো। তার আছে একটি হেডলাইট এবং সবুজ-হলুদ কোট। পুরো দিল্লীর সবচেয়ে বড় পরিবারটি তার মালিক। অর্জুন যেখানেই যায় দেখা পায় ভাই, বোন, চাচা, চাচী ও কাজিনদের।  “সাবধানে যেও,” তারা চেঁচিয়ে বলে।"], ["অর্জুন দিনরাত কঠিন পরিশ্রম করে।  শব্দ করে যে চলে। ফট ফট ভট ভট!  সে কখনো অভিযোগ করে না, কারণ অটোর চালক সিরিশও কঠোর পরিশ্রম করে। সিরিশজির বুড়ো হাড়ে টনটনে ব্যথা। তিনি অর্জুনকে প্লাস্টিক ফুল ও চিত্র তারকাদের ছবি দিয়ে সাজিয়েছেন। তিনি জ্বালানি তেল দিয়ে অর্জুনের তৃষ্ণা মিটান। কখনো কখনো তেল নেওয়ার জন্য লম্বা লাইনে দাঁড়িয়ে থাকতে হয়। যখনই অর্জুনের আচ্ছাদন ছিঁড়ে যায়, সঙ্গে সঙ্গে তিনি তা জোড়াতালি দেওয়ার ব্যবস্থা করে্ন।"], ["অর্জুন পরিবারকে লাজপাত নগর বাজারে নিয়ে যেতে পছন্দ করে। যখন পর্যটকেরা চার চাকার গাড়ির বদলে তিন চাকার অটো ভাড়া করে, তখন তার আনন্দের সীমা থাকে না। কুতুব মিনারের বাইরে একটি গাছের ছায়ায় সিরিশজির সঙ্গে সে বিশ্রাম নিতে ভালোবাসে।"], ["অর্জুন কোন্নট প্লেসের রাতের জাঁকজমক দেখে কখনো ক্লান্ত হয় না। সে রেলওয়ে স্টেশনের বাইরে ব্যস্ততা ক্রিকেট দেখে রোমাঞ্চিত হয়। ক্রিকেট ম্যাচের পরে ফিরোজ শাহ কোটলা থেকে বেরিয়ে আসা জনতা দেখেও তার একই অনুভূতি হয়। জীবন ভালোই চলছে, এবং অর্জুন জানে তার এর চেয়ে বেশি চাওয়ার নেই।"], ["কিন্তু মনে মনে অর্জুন আরো কিছু আশা করে। সে উড়তে চায়। ওহ, এর জন্য তো হেলিকপ্টারের পাখা লাগবে! অর্জুন ভাবে। তার ছাদের ওপরে সেগুলো বাতাস কেটে ঘুরবে। সিরিশজি চাদর দিয়ে তার মাথা ঢাকবে্ন, এবং এর প্রান্তগুলো ফরফর করে বাতাসে উড়বে। তারা আকাশে দিয়ে চলবে, ফট ফট ভট ভট!"], ["কিন্তু অর্জুন জানে এটা কেবলই একটা স্বপ্ন। অটোর হেলিকপ্টারের পাখা হচ্ছে হাতির ডানা থাকার মতো। অথবা পেছনে বগির সারি নিয়ে কোনো ট্রেইনের রকেটের মতো আকাশে ছোটা।"], ["এক গরমের দিনে, অর্জুন ব্যস্ত চার রাস্তার মোড়ে ট্রাফিকের জটলার মধ্যে অপেক্ষা করছে। সিরিশজির পেছনে বিরস বদনে বসে আছেন পরিপাটি শাড়ি পরা এক পাকাচুলো মহিলা।  ফট ফট ভট ভট!"], ["মলিন কাপড় পরা এক ছেলে গাড়ি ও অটোর মধ্যে দিয়ে হেঁটে হেঁটে পানি বিক্রি করছে। তার চোখ উজ্জ্বল পাথরের মতো চকচক করছে। সে এক বোতল ঠাণ্ডা বাড়িয়ে দিলো।  “ম্যাম? খুব ঠাণ্ডা... খুব ঠান্ডা... জাদু।” মহিলা হাসলেন, “জাদু?” ছেলেটি এমন জোরসে মাথা ঝাঁকালো যে মনে হলো তা ছিঁড়ে পড়বে।"], ["“আমাদের সকলেরই জাদু দরকার,” মহিলা বললেন। তিনি ছেলেটির কাছ থেকে কিছু রুপি দিয়ে দুই বোতল পানি কিনলেন। সঙ্গে সঙ্গে তিনি একটি বোতল সিরিশজির হাতে দিলেন।"], ["সিরিশজি তার পানের দাগ পড়া দাঁত বের করে প্রশস্ত হাসি হাসলেন। ্তিনি ঢক ঢক করে পানি পান করলেন, আর তখনি ট্রাফিক নড়ে উঠলেন।  “এই মধ্যেই জাদু কাজ করা শুরু করেছে,” সিরিশজি কৌতুক করলেন।  মহিলাও পানি পান করলেন। গাড়ি হঠাৎ চলতে শুরু করায় একটু পানি ছিটকে অর্জুনের গায়ে পড়লো।  ফট ফট ভট ভট! অর্জুন তার এক ভাইকে পিপ পিপ করে হাই বলে উল্লাস প্রকাশ করে।"], ["যেইমাত্র চলতে শুরু করেছে, অর্জুন অনুভব করে তার চাকা হালকা হয়ে গেছে। সামনের গাড়িগুলো দুই দিকে সরে যায় এবং সে শা-শা করে সামনে এগিয়ে চলে।"], ["খোলা রাস্তা পেয়ে সিরিশজি আশ্চর্য। তিনি রিয়ার-ভিউ মিররে মহিলার চোখের দিকে তাকা্ন।  “হ্যাঁ, দারুণ জাদু, ম্যাম!” মহিলার শাড়ি ঝলমলে, সূক্ষ্ম সোনালি সুতায় নকশা করা। “জাদু...” তিনি নীরবে হাসলেন।"], ["অর্জুনের চাকাগুলো রাস্তা ছেড়ে ওপরে উঠে যায়। কেবলই ওপরে উঠতে থাকে।  ফট ফট ভট ভট... ওপরে, ওপরে, ওপরে! তাকে সাহায্য করার জন্য কোনো হেল্লিকপ্টারের পাখা নেই। জাদুই হবে, অর্জুন ভাবে। এটি অটোর জাদু!"], ["এক ঝাঁক পাখি ছড়িয়ে ছিটিয়ে গেলো। খুশিতে অর্জুনের হেডলাইট জ্বলে।"], ["অর্জুন জওহরলাল নেহরু স্টেডিয়াম ও ইন্ডিয়া গেইটের ওপর দিয়ে উড়ে যায়। সে হুমায়ুনের সমাধি, যমুনা নদী এবং অনিন্দ্যসুন্দর অকশরধাম মন্দিরের দিকে তাকায়।  সে দেখে রাস্তার বিশাল সংযোগ, উন্মাদ মাকড়সার জালের মতো।  চোখ বড় বড় করে সিরিশজি হ্যান্ডেলবার চেপে ধরেন। কোনো গাড়িকে আর পাশ কাটিয়ে যাওয়ার দরকার নেই।"], ["মহিলা আঙুল দিয়ে শাড়ি স্পর্শ করেন।  “জনাব,” রিয়ার-ভিউ মিররের দিকে তাকিয়ে মহিলা বলেন।  “আপনার চেহারা!”  সিরিশজি আয়নায় তার নিজের দিকে তাকিয়ে দেখেন তার চেহারা বলিউড নায়কের মতো হয়ে গেছে। তার দাঁত সাদা ও চামড়া উজ্জ্বল। “আমরা আরেকটু পানি পান করবো,” তিনি উচ্চস্বরে বলেন।"], ["কিন্তু আমরা কী করছি? অর্জুন ভাবে। আমরা কোথায় যেতে পারি? আমি কে, যদি আমি আর চালক না হই? অর্জুন কখনোই এতো মুক্ত ছিল না। কখনোই তিনি এভাবে নিজেকে হারিয়ে ফেলেননি। তিনি জানেন, দুনিয়ার প্রতিটি ভ্রমণের উদ্দেশ্য আছে, এবং প্রতিটি গন্তব্য সাময়িক।"], ["হর্ন বাজানো গাড়িগুলোর ওপরে এক সীমাহীন নীরবতার মধ্যে অর্জুন রাস্তার জীবনের কথা মনে করে। সেখানে গাড়ি, মোটরসাইকেল ও বাসের জটলা। নিচে তাকিয়ে সে তার পরিবারের সদস্যদের দেখে, হলুদ ছাউনির বিন্দুগুলো উজ্জ্বল আলো ছড়াচ্ছে। অর্জুন মানুষের ভিড় ও হুলুস্থূলের কথা মনে করে, সকলেই কোথাও না কোথাও পৌঁছার জন্য মরিয়া। স্থান থেকে স্থানে, ফট ফট ভট ভট..."], ["মহিলা নিচে তাঁকিয়ে সেখানকার আনন্দের কথা ভাবলেন। এখন তার ঝিকিমিকি শাড়ির কথা ভুলে যাওয়া সহজ। আমি যাচ্ছি আমার কন্যা ও নাতিনাতনিদের দেখতে, তিনি ভাবেন। তারা অপেক্ষা করছে। তারাই আমার জীবনের আসল জাদু।"], ["এর মধ্যে, সিরিশজি তার বলিউডি চেহারা নিয়ে ক্লান্ত হয়ে পড়েছেন। এ দিয়ে কী হবে, তিনি ভাবেন। তিনি যদি এটা চাইতেন। এখন সিরিশজি তার নিজের চামড়ার আরাম ফিরে চান।"], ["অর্জুনের হেডলাইট টিমটিম করছে। সে উদ্দেশ্যহীন বোধ করছে। সে সিরিশজির মনের কথা পড়তে পারে। সে মহিলার মনোভাবও আঁচ করতে পারে।  সে ধীরে ধীরে নিচে নামতে থাকে। শহর উষ্ণতা ছড়াচ্ছে। যতই নিচে নামছে, অর্জুন ততই শক্তি পাচ্ছে।"], ["সিরিশজি আবার ব্যস্ত হয়ে পড়েন। তিনি শহরের পরিচিত জাদুতে নিমজ্জিত হন। শহরের প্রতিটি চিহ্ন ও প্রতি মোড় তার সঙ্গে কথা বলছে। শীঘ্রই তিনি নিজেকে আবার চিনতে পারেন। তিনি বুঝতে পারেন কোথায় যেতে হবে। তিনি এর মধ্যে সেখানে পৌঁছে গেছেন। মহিলার শাড়ি ফ্যাকাসে হয়ে এসেছে, কিন্তু তার চেহারা জ্বলজ্বল করছে।  অর্জুনের চাকাগুলো পাকা রাস্তা স্পর্শ করে এবং তার ইঞ্জিন স্বস্তির নিঃশ্বাস ছাড়ে। ফট ফট ভট ভট... “সাবধানে যেও” সড়কের এক কোণা থেকে তার এক ভাই পিপপিপ করে বলে।"], ["মহিলার নাতিনাতনি ওপরের জানালা থেকে তার উদ্দেশ্য হাত নাড়ে।  তিনি অটো থেকে নেমে সিরিশজিকে তার ভাড়া মিটিয়ে দেন।  প্রতিবার চলাতেই নতুন অভিজ্ঞতা। নিরন্তর এই চলা, আশ্চর্য ঘটনায় পূর্ণ।"]]
                
                // Prevent glyph from being stored as ""glyph""
                // TODO: find more robust solution (e.g. by using CSV parser library or JSON array parsing)
                row = row.replace("\"", "");
                logger.info("row (after removing '\"'): " + row);
                
                // Prevent "java.lang.StoryBookFormatException: For input string: " 6]""
                // TODO: find more robust solution
                row = row.replace(", ", "§§§");
                logger.info("row (after removing ', '): " + row);
                
                String[] rowValues = row.split(",");
                logger.info("rowValues: " + Arrays.toString(rowValues));
                
                // chapter_ids
                String[] chapterIdArray = null;
                if (!"null".equals(rowValues[7])) {
                    // "[1§§§2§§§3]" --> [1, 2, 3]
                    String chapterIdArrayAsString = rowValues[7];
                    logger.info("chapterIdArrayAsString: " + chapterIdArrayAsString);
                    chapterIdArrayAsString = chapterIdArrayAsString.replace("[", "");
                    logger.info("chapterIdArrayAsString (after removing '['): " + chapterIdArrayAsString);
                    chapterIdArrayAsString = chapterIdArrayAsString.replace("]", "");
                    logger.info("chapterIdArrayAsString (after removing ']'): " + chapterIdArrayAsString);
                    chapterIdArray = chapterIdArrayAsString.split("§§§");
                    logger.info("chapterIdArray.length: " + chapterIdArray.length);
                    logger.info("Arrays.toString(chapterIdArray) (after splitting by '§§§'): " + Arrays.toString(chapterIdArray));
                }
                
                // chapter_paragraph_texts
                String[] chapterParagraphTextsArray = null;
                if (!"null".equals(rowValues[8])) {
                    // "[["আজ কোথায়"]§§§["বাবার সঙ্গে"]]" --> ["আজ কোথায়"], ["বাবার সঙ্গে"]
                    String chapterParagraphTextsArrayAsString = rowValues[8];
                    logger.info("chapterParagraphTextsArrayAsString: " + chapterParagraphTextsArrayAsString);
                    chapterParagraphTextsArrayAsString = chapterParagraphTextsArrayAsString.substring(1, chapterParagraphTextsArrayAsString.length() - 1);
                    logger.info("chapterParagraphTextsArrayAsString (after removing leading '[' and trailing ']'): " + chapterParagraphTextsArrayAsString);
                    chapterParagraphTextsArray = chapterParagraphTextsArrayAsString.split("§§§");
                    logger.info("chapterParagraphTextsArray.length: " + chapterParagraphTextsArray.length);
                    logger.info("Arrays.toString(chapterParagraphTextsArray) (after splitting by '§§§'): " + Arrays.toString(chapterParagraphTextsArray));
                    for (int i = 0; i < chapterParagraphTextsArray.length; i++) {
                        logger.info("***");
                        
                        Long storyBookChapterId = Long.valueOf(chapterIdArray[i]);
                        logger.info("storyBookChapterId: " + storyBookChapterId);
                        
                        StoryBookChapter storyBookChapter = storyBookChapterDao.read(storyBookChapterId);
                        logger.info("storyBookChapter: " + storyBookChapter);
                        
                        String originalText = chapterParagraphTextsArray[i];
                        logger.info("originalText: \"" + originalText + "\"");
                        
                        StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
                        // storyBookParagraph.setId(); // TODO
                        storyBookParagraph.setStoryBookChapter(storyBookChapter);
                        storyBookParagraph.setSortOrder(i);
                        storyBookParagraph.setOriginalText(originalText);
                        storyBookParagraphs.add(storyBookParagraph);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException ex) {
            logger.error(null, ex);
        }
        
        return storyBookParagraphs;
    }
}
