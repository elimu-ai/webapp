package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all Words and calculates the frequency of Allophones (speech sounds), based on the Word's frequency in 
 * StoryBooks.
 * <p />
 * For this to work, the frequency of each {@link Word} must have been calculated and stored previously 
 * (see {@link WordUsageCountScheduler}
 */
@Service
public class AllophoneUsageCountScheduler {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;

    @Autowired
    private WordDao wordDao;
    
    @Scheduled(cron="00 30 06 * * *") // At 06:30 every day
    public synchronized void execute() {
        logger.info("execute");
        
        logger.info("Calculating usage count of Allophones");

        // Long = Allophone ID
        // Integer = Usage count
        Map<Long, Integer> allophoneFrequencyMap = new HashMap<>();

        // Summarize the usage count of each Word's Allophone based on the Word's usage count
        List<Word> words = wordDao.readAllOrdered();
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

        // Update each Allophone's usage count in the database
        for (Long allophoneId : allophoneFrequencyMap.keySet()) {
            Allophone allophone = allophoneDao.read(allophoneId);
            allophone.setUsageCount(allophoneFrequencyMap.get(allophoneId));
            allophoneDao.update(allophone);
        }
        
        logger.info("execute complete");
    }
}
