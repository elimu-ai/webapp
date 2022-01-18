package ai.elimu.web.content.peer_review;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.contributor.Contributor;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.contributor.StoryBookPeerReviewEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    
    @Autowired
    private StoryBookDao storyBookDao;
    
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
        
        // Store the peer review event
        StoryBookPeerReviewEvent storyBookPeerReviewEvent = new StoryBookPeerReviewEvent();
        storyBookPeerReviewEvent.setContributor(contributor);
        storyBookPeerReviewEvent.setStoryBookContributionEvent(storyBookContributionEvent);
        storyBookPeerReviewEvent.setApproved(approved);
        storyBookPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
        storyBookPeerReviewEvent.setTime(Calendar.getInstance());
        storyBookPeerReviewEventDao.create(storyBookPeerReviewEvent);
        
        String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBookContributionEvent.getStoryBook().getId();
        DiscordHelper.postChatMessage(
                "Storybook peer-reviewed: " + contentUrl, 
                "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                "Comment: \"" + storyBookPeerReviewEvent.getComment() + "\"",
                storyBookPeerReviewEvent.isApproved()
        );

        // Update the storybook's peer review status
        int approvedCount = 0;
        int notApprovedCount = 0;
        for (StoryBookPeerReviewEvent peerReviewEvent : storyBookPeerReviewEventDao.readAll(storyBookContributionEvent)) {
            if (peerReviewEvent.isApproved()) {
                approvedCount++;
            } else {
                notApprovedCount++;
            }
        }
        logger.info("approvedCount: " + approvedCount);
        logger.info("notApprovedCount: " + notApprovedCount);
        StoryBook storyBook = storyBookContributionEvent.getStoryBook();
        if (approvedCount >= notApprovedCount) {
            storyBook.setPeerReviewStatus(PeerReviewStatus.APPROVED);
        } else {
            storyBook.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
        }
        storyBookDao.update(storyBook);

        return "redirect:/content/storybook/edit/" + storyBookContributionEvent.getStoryBook().getId() + "#contribution-events";
    }
}
