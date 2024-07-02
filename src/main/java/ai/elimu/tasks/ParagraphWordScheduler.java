package ai.elimu.tasks;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordExtractionHelper;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all {@link StoryBookParagraph}s and looks for {@link Word} matches in the paragraph's original text.
 */
@Service
public class ParagraphWordScheduler {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private StoryBooksJsonService storyBooksJsonService;
    
    @Scheduled(cron="00 00 * * * *") // Every hour
    public synchronized void execute() {
        logger.info("execute");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll();
        logger.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
            logger.info("storyBookParagraph.getId(): " + storyBookParagraph.getId());
            
            List<String> wordsInOriginalText = WordExtractionHelper.getWords(storyBookParagraph.getOriginalText(), language);
            logger.info("wordsInOriginalText.size(): " + wordsInOriginalText.size());
            
            // Look for matches of existing Words in the paragraph's original text
            List<Word> words = new ArrayList<>();
            for (String wordInOriginalText : wordsInOriginalText) {
                logger.info("wordInOriginalText: \"" + wordInOriginalText + "\"");
                wordInOriginalText = wordInOriginalText.toLowerCase();
                logger.info("wordInOriginalText (lower-case): \"" + wordInOriginalText + "\"");
                Word word = wordDao.readByText(wordInOriginalText);
                logger.info("word: " + word);
                words.add(word);
            }
            logger.info("words.size(): " + words.size());
            storyBookParagraph.setWords(words);
            
            // Update the paragraph's list of Words in the database
            storyBookParagraphDao.update(storyBookParagraph);
        }
        
        // Refresh REST API cache
        storyBooksJsonService.refreshStoryBooksJSONArray();
        
        logger.info("execute complete");
    }
}
