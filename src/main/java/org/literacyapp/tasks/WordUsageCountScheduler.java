package org.literacyapp.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.util.WordFrequencyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each word.
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
                    String wordLowerCase = key.toLowerCase();
                    if (!wordFrequencyMap.containsKey(wordLowerCase)) {
                        wordFrequencyMap.put(key, 1);
                    } else {
                        wordFrequencyMap.put(key, wordFrequencyMap.get(wordLowerCase) + 1);
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
