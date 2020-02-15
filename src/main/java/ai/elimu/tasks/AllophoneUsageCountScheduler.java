package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all Words and calculates the frequency of speech sounds, based on 
 * the Word's frequency in StoryBooks.
 * <p />
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
        
        for (Language language : Language.values()) {
            logger.info("Calculating usage count of Allophones for language " + language);
            
            Map<Long, Integer> allophoneFrequencyMap = new HashMap<>();
            
            List<Allophone> allophones = allophoneDao.readAllOrdered(language);
            logger.info("allophones.size(): " + allophones.size());
            
            List<Word> words = wordDao.readAllOrdered(language);
            logger.info("words.size(): " + words.size());
            
            for (Word word : words) {
                for (Allophone allophone : word.getAllophones()) {
                    if (!allophoneFrequencyMap.containsKey(allophone.getId())) {
                        allophoneFrequencyMap.put(allophone.getId(), word.getUsageCount());
                    } else {
                        allophoneFrequencyMap.put(allophone.getId(), allophoneFrequencyMap.get(allophone.getId()) + word.getUsageCount());
                    }
                }
            }
            
            for (Long allophoneId : allophoneFrequencyMap.keySet()) {
                Allophone allophone = allophoneDao.read(allophoneId);
                allophone.setUsageCount(allophoneFrequencyMap.get(allophoneId));
                allophoneDao.update(allophone);
            }
        }
        
        logger.info("execute complete");
    }
}
