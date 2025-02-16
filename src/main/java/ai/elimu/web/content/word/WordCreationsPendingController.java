package ai.elimu.web.content.word;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordFrequencyHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/word/pending")
public class WordCreationsPendingController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @GetMapping
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<String> paragraphs = new ArrayList<>();
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll()) {
            paragraphs.add(storyBookParagraph.getOriginalText());
        }
        logger.info("paragraphs.size(): " + paragraphs.size());
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, language);
        model.addAttribute("wordFrequencyMap", wordFrequencyMap);
        logger.info("wordFrequencyMap.size(): " + wordFrequencyMap.size());
        
        // Remove Words that have already been added
        Iterator<String> wordTextIterator = wordFrequencyMap.keySet().iterator();
        while (wordTextIterator.hasNext()) {
            String wordText = wordTextIterator.next();
            Word existingWord = wordDao.readByText(wordText);
            if (existingWord != null) {
                wordTextIterator.remove();
            }
        }
        
        int maxUsageCount = 0;
        for (Integer usageCount : wordFrequencyMap.values()) {
            if (usageCount > maxUsageCount) {
                maxUsageCount = usageCount;
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/word/pending";
    }
}
