package ai.elimu.web.content.peer_review;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.Contributor;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
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
@RequestMapping("/content/audio-peer-review-event/create")
public class AudioPeerReviewEventCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    @Autowired
    private AudioPeerReviewEventDao audioPeerReviewEventDao;
    
    @Autowired
    private AudioDao audioDao;
    
    /**
     * Note: The logic in this method is similar to the one used at {@link AudioPeerReviewsRestController#uploadAudioPeerReview}
     */
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @RequestParam Long audioContributionEventId,
            @RequestParam Boolean approved,
            @RequestParam(required = false) String comment,
            HttpSession session
    ) {
        logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        logger.info("audioContributionEventId: " + audioContributionEventId);
        AudioContributionEvent audioContributionEvent = audioContributionEventDao.read(audioContributionEventId);
        logger.info("audioContributionEvent: " + audioContributionEvent);
        
        // Store the peer review event
        AudioPeerReviewEvent audioPeerReviewEvent = new AudioPeerReviewEvent();
        audioPeerReviewEvent.setContributor(contributor);
        audioPeerReviewEvent.setAudioContributionEvent(audioContributionEvent);
        audioPeerReviewEvent.setApproved(approved);
        audioPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
        audioPeerReviewEvent.setTimestamp(Calendar.getInstance());
        audioPeerReviewEventDao.create(audioPeerReviewEvent);
        
        if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/audio/edit/" + audioContributionEvent.getAudio().getId();
            DiscordHelper.sendChannelMessage(
                    "Audio peer-reviewed: " + contentUrl, 
                    "\"" + audioContributionEvent.getAudio().getTitle() + "\"",
                    "Comment: \"" + audioPeerReviewEvent.getComment() + "\"",
                    audioPeerReviewEvent.isApproved(),
                    null
            );
        }

        // Update the audio's peer review status
        int approvedCount = 0;
        int notApprovedCount = 0;
        for (AudioPeerReviewEvent peerReviewEvent : audioPeerReviewEventDao.readAll(audioContributionEvent)) {
            if (peerReviewEvent.isApproved()) {
                approvedCount++;
            } else {
                notApprovedCount++;
            }
        }
        logger.info("approvedCount: " + approvedCount);
        logger.info("notApprovedCount: " + notApprovedCount);
        Audio audio = audioContributionEvent.getAudio();
        if (approvedCount >= notApprovedCount) {
            audio.setPeerReviewStatus(PeerReviewStatus.APPROVED);
        } else {
            audio.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
        }
        audioDao.update(audio);

        return "redirect:/content/multimedia/audio/edit/" + audioContributionEvent.getAudio().getId() + "#contribution-events";
    }
}
