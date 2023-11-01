package ai.elimu.web.content.peer_review;

import ai.elimu.dao.LetterSoundCorrespondenceContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
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
@RequestMapping("/content/letter-sound-correspondence-peer-review-event/create")
public class LetterSoundCorrespondencePeerReviewEventCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundCorrespondenceContributionEventDao letterSoundCorrespondenceContributionEventDao;
    
    @Autowired
    private LetterSoundPeerReviewEventDao letterSoundCorrespondencePeerReviewEventDao;
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @RequestParam Long letterSoundCorrespondenceContributionEventId,
            @RequestParam Boolean approved,
            @RequestParam(required = false) String comment,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        logger.info("letterSoundCorrespondenceContributionEventId: " + letterSoundCorrespondenceContributionEventId);
        LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent = letterSoundCorrespondenceContributionEventDao.read(letterSoundCorrespondenceContributionEventId);
        logger.info("letterSoundCorrespondenceContributionEvent: " + letterSoundCorrespondenceContributionEvent);
        
        // Store the peer review event
        LetterSoundCorrespondencePeerReviewEvent letterSoundCorrespondencePeerReviewEvent = new LetterSoundCorrespondencePeerReviewEvent();
        letterSoundCorrespondencePeerReviewEvent.setContributor(contributor);
        letterSoundCorrespondencePeerReviewEvent.setLetterSoundCorrespondenceContributionEvent(letterSoundCorrespondenceContributionEvent);
        letterSoundCorrespondencePeerReviewEvent.setApproved(approved);
        letterSoundCorrespondencePeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
        letterSoundCorrespondencePeerReviewEvent.setTime(Calendar.getInstance());
        letterSoundCorrespondencePeerReviewEvent.setPlatform(Platform.WEBAPP);
        letterSoundCorrespondencePeerReviewEventDao.create(letterSoundCorrespondencePeerReviewEvent);
        
        if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letterSoundCorrespondence/edit/" + letterSoundCorrespondenceContributionEvent.getLetterSoundCorrespondence().getId();
            DiscordHelper.sendChannelMessage(
                    "LetterSoundCorrespondence peer-reviewed: " + contentUrl, 
                    "\"" + letterSoundCorrespondenceContributionEvent.getLetterSoundCorrespondence().getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
                    "Comment: \"" + letterSoundCorrespondencePeerReviewEvent.getComment() + "\"",
                    letterSoundCorrespondencePeerReviewEvent.isApproved(),
                    null
            );
        }

        // Update the letterSoundCorrespondence's peer review status
        int approvedCount = 0;
        int notApprovedCount = 0;
        for (LetterSoundCorrespondencePeerReviewEvent peerReviewEvent : letterSoundCorrespondencePeerReviewEventDao.readAll(letterSoundCorrespondenceContributionEvent)) {
            if (peerReviewEvent.isApproved()) {
                approvedCount++;
            } else {
                notApprovedCount++;
            }
        }
        logger.info("approvedCount: " + approvedCount);
        logger.info("notApprovedCount: " + notApprovedCount);
        LetterSoundCorrespondence letterSoundCorrespondence = letterSoundCorrespondenceContributionEvent.getLetterSoundCorrespondence();
        if (approvedCount >= notApprovedCount) {
            letterSoundCorrespondence.setPeerReviewStatus(PeerReviewStatus.APPROVED);
        } else {
            letterSoundCorrespondence.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
        }
        letterSoundDao.update(letterSoundCorrespondence);

        return "redirect:/content/letter-sound/edit/" + letterSoundCorrespondenceContributionEvent.getLetterSoundCorrespondence().getId() + "#contribution-events";
    }
}
