package ai.elimu.web.content.syllable;

import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.SyllableDao;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/syllable/list")
public class SyllableListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private SyllableDao syllableDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<Syllable> syllables = syllableDao.readAllOrderedByUsage(language);
        logger.info("syllables.size(): " + syllables.size());
        model.addAttribute("syllables", syllables);

        return "content/syllable/list";
    }
}
