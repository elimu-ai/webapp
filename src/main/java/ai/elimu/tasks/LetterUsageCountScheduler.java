package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.enums.Language;
import ai.elimu.util.LetterFrequencyHelper;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each letter. Lower-case 
 * and upper-case variants are considered as two different letters, e.g. 'a' and 'A'.
 */
@Service
public class LetterUsageCountScheduler {
    
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Scheduled(cron="00 15 06 * * *") // At 06:15 every day
    public synchronized void execute() {
        logger.info("execute");
        
        for (Language language : Language.values()) {
            logger.info("Calculating usage count for Letters with language " + language);
            
            Map<String, Integer> letterFrequencyMap = new HashMap<>();
            
            List<StoryBook> storyBooks = storyBookDao.readAllOrdered(language);
            logger.info("storyBooks.size(): " + storyBooks.size());
            for (StoryBook storyBook : storyBooks) {
                logger.info("storyBook.getTitle(): " + storyBook.getTitle());
                
                List<String> paragraphs = new ArrayList<>();
                List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
                for (StoryBookChapter storyBookChapter : storyBookChapters) {
                    List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
                    for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
                        paragraphs.add(storyBookParagraph.getOriginalText());
                    }
                }
                
                Map<String, Integer> letterFrequencyMapForBook = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
                for (String key : letterFrequencyMapForBook.keySet()) {
                    String letterText = key;
                    int letterFrequency = letterFrequencyMapForBook.get(key);
                    if (!letterFrequencyMap.containsKey(letterText)) {
                        letterFrequencyMap.put(letterText, letterFrequency);
                    } else {
                        letterFrequencyMap.put(letterText, letterFrequencyMap.get(letterText) + letterFrequency);
                    }
                }
            }
            
            logger.info("letterFrequencyMap: " + letterFrequencyMap);
            
            for (String key : letterFrequencyMap.keySet()) {
                String letterText = key;
                Letter existingLetter = letterDao.readByText(language, letterText);
                if (existingLetter != null) {
                    existingLetter.setUsageCount(letterFrequencyMap.get(letterText));
                    letterDao.update(existingLetter);
                }
            }
        }
        
        logger.info("execute complete");
    }
}
