package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.Word;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ai.elimu.dao.LetterSoundCorrespondenceDao;

@Service
public class LetterSoundCorrespondenceUsageCountScheduler {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;

    @Autowired
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
    @Scheduled(cron="00 15 06 * * *") // At 06:15 every day
    public synchronized void execute() {
        logger.info("execute");
        
        logger.info("Calculating usage count for LetterSoundCorrespondences");

        // <id, usageCount>
        Map<Long, Integer> letterSoundCorrespondenceFrequencyMap = new HashMap<>();

        List<Word> words = wordDao.readAll();
        logger.info("words.size(): " + words.size());
        for (Word word : words) {
            logger.info("word.getText(): " + word.getText());
            for (LetterSoundCorrespondence letterSoundCorrespondence : word.getLetterSoundCorrespondences()) {
                letterSoundCorrespondenceFrequencyMap.put(letterSoundCorrespondence.getId(),
                        letterSoundCorrespondenceFrequencyMap.getOrDefault(letterSoundCorrespondence.getId(), 0) + word.getUsageCount());
            }
        }

        // Update the values previously stored in the database
        for (LetterSoundCorrespondence letterSoundCorrespondence : letterSoundCorrespondenceDao.readAll()) {
            logger.info("letterSoundCorrespondence.getId(): " + letterSoundCorrespondence.getId());
            logger.info("letterSoundCorrespondence Letters: \"" + letterSoundCorrespondence.getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"");
            logger.info("letterSoundCorrespondence Sounds: /" + letterSoundCorrespondence.getSounds().stream().map(Allophone::getValueIpa).collect(Collectors.joining()) + "/");
            logger.info("letterSoundCorrespondence.getUsageCount() (before update): " + letterSoundCorrespondence.getUsageCount());
            
            int newUsageCount = 0;
            if (letterSoundCorrespondenceFrequencyMap.containsKey(letterSoundCorrespondence.getId())) {
                newUsageCount = letterSoundCorrespondenceFrequencyMap.get(letterSoundCorrespondence.getId());
            }
            logger.info("newUsageCount: " + newUsageCount);
            
            letterSoundCorrespondence.setUsageCount(newUsageCount);
            letterSoundCorrespondenceDao.update(letterSoundCorrespondence);
            logger.info("letterSoundCorrespondence.getUsageCount() (after update): " + letterSoundCorrespondence.getUsageCount());
        }
        
        logger.info("execute complete");
    }
}
