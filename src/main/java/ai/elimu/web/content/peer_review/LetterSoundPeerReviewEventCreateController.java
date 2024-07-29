package ai.elimu.web.content.peer_review;

import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.LetterSoundPeerReviewEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/letter-sound-peer-review-event/create")
public class LetterSoundPeerReviewEventCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundContributionEventDao letterSoundContributionEventDao;
    
    @Autowired
    private LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @RequestParam Long letterSoundContributionEventId,
            @RequestParam Boolean approved,
            @RequestParam(required = false) String comment,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        logger.info("letterSoundContributionEventId: " + letterSoundContributionEventId);
        LetterSoundContributionEvent letterSoundContributionEvent = letterSoundContributionEventDao.read(letterSoundContributionEventId);
        logger.info("letterSoundContributionEvent: " + letterSoundContributionEvent);
        
        // Store the peer review event
        LetterSoundPeerReviewEvent letterSoundPeerReviewEvent = new LetterSoundPeerReviewEvent();
        letterSoundPeerReviewEvent.setContributor(contributor);
        letterSoundPeerReviewEvent.setLetterSoundContributionEvent(letterSoundContributionEvent);
        letterSoundPeerReviewEvent.setApproved(approved);
        letterSoundPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
        letterSoundPeerReviewEvent.setTimestamp(Calendar.getInstance());
        letterSoundPeerReviewEventDao.create(letterSoundPeerReviewEvent);
        
        if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter-sound/edit/" + letterSoundContributionEvent.getLetterSound().getId();
            DiscordHelper.sendChannelMessage(
                    "Letter-sound peer-reviewed: " + contentUrl,
                    "\"" + letterSoundContributionEvent.getLetterSound().getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
                    "Comment: \"" + letterSoundPeerReviewEvent.getComment() + "\"",
                    letterSoundPeerReviewEvent.isApproved(),
                    null
            );
        }

        // Update the peer review status
        int approvedCount = 0;
        int notApprovedCount = 0;
        for (LetterSoundPeerReviewEvent peerReviewEvent : letterSoundPeerReviewEventDao.readAll(letterSoundContributionEvent)) {
            if (peerReviewEvent.isApproved()) {
                approvedCount++;
            } else {
                notApprovedCount++;
            }
        }
        logger.info("approvedCount: " + approvedCount);
        logger.info("notApprovedCount: " + notApprovedCount);
        LetterSound letterSound = letterSoundContributionEvent.getLetterSound();
        updatePeerReviewStatus(letterSound, approvedCount, notApprovedCount);

        return "redirect:/content/letter-sound/edit/" + letterSoundContributionEvent.getLetterSound().getId() + "#contribution-events";
    }

    private void updatePeerReviewStatus(LetterSound letterSound, int approvedCount, int notApprovedCount) {
        if (approvedCount >= notApprovedCount) {
            letterSound.setPeerReviewStatus(PeerReviewStatus.APPROVED);
        } else {
            letterSound.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
        }
        letterSoundDao.update(letterSound);
    }
}
