package ai.elimu.web.content.storybook;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping
    public String handleGetRequest(HttpSession session, Model model) {
        logger.info("handleGetRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor: " + contributor);
        
        List<StoryBookContributionEvent> allStoryBookContributionEvents = storyBookContributionEventDao.readAllOrderedByTimeDesc();
        logger.info("allStoryBookContributionEvents.size(): " + allStoryBookContributionEvents.size());
        
        // Get the most recent StoryBookContributionEvent for each StoryBook, including those made by the current Contributor
        List<StoryBookContributionEvent> mostRecentStoryBookContributionEvents = storyBookContributionEventDao.readMostRecentPerStoryBook();
        logger.info("mostRecentStoryBookContributionEvents.size(): " + mostRecentStoryBookContributionEvents.size());
        
        // For each StoryBookContributionEvent, check if the Contributor has already performed a peer-review.
        // If not, add it to the list of pending peer reviews.
        List<StoryBookContributionEvent> storyBookContributionEventsPendingPeerReview = new ArrayList<>();
        for (StoryBookContributionEvent mostRecentStoryBookContributionEvent : mostRecentStoryBookContributionEvents) {
            // Ignore StoryBookContributionEvents made by the current Contributor
            if (mostRecentStoryBookContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }
            
            // Check if the current Contributor has already peer-reviewed this StoryBook contribution
            List<StoryBookPeerReviewEvent> storyBookPeerReviewEvents = storyBookPeerReviewEventDao.readAll(mostRecentStoryBookContributionEvent, contributor);
            if (storyBookPeerReviewEvents.isEmpty()) {
                storyBookContributionEventsPendingPeerReview.add(mostRecentStoryBookContributionEvent);
            }
        }
        logger.info("storyBookContributionEventsPendingPeerReview.size(): " + storyBookContributionEventsPendingPeerReview.size());
        model.addAttribute("storyBookContributionEventsPendingPeerReview", storyBookContributionEventsPendingPeerReview);
        
        return "content/storybook/peer-reviews/pending";
    }
}
