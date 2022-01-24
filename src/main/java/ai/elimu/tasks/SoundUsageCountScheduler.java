package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Word;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ai.elimu.dao.SoundDao;

/**
 * Iterates all Words and calculates the frequency of Sounds, based on the Word's frequency in 
 * StoryBooks.
 * <p />
 * For this to work, the frequency of each {@link Word} must have been calculated and stored previously 
 * (see {@link WordUsageCountScheduler} and {@link LetterSoundCorrespondenceUsageCountScheduler}).
 */
@Service
public class SoundUsageCountScheduler {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private SoundDao soundDao;

    @Autowired
    private WordDao wordDao;
    
    @Scheduled(cron="00 30 06 * * *") // At 06:30 every day
    public synchronized void execute() {
        logger.info("execute");
        
        logger.info("Calculating usage count of Sounds");

        // Long = Sound ID
        // Integer = Usage count
        Map<Long, Integer> soundFrequencyMap = new HashMap<>();

        // Summarize the usage count of each Word's Sounds based on the LetterSoundCorrespondence's 
        // usage count (see LetterSoundCorrespondenceUsageCountScheduler).
        List<Word> words = wordDao.readAllOrdered();
        logger.info("words.size(): " + words.size());
        for (Word word : words) {
            for (LetterSoundCorrespondence letterSoundCorrespondence : word.getLetterSoundCorrespondences()) {
                for (Allophone sound : letterSoundCorrespondence.getSounds()) {
                    soundFrequencyMap.put(sound.getId(), soundFrequencyMap.getOrDefault(sound.getId(), 0) + letterSoundCorrespondence.getUsageCount());
                }
            }
        }
        // Update each Sound's usage count in the database
        for (Long soundId : soundFrequencyMap.keySet()) {
            Allophone sound = soundDao.read(soundId);
            sound.setUsageCount(soundFrequencyMap.get(soundId));
            soundDao.update(sound);
        }
        
        logger.info("execute complete");
    }
}
