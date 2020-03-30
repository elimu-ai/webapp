package ai.elimu.web.content.letter;

import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/list")
public class LetterListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<Letter> letters = letterDao.readAllOrderedByUsage(language);
        model.addAttribute("letters", letters);
        
        int maxUsageCount = 0;
        for (Letter letter : letters) {
            if (letter.getUsageCount() > maxUsageCount) {
                maxUsageCount = letter.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/letter/list";
    }
}
