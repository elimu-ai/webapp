package ai.elimu.web.content.peer_review;

import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.contributor.Contributor;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.contributor.LetterSoundCorrespondencePeerReviewEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.enums.Platform;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
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
        LetterSoundCorrespondenceContributionEvent letterSoundContributionEvent = letterSoundContributionEventDao.read(letterSoundContributionEventId);
        logger.info("letterSoundContributionEvent: " + letterSoundContributionEvent);
        
        // Store the peer review event
        LetterSoundCorrespondencePeerReviewEvent letterSoundPeerReviewEvent = new LetterSoundCorrespondencePeerReviewEvent();
        letterSoundPeerReviewEvent.setContributor(contributor);
        letterSoundPeerReviewEvent.setLetterSoundCorrespondenceContributionEvent(letterSoundContributionEvent);
        letterSoundPeerReviewEvent.setApproved(approved);
        letterSoundPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
        letterSoundPeerReviewEvent.setTime(Calendar.getInstance());
        letterSoundPeerReviewEvent.setPlatform(Platform.WEBAPP);
        letterSoundPeerReviewEventDao.create(letterSoundPeerReviewEvent);
        
        if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letterSoundCorrespondence/edit/" + letterSoundContributionEvent.getLetterSoundCorrespondence().getId();
            DiscordHelper.sendChannelMessage(
                    "Letter-sound correspondence peer-reviewed: " + contentUrl, 
                    "\"" + letterSoundContributionEvent.getLetterSoundCorrespondence().getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
                    "Comment: \"" + letterSoundPeerReviewEvent.getComment() + "\"",
                    letterSoundPeerReviewEvent.isApproved(),
                    null
            );
        }

        // Update the peer review status
        int approvedCount = 0;
        int notApprovedCount = 0;
        for (LetterSoundCorrespondencePeerReviewEvent peerReviewEvent : letterSoundPeerReviewEventDao.readAll(letterSoundContributionEvent)) {
            if (peerReviewEvent.isApproved()) {
                approvedCount++;
            } else {
                notApprovedCount++;
            }
        }
        logger.info("approvedCount: " + approvedCount);
        logger.info("notApprovedCount: " + notApprovedCount);
        LetterSound letterSound = letterSoundContributionEvent.getLetterSoundCorrespondence();
        if (approvedCount >= notApprovedCount) {
            letterSound.setPeerReviewStatus(PeerReviewStatus.APPROVED);
        } else {
            letterSound.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
        }
        letterSoundDao.update(letterSound);

        return "redirect:/content/letter-sound/edit/" + letterSoundContributionEvent.getLetterSoundCorrespondence().getId() + "#contribution-events";
    }
}
