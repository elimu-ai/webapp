package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Locale;
import ai.elimu.util.PhoneticsHelper;
import ai.elimu.util.WordFrequencyHelper;
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
