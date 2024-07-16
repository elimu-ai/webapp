package ai.elimu.web.analytics;

import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.model.analytics.WordLearningEvent;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/word-learning-event/list")
public class WordLearningEventListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordLearningEventDao wordLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.readAll();
        model.addAttribute("wordLearningEvents", wordLearningEvents);

        return "analytics/word-learning-event/list";
    }
}
