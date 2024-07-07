package ai.elimu.tasks;

import ai.elimu.dao.StoryBookChapterDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordFrequencyHelper;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each word. Does not 
 * separate words with differing upper-case and lower-case letters.
 */
@Service
public class WordUsageCountScheduler {
    
    private Logger logger = LogManager.getLogger();

    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Scheduled(cron="00 00 06 * * *") // At 06:00 every day
    public synchronized void execute() {
        logger.info("execute");
        
        logger.info("Calculating usage count for Words");

        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));

        List<StoryBook> storyBooks = storyBookDao.readAllOrdered();
        logger.info("storyBooks.size(): " + storyBooks.size());
        for (StoryBook storyBook : storyBooks) {
            logger.info("storyBook.getTitle(): " + storyBook.getTitle());

            List<String> paragraphs = new ArrayList<>();
            List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
            for (StoryBookChapter storyBookChapter : storyBookChapters) {
                List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
                for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
                    paragraphs.add(storyBookParagraph.getOriginalText());
                }
            }

            Map<String, Integer> wordFrequencyMapForBook = WordFrequencyHelper.getWordFrequency(paragraphs, language);
            wordFrequencyMapForBook.keySet().forEach(word -> wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + wordFrequencyMapForBook.get(word)));
        }

        for (String word : wordFrequencyMap.keySet()) {
            logger.info("word: \"" + word + "\"");
            Word existingWord = wordDao.readByText(word);
            if (existingWord != null) {
                existingWord.setUsageCount(wordFrequencyMap.get(word));
                
                // Temporary fix for "javax.validation.ConstraintViolationException"
                if (existingWord.getLetterSoundCorrespondences().isEmpty()) {
                    logger.warn("Letter-sound correspondences not yet added. Skipping usage count update for word...");
                    continue;
                }
                
                wordDao.update(existingWord);
            }
        }
        
        logger.info("execute complete");
    }
}
