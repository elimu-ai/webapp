package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Word;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ai.elimu.dao.LetterSoundDao;

@Service
public class LetterSoundUsageCountScheduler {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;

    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @Scheduled(cron="00 15 06 * * *") // At 06:15 every day
    public synchronized void execute() {
        logger.info("execute");
        
        logger.info("Calculating usage count for LetterSounds");

        // <id, usageCount>
        Map<Long, Integer> letterSoundFrequencyMap = new HashMap<>();

        List<Word> words = wordDao.readAll();
        logger.info("words.size(): " + words.size());
        for (Word word : words) {
            logger.info("word.getText(): " + word.getText());
            for (LetterSound letterSound : word.getLetterSounds()) {
                letterSoundFrequencyMap.put(letterSound.getId(),
                        letterSoundFrequencyMap.getOrDefault(letterSound.getId(), 0) + word.getUsageCount());
            }
        }

        // Update the values previously stored in the database
        for (LetterSound letterSound : letterSoundDao.readAll()) {
            logger.info("letterSound.getId(): " + letterSound.getId());
            logger.info("letterSound Letters: \"" + letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"");
            logger.info("letterSound Sounds: /" + letterSound.getSounds().stream().map(Sound::getValueIpa).collect(Collectors.joining()) + "/");
            logger.info("letterSound.getUsageCount() (before update): " + letterSound.getUsageCount());
            
            int newUsageCount = 0;
            if (letterSoundFrequencyMap.containsKey(letterSound.getId())) {
                newUsageCount = letterSoundFrequencyMap.get(letterSound.getId());
            }
            logger.info("newUsageCount: " + newUsageCount);

            letterSound.setUsageCount(newUsageCount);
            letterSoundDao.update(letterSound);
            logger.info("letterSound.getUsageCount() (after update): " + letterSound.getUsageCount());
        }
        
        logger.info("execute complete");
    }
}
