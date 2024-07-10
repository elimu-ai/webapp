package ai.elimu.web.contributions;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.entity.contributor.AudioContributionEvent;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.entity.contributor.WordContributionEvent;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/contributions/most-recent")
public class MostRecentContributionsController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    @Autowired
    private NumberContributionEventDao numberContributionEventDao;
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readMostRecent(9);
        logger.info("storyBookContributionEvents.size(): " + storyBookContributionEvents.size());
        model.addAttribute("storyBookContributionEvents", storyBookContributionEvents);
        
        List<AudioContributionEvent> audioContributionEvents = audioContributionEventDao.readMostRecent(10);
        logger.info("audioContributionEvents.size(): " + audioContributionEvents.size());
        model.addAttribute("audioContributionEvents", audioContributionEvents);
        
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readMostRecent(10);
        logger.info("wordContributionEvents.size(): " + wordContributionEvents.size());
        model.addAttribute("wordContributionEvents", wordContributionEvents);
        
        List<NumberContributionEvent> numberContributionEvents = numberContributionEventDao.readMostRecent(10);
        logger.info("numberContributionEvents.size(): " + numberContributionEvents.size());
        model.addAttribute("numberContributionEvents", numberContributionEvents);
        
        
        List<Contributor> contributorsWithStoryBookContributions = contributorDao.readAllWithStoryBookContributions();
        logger.info("contributorsWithStoryBookContributions.size(): " + contributorsWithStoryBookContributions.size());
        model.addAttribute("contributorsWithStoryBookContributions", contributorsWithStoryBookContributions);
        Map<Long, Long> storyBookContributionsCountMap = new HashMap<>();
        for (Contributor contributor : contributorsWithStoryBookContributions) {
            storyBookContributionsCountMap.put(contributor.getId(), storyBookContributionEventDao.readCount(contributor));
        }
        model.addAttribute("storyBookContributionsCountMap", storyBookContributionsCountMap);
        
        List<Contributor> contributorsWithAudioContributions = contributorDao.readAllWithAudioContributions();
        logger.info("contributorsWithAudioContributions.size(): " + contributorsWithAudioContributions.size());
        model.addAttribute("contributorsWithAudioContributions", contributorsWithAudioContributions);
        Map<Long, Long> audioContributionsCountMap = new HashMap<>();
        for (Contributor contributor : contributorsWithAudioContributions) {
            audioContributionsCountMap.put(contributor.getId(), audioContributionEventDao.readCount(contributor));
        }
        model.addAttribute("audioContributionsCountMap", audioContributionsCountMap);
        
        List<Contributor> contributorsWithWordContributions = contributorDao.readAllWithWordContributions();
        logger.info("contributorsWithWordContributions.size(): " + contributorsWithWordContributions.size());
        model.addAttribute("contributorsWithWordContributions", contributorsWithWordContributions);
        Map<Long, Long> wordContributionsCountMap = new HashMap<>();
        for (Contributor contributor : contributorsWithWordContributions) {
            wordContributionsCountMap.put(contributor.getId(), wordContributionEventDao.readCount(contributor));
        }
        model.addAttribute("wordContributionsCountMap", wordContributionsCountMap);
        
        List<Contributor> contributorsWithNumberContributions = contributorDao.readAllWithNumberContributions();
        logger.info("contributorsWithNumberContributions.size(): " + contributorsWithNumberContributions.size());
        model.addAttribute("contributorsWithNumberContributions", contributorsWithNumberContributions);
        Map<Long, Long> numberContributionsCountMap = new HashMap<>();
        for (Contributor contributor : contributorsWithNumberContributions) {
            numberContributionsCountMap.put(contributor.getId(), numberContributionEventDao.readCount(contributor));
        }
        model.addAttribute("numberContributionsCountMap", numberContributionsCountMap);
        

        return "contributions/most-recent";
    }
}
