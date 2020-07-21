package ai.elimu.web.contributions;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.model.contributor.WordContributionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/contributions/most-recent")
public class MostRecentContributionsController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readMostRecent(9);
        logger.info("storyBookContributionEvents.size(): " + storyBookContributionEvents.size());
        model.addAttribute("storyBookContributionEvents", storyBookContributionEvents);
        
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        model.addAttribute("wordContributionEvents", wordContributionEvents);

        return "contributions/most-recent";
    }
}
