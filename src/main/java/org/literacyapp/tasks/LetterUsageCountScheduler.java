package org.literacyapp.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.util.LetterFrequencyHelper;
import org.literacyapp.util.WordFrequencyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each letter.
 */
@Service
public class LetterUsageCountScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
//    @Scheduled(cron="00 15 06 * * *") // At 06:15 every day
    @Scheduled(cron="00 * * * * *")
    public synchronized void execute() {
        logger.info("execute");
        
        for (Locale locale : Locale.values()) {
            logger.info("Calculating usage count for Letters with locale " + locale);
            
            Map<String, Integer> letterFrequencyMap = new HashMap<>();
            
            List<StoryBook> storyBooks = storyBookDao.readAllOrdered(locale);
            logger.info("storyBooks.size(): " + storyBooks.size());
            for (StoryBook storyBook : storyBooks) {
                logger.info("storyBook.getTitle(): " + storyBook.getTitle());
                
                Map<String, Integer> letterFrequencyMapForBook = LetterFrequencyHelper.getLetterFrequency(storyBook);
                for (String key : letterFrequencyMapForBook.keySet()) {
                    int letterFrequency = letterFrequencyMapForBook.get(key);
                    String letterLowerCase = key.toLowerCase();
                    if (!letterFrequencyMap.containsKey(letterLowerCase)) {
                        letterFrequencyMap.put(letterLowerCase, letterFrequency);
                    } else {
                        letterFrequencyMap.put(letterLowerCase, letterFrequencyMap.get(letterLowerCase) + letterFrequency);
                    }
                }
            }
            
            logger.info("letterFrequencyMap: " + letterFrequencyMap);
            
            for (String key : letterFrequencyMap.keySet()) {
                String letterLowerCase = key.toLowerCase();
                Letter letter = letterDao.readByText(locale, letterLowerCase);
                if (letter != null) {
                    letter.setUsageCount(letterFrequencyMap.get(letterLowerCase));
                    letterDao.update(letter);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
