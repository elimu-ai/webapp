package ai.elimu.web.content.word;

import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
public class WordListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<Word> words = wordDao.readAllOrderedByUsage(language);
        model.addAttribute("words", words);
        
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
