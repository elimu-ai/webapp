package ai.elimu.web.analytics;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.model.analytics.StoryBookLearningEvent;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/storybook-learning-event/list")
public class StoryBookLearningEventListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookLearningEventDao storyBookLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAllOrderedByTime();
        model.addAttribute("storyBookLearningEvents", storyBookLearningEvents);

        return "analytics/storybook-learning-event/list";
    }
}
