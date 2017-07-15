package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;
import ai.elimu.util.WordFrequencyHelper;
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
    
    @Scheduled(cron="00 00 06 * * *") // At 06:00 every day
    public synchronized void execute() {
        logger.info("execute");
        
        for (Locale locale : Locale.values()) {
            logger.info("Calculating usage count for Words with locale " + locale);
            
            Map<String, Integer> wordFrequencyMap = new HashMap<>();
            
            List<StoryBook> storyBooks = storyBookDao.readAllOrdered(locale);
            logger.info("storyBooks.size(): " + storyBooks.size());
            for (StoryBook storyBook : storyBooks) {
                logger.info("storyBook.getTitle(): " + storyBook.getTitle());
                
                Map<String, Integer> wordFrequencyMapForBook = WordFrequencyHelper.getWordFrequency(storyBook);
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
                Word word = wordDao.readByText(locale, wordLowerCase);
                if (word != null) {
                    word.setUsageCount(wordFrequencyMap.get(wordLowerCase));
                    wordDao.update(word);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
