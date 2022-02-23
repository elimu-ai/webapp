package ai.elimu.web.content.letter_sound_correspondence;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterSoundCorrespondenceContributionEventDao;
import ai.elimu.dao.LetterSoundCorrespondenceDao;
import ai.elimu.dao.LetterSoundCorrespondencePeerReviewEventDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.contributor.LetterSoundCorrespondencePeerReviewEvent;
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
@RequestMapping("/content/letter-sound-correspondence/peer-reviews")
public class LetterSoundCorrespondencePeerReviewsController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundCorrespondenceContributionEventDao letterSoundCorrespondenceContributionEventDao;
    
    @Autowired
    private LetterSoundCorrespondencePeerReviewEventDao letterSoundCorrespondencePeerReviewEventDao;
    
    @Autowired
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
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
        
        // Get the most recent LetterSoundCorrespondenceContributionEvent for each LetterSoundCorrespondence, including those made by the current Contributor
        List<LetterSoundCorrespondenceContributionEvent> mostRecentLetterSoundCorrespondenceContributionEvents = letterSoundCorrespondenceContributionEventDao.readMostRecentPerLetterSoundCorrespondence();
        logger.info("mostRecentLetterSoundCorrespondenceContributionEvents.size(): " + mostRecentLetterSoundCorrespondenceContributionEvents.size());
        
        // For each LetterSoundCorrespondenceContributionEvent, check if the Contributor has already performed a peer-review.
        // If not, add it to the list of pending peer reviews.
        List<LetterSoundCorrespondenceContributionEvent> letterSoundCorrespondenceContributionEventsPendingPeerReview = new ArrayList<>();
        for (LetterSoundCorrespondenceContributionEvent mostRecentLetterSoundCorrespondenceContributionEvent : mostRecentLetterSoundCorrespondenceContributionEvents) {
            // Ignore LetterSoundCorrespondenceContributionEvents made by the current Contributor
            if (mostRecentLetterSoundCorrespondenceContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }
            
            // Check if the current Contributor has already peer-reviewed this LetterSoundCorrespondence contribution
            List<LetterSoundCorrespondencePeerReviewEvent> letterSoundCorrespondencePeerReviewEvents = letterSoundCorrespondencePeerReviewEventDao.readAll(mostRecentLetterSoundCorrespondenceContributionEvent, contributor);
            if (letterSoundCorrespondencePeerReviewEvents.isEmpty()) {
                letterSoundCorrespondenceContributionEventsPendingPeerReview.add(mostRecentLetterSoundCorrespondenceContributionEvent);
            }
        }
        logger.info("letterSoundCorrespondenceContributionEventsPendingPeerReview.size(): " + letterSoundCorrespondenceContributionEventsPendingPeerReview.size());
        model.addAttribute("letterSoundCorrespondenceContributionEventsPendingPeerReview", letterSoundCorrespondenceContributionEventsPendingPeerReview);
        
        return "content/letter-sound-correspondence/peer-reviews/pending";
    }
}
