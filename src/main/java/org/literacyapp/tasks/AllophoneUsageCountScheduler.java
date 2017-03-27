package org.literacyapp.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.util.PhoneticsHelper;
import org.literacyapp.util.WordFrequencyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all Words and calculates the frequency of speech sounds, based on 
 * the Word's frequency in StoryBooks.
 * </ p>
 * For this to work, the frequency of each Word must have been calculated and 
 * stored previously (see {@link WordUsageCountScheduler}
 */
@Service
public class AllophoneUsageCountScheduler {
    
    private Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;

    @Autowired
    private WordDao wordDao;
    
    @Scheduled(cron="00 30 06 * * *") // At 06:30 every day
    public synchronized void execute() {
        logger.info("execute");
        
        for (Locale locale : Locale.values()) {
            logger.info("Calculating usage count of Allophones for locale " + locale);
            
            Map<String, Integer> allophoneFrequencyMap = new HashMap<>();
            
            List<Allophone> allophones = allophoneDao.readAllOrdered(locale);
            logger.info("allophones.size(): " + allophones.size());
            
            List<Word> words = wordDao.readAllOrdered(locale);
            logger.info("words.size(): " + words.size());
            
            for (Word word : words) {
                List<String> allophonesInWord = PhoneticsHelper.getAllophones(word);
                for (String allophoneInWord : allophonesInWord) {
                    if (!allophoneFrequencyMap.containsKey(allophoneInWord)) {
                        allophoneFrequencyMap.put(allophoneInWord, word.getUsageCount());
                    } else {
                        allophoneFrequencyMap.put(allophoneInWord, allophoneFrequencyMap.get(allophoneInWord) + word.getUsageCount());
                    }
                }
            }
            
            for (String allophoneIpa : allophoneFrequencyMap.keySet()) {
                Allophone allophone = allophoneDao.readByValueIpa(locale, allophoneIpa);
                allophone.setUsageCount(allophoneFrequencyMap.get(allophoneIpa));
                allophoneDao.update(allophone);
            }
        }
        
        logger.info("execute complete");
    }
}
