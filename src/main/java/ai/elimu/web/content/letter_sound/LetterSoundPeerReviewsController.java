package ai.elimu.web.content.letter_sound;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.entity.contributor.LetterSoundCorrespondencePeerReviewEvent;
import java.util.ArrayList;
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
@RequestMapping("/content/letter-sound/peer-reviews")
public class LetterSoundPeerReviewsController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundContributionEventDao letterSoundContributionEventDao;
    
    @Autowired
    private LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    /**
     * Get {@link LetterSoundCorrespondenceContributionEvent}s pending a {@link LetterSoundCorrespondencePeerReviewEvent} for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpSession session, Model model) {
        logger.info("handleGetRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor: " + contributor);
        
        // Get the most recent LetterSoundCorrespondenceContributionEvent for each LetterSound, including those made by the current Contributor
        List<LetterSoundCorrespondenceContributionEvent> mostRecentLetterSoundContributionEvents = letterSoundContributionEventDao.readMostRecentPerLetterSound();
        logger.info("mostRecentLetterSoundContributionEvents.size(): " + mostRecentLetterSoundContributionEvents.size());
        
        // For each LetterSoundCorrespondenceContributionEvent, check if the Contributor has already performed a peer-review.
        // If not, add it to the list of pending peer reviews.
        List<LetterSoundCorrespondenceContributionEvent> letterSoundContributionEventsPendingPeerReview = new ArrayList<>();
        for (LetterSoundCorrespondenceContributionEvent mostRecentLetterSoundContributionEvent : mostRecentLetterSoundContributionEvents) {
            // Ignore LetterSoundCorrespondenceContributionEvents made by the current Contributor
            if (mostRecentLetterSoundContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }
            
            // Check if the current Contributor has already peer-reviewed this LetterSoundCorrespondence contribution
            List<LetterSoundCorrespondencePeerReviewEvent> letterSoundPeerReviewEvents = letterSoundPeerReviewEventDao.readAll(mostRecentLetterSoundContributionEvent, contributor);
            if (letterSoundPeerReviewEvents.isEmpty()) {
                letterSoundContributionEventsPendingPeerReview.add(mostRecentLetterSoundContributionEvent);
            }
        }
        logger.info("letterSoundContributionEventsPendingPeerReview.size(): " + letterSoundContributionEventsPendingPeerReview.size());
        model.addAttribute("letterSoundContributionEventsPendingPeerReview", letterSoundContributionEventsPendingPeerReview);
        
        return "content/letter-sound/peer-reviews/pending";
    }
}
