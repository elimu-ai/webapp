package ai.elimu.web.content.contributor;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.NumberPeerReviewEvent;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;
import ai.elimu.entity.contributor.WordContributionEvent;
import ai.elimu.entity.contributor.WordPeerReviewEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/contributor/{contributorId}")
public class ContributorController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    @Autowired
    private AudioPeerReviewEventDao audioPeerReviewEventDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    @Autowired
    private WordPeerReviewEventDao wordPeerReviewEventDao;
    
    @Autowired
    private NumberContributionEventDao numberContributionEventDao;
    
    @Autowired
    private NumberPeerReviewEventDao numberPeerReviewEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long contributorId,
            Model model
    ) {
    	logger.info("handleRequest");
        
        Contributor contributor = contributorDao.read(contributorId);
        model.addAttribute("contributor2", contributor);
        
        // For contributor-summarized.jsp
        model.addAttribute("storyBookContributionsCount", storyBookContributionEventDao.readCount(contributor));
        model.addAttribute("storyBookPeerReviewsCount", storyBookPeerReviewEventDao.readCount(contributor));
        model.addAttribute("audioContributionsCount", audioContributionEventDao.readCount(contributor));
        model.addAttribute("audioPeerReviewsCount", audioPeerReviewEventDao.readCount(contributor));
        model.addAttribute("wordContributionsCount", wordContributionEventDao.readCount(contributor));
        model.addAttribute("wordPeerReviewsCount", wordPeerReviewEventDao.readCount(contributor));
        model.addAttribute("numberContributionsCount", numberContributionEventDao.readCount(contributor));
        model.addAttribute("numberPeerReviewsCount", numberPeerReviewEventDao.readCount(contributor));
        
        // For contributor-storybooks.jsp
        List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readAll(contributor);
        model.addAttribute("storyBookContributionEvents", storyBookContributionEvents);
        model.addAttribute("storyBookPeerReviewEvents", storyBookPeerReviewEventDao.readAll(contributor));
        Map<Long, List<StoryBookPeerReviewEvent>> storyBookPeerReviewEventsByContributionMap = new HashMap<>();
        for (StoryBookContributionEvent storyBookContributionEvent : storyBookContributionEvents) {
            storyBookPeerReviewEventsByContributionMap.put(storyBookContributionEvent.getId(), storyBookPeerReviewEventDao.readAll(storyBookContributionEvent));
        }
        model.addAttribute("storyBookPeerReviewEventsByContributionMap", storyBookPeerReviewEventsByContributionMap);
        
        // For contributor-words.jsp
        List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(contributor);
        model.addAttribute("wordContributionEvents", wordContributionEvents);
        model.addAttribute("wordPeerReviewEvents", wordPeerReviewEventDao.readAll(contributor));
        Map<Long, List<WordPeerReviewEvent>> wordPeerReviewEventsByContributionMap = new HashMap<>();
        for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
            wordPeerReviewEventsByContributionMap.put(wordContributionEvent.getId(), wordPeerReviewEventDao.readAll(wordContributionEvent));
        }
        model.addAttribute("wordPeerReviewEventsByContributionMap", wordPeerReviewEventsByContributionMap);
        
        // For contributor-numbers.jsp
        List<NumberContributionEvent> numberContributionEvents = numberContributionEventDao.readAll(contributor);
        model.addAttribute("numberContributionEvents", numberContributionEvents);
        model.addAttribute("numberPeerReviewEvents", numberPeerReviewEventDao.readAll(contributor));
        Map<Long, List<NumberPeerReviewEvent>> numberPeerReviewEventsByContributionMap = new HashMap<>();
        for (NumberContributionEvent numberContributionEvent : numberContributionEvents) {
            numberPeerReviewEventsByContributionMap.put(numberContributionEvent.getId(), numberPeerReviewEventDao.readAll(numberContributionEvent));
        }
        model.addAttribute("numberPeerReviewEventsByContributionMap", numberPeerReviewEventsByContributionMap);
        
        return "content/contributor/contributor";
    }
}
