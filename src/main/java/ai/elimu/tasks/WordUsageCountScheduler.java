package ai.elimu.tasks;

import ai.elimu.dao.StoryBookChapterDao;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.WordFrequencyHelper;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each word. Does not 
 * separate words with differing upper-case and lower-case letters.
 */
@Service
public class WordUsageCountScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

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
        
        for (Language language : Language.values()) {
            logger.info("Calculating usage count for Words with language " + language);
            
            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            
            List<StoryBook> storyBooks = storyBookDao.readAllOrdered(language);
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
                
                Map<String, Integer> wordFrequencyMapForBook = WordFrequencyHelper.getWordFrequency(paragraphs, language    );
                for (String key : wordFrequencyMapForBook.keySet()) {
                    int wordFrequency = wordFrequencyMapForBook.get(key);
                    String wordLowerCase = key.toLowerCase();
                    if (!wordFrequencyMap.containsKey(wordLowerCase)) {
                        wordFrequencyMap.put(wordLowerCase, wordFrequency);
                    } else {
                        wordFrequencyMap.put(wordLowerCase, wordFrequencyMap.get(wordLowerCase) + wordFrequency);
                    }
                }
            }
            
            for (String key : wordFrequencyMap.keySet()) {
                String wordLowerCase = key.toLowerCase();
                Word word = wordDao.readByText(language, wordLowerCase);
                if (word != null) {
                    word.setUsageCount(wordFrequencyMap.get(wordLowerCase));
                    wordDao.update(word);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
