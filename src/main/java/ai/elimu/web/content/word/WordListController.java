package ai.elimu.web.content.word;

import java.util.List;

import ai.elimu.web.content.emoji.EmojiComponent;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
public class WordListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiComponent emojiComponent;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<Word> words = wordDao.readAllOrderedByUsage();
        model.addAttribute("words", words);
        model.addAttribute("emojisByWordId", emojiComponent.getEmojisByWordId());
        
        int maxUsageCount = 0;
        for (Word word : words) {
            if (word.getUsageCount() > maxUsageCount) {
                maxUsageCount = word.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/word/list";
    }

}
