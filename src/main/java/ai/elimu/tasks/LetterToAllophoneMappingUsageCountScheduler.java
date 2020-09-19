package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.content.Word;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class LetterToAllophoneMappingUsageCountScheduler {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;

    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @Scheduled(cron="00 15 06 * * *") // At 06:15 every day
    public synchronized void execute() {
        logger.info("execute");
        
        logger.info("Calculating usage count for LetterToAllophoneMappings");

        // <id, usageCount>
        Map<Long, Integer> letterToAllophoneMappingFrequencyMap = new HashMap<>();

        List<Word> words = wordDao.readAll();
        logger.info("words.size(): " + words.size());
        for (Word word : words) {
            logger.info("word.getText(): " + word.getText());
            
            for (LetterToAllophoneMapping letterToAllophoneMapping : word.getLetterToAllophoneMappings()) {
                if (!letterToAllophoneMappingFrequencyMap.containsKey(letterToAllophoneMapping.getId())) {
                    letterToAllophoneMappingFrequencyMap.put(letterToAllophoneMapping.getId(), word.getUsageCount());
                } else {
                    letterToAllophoneMappingFrequencyMap.put(letterToAllophoneMapping.getId(), letterToAllophoneMappingFrequencyMap.get(letterToAllophoneMapping.getId()) + word.getUsageCount());
                }
            }
        }

        // Update the values previously stored in the database
        for (LetterToAllophoneMapping letterToAllophoneMapping : letterToAllophoneMappingDao.readAll()) {
            logger.info("letterToAllophoneMapping.getId(): " + letterToAllophoneMapping.getId());
            logger.info("letterToAllophoneMapping.getLetter().getText(): " + letterToAllophoneMapping.getLetter().getText());
            logger.info("letterToAllophoneMapping.getUsageCount() (before update): " + letterToAllophoneMapping.getUsageCount());
            
            int newUsageCount = 0;
            if (letterToAllophoneMappingFrequencyMap.containsKey(letterToAllophoneMapping.getId())) {
                newUsageCount = letterToAllophoneMappingFrequencyMap.get(letterToAllophoneMapping.getId());
            }
            logger.info("newUsageCount: " + newUsageCount);
            
            letterToAllophoneMapping.setUsageCount(newUsageCount);
            letterToAllophoneMappingDao.update(letterToAllophoneMapping);
            logger.info("letterToAllophoneMapping.getUsageCount() (after update): " + letterToAllophoneMapping.getUsageCount());
        }
        
        logger.info("execute complete");
    }
}
