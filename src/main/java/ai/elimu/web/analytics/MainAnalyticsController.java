package ai.elimu.web.analytics;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.WordLearningEventDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics")
public class MainAnalyticsController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookLearningEventDao storyBookLearningEventDao;
    
    @Autowired
    private WordLearningEventDao wordLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        model.addAttribute("storyBookLearningEventCount", storyBookLearningEventDao.readCount());
        model.addAttribute("wordLearningEventCount", wordLearningEventDao.readCount());
    	
        return "analytics/main";
    }
}
