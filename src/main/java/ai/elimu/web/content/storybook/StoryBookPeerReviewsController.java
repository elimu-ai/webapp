package ai.elimu.web.content.storybook;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/peer-reviews")
public class StoryBookPeerReviewsController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    /**
     * Get {@link StoryBookContributionEvent}s pending a {@link StoryBookPeerReviewEvent} for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpSession session, Model model) {
        logger.info("handleGetRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor: " + contributor);
        
        List<StoryBookContributionEvent> allStoryBookContributionEvents = storyBookContributionEventDao.readAll();
        logger.info("allStoryBookContributionEvents.size(): " + allStoryBookContributionEvents.size());
        
        // Get the most recent StoryBookContributionEvent for each StoryBook
        List<StoryBookContributionEvent> mostRecentStoryBookContributionEvents = new ArrayList<>();
        HashMap<Long, Void> idsOfStoryBooksWithContributionEventHashMap = new HashMap<>();
        for (StoryBookContributionEvent storyBookContributionEvent : allStoryBookContributionEvents) {
            // Ignore StoryBookContributionEvent if one has already been added for this StoryBook
            if (idsOfStoryBooksWithContributionEventHashMap.containsKey(storyBookContributionEvent.getStoryBook().getId())) {
                continue;
            }
            
            // Keep track of the StoryBook ID
            idsOfStoryBooksWithContributionEventHashMap.put(storyBookContributionEvent.getStoryBook().getId(), null);
            
            // Ignore StoryBookContributionEvents made by the current Contributor
            if (storyBookContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }
            
            mostRecentStoryBookContributionEvents.add(storyBookContributionEvent);
        }
        logger.info("mostRecentStoryBookContributionEvents.size(): " + mostRecentStoryBookContributionEvents.size());
        
        // For each StoryBookContributionEvent, check if the Contributor has already performed a peer review.
        // If not, add it to the list of pending peer reviews.
        List<StoryBookContributionEvent> storyBookContributionEventsPendingPeerReview = new ArrayList<>();
        for (StoryBookContributionEvent mostRecentStoryBookContributionEvent : mostRecentStoryBookContributionEvents) {
            StoryBookPeerReviewEvent storyBookPeerReviewEvent = storyBookPeerReviewEventDao.read(mostRecentStoryBookContributionEvent, contributor);
            if (storyBookPeerReviewEvent == null) {
                storyBookContributionEventsPendingPeerReview.add(mostRecentStoryBookContributionEvent);
            }
        }
        logger.info("storyBookContributionEventsPendingPeerReview.size(): " + storyBookContributionEventsPendingPeerReview.size());
        model.addAttribute("storyBookContributionEventsPendingPeerReview", storyBookContributionEventsPendingPeerReview);
        
        return "content/storybook/peer-reviews/pending";
    }
}
