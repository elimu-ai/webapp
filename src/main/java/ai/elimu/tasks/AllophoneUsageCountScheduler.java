package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Word;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all Words and calculates the frequency of Allophones (speech sounds), based on the Word's frequency in 
 * StoryBooks.
 * <p />
 * For this to work, the frequency of each {@link Word} must have been calculated and stored previously 
 * (see {@link WordUsageCountScheduler} and {@link LetterSoundCorrespondenceUsageCountScheduler}).
 */
@Service
public class AllophoneUsageCountScheduler {
    
    private final Logger logger = LogManager.getLogger();
    
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

        // Summarize the usage count of each Word's Allophone based on the LetterSoundCorrespondence's 
        // usage count (see LetterSoundCorrespondenceUsageCountScheduler).
        List<Word> words = wordDao.readAllOrdered();
        logger.info("words.size(): " + words.size());
        for (Word word : words) {
            for (LetterSoundCorrespondence letterSoundCorrespondence : word.getLetterSoundCorrespondences()) {
                for (Allophone allophone : letterSoundCorrespondence.getAllophones()) {
                    allophoneFrequencyMap.put(allophone.getId(), allophoneFrequencyMap.getOrDefault(allophone.getId(), 0) + letterSoundCorrespondence.getUsageCount());
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
