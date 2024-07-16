package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.WordLearningEventDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics")
public class MainAnalyticsController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterLearningEventDao letterLearningEventDao;
    
    @Autowired
    private WordLearningEventDao wordLearningEventDao;
    
    @Autowired
    private StoryBookLearningEventDao storyBookLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        model.addAttribute("letterLearningEventCount", letterLearningEventDao.readCount());
        model.addAttribute("wordLearningEventCount", wordLearningEventDao.readCount());
        model.addAttribute("storyBookLearningEventCount", storyBookLearningEventDao.readCount());
        
        return "analytics/main";
    }
}
