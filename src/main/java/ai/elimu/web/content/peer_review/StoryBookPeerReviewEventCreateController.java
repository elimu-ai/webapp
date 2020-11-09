package ai.elimu.web.content.peer_review;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.model.contributor.Contributor;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/storybook-peer-review-event/create")
public class StoryBookPeerReviewEventCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @RequestParam Long storyBookContributionEventId,
            @RequestParam Boolean approved,
            @RequestParam(required = false) String comment,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        logger.info("storyBookContributionEventId: " + storyBookContributionEventId);
        StoryBookContributionEvent storyBookContributionEvent = storyBookContributionEventDao.read(storyBookContributionEventId);
        logger.info("storyBookContributionEvent: " + storyBookContributionEvent);
            
        StoryBookPeerReviewEvent storyBookPeerReviewEvent = new StoryBookPeerReviewEvent();
        storyBookPeerReviewEvent.setContributor(contributor);
        storyBookPeerReviewEvent.setStoryBookContributionEvent(storyBookContributionEvent);
        storyBookPeerReviewEvent.setApproved(approved);
        storyBookPeerReviewEvent.setComment(comment);
        storyBookPeerReviewEvent.setTime(Calendar.getInstance());
        storyBookPeerReviewEventDao.create(storyBookPeerReviewEvent);

        // Update the storybook's metadata
        // TODO

        return "redirect:/content/storybook/edit/" + storyBookContributionEvent.getStoryBook().getId() + "#contribution-events";
    }
}
