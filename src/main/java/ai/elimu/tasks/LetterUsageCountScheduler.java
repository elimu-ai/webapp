package ai.elimu.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.LetterFrequencyHelper;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all StoryBooks and calculates the frequency of each letter. Lower-case 
 * and upper-case variants are considered as two different letters, e.g. 'a' and 'A'.
 */
@Service
public class LetterUsageCountScheduler {
    
    private Logger logger = LogManager.getLogger();

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
        
        logger.info("Calculating usage count for Letters");

        Map<String, Integer> letterFrequencyMap = new HashMap<>();
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));

        List<StoryBook> storyBooks = storyBookDao.readAllOrdered();
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
            letterFrequencyMapForBook.keySet().forEach(letterText -> letterFrequencyMap.put(letterText, letterFrequencyMap.getOrDefault(letterText, 0) + letterFrequencyMapForBook.get(letterText)));
        }

        logger.info("letterFrequencyMap: " + letterFrequencyMap);

        for (String letterText : letterFrequencyMap.keySet()) {
            Letter existingLetter = letterDao.readByText(letterText);
            if (existingLetter != null) {
                existingLetter.setUsageCount(letterFrequencyMap.get(letterText));
                letterDao.update(existingLetter);
            }
        }
        
        logger.info("execute complete");
    }
}
