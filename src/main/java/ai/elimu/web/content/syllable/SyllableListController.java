package ai.elimu.web.content.syllable;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.SyllableDao;
import ai.elimu.model.content.Syllable;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/syllable/list")
public class SyllableListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private SyllableDao syllableDao;

    @GetMapping
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<Syllable> syllables = syllableDao.readAllOrderedByUsage();
        logger.info("syllables.size(): " + syllables.size());
        model.addAttribute("syllables", syllables);

        return "content/syllable/list";
    }
}
