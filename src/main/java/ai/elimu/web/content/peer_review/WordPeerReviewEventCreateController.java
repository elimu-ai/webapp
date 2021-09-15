package ai.elimu.web.content.peer_review;

import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.contributor.WordPeerReviewEvent;
import ai.elimu.model.content.Content.PeerReviewStatus;
import ai.elimu.util.SlackHelper;
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
@RequestMapping("/content/word-peer-review-event/create")
public class WordPeerReviewEventCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    @Autowired
    private WordPeerReviewEventDao wordPeerReviewEventDao;
    
    @Autowired
    private WordDao wordDao;
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @RequestParam Long wordContributionEventId,
            @RequestParam Boolean approved,
            @RequestParam(required = false) String comment,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        logger.info("wordContributionEventId: " + wordContributionEventId);
        WordContributionEvent wordContributionEvent = wordContributionEventDao.read(wordContributionEventId);
        logger.info("wordContributionEvent: " + wordContributionEvent);
        
        // Store the peer review event
        WordPeerReviewEvent wordPeerReviewEvent = new WordPeerReviewEvent();
        wordPeerReviewEvent.setContributor(contributor);
        wordPeerReviewEvent.setWordContributionEvent(wordContributionEvent);
        wordPeerReviewEvent.setApproved(approved);
        wordPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
        wordPeerReviewEvent.setTime(Calendar.getInstance());
        wordPeerReviewEventDao.create(wordPeerReviewEvent);
        
        String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + wordContributionEvent.getWord().getId();
        SlackHelper.postChatMessage("Word peer-reviewed: " + contentUrl);

        // Update the word's peer review status
        int approvedCount = 0;
        int notApprovedCount = 0;
        for (WordPeerReviewEvent peerReviewEvent : wordPeerReviewEventDao.readAll(wordContributionEvent)) {
            if (peerReviewEvent.isApproved()) {
                approvedCount++;
            } else {
                notApprovedCount++;
            }
        }
        logger.info("approvedCount: " + approvedCount);
        logger.info("notApprovedCount: " + notApprovedCount);
        Word word = wordContributionEvent.getWord();
        if (approvedCount >= notApprovedCount) {
            word.setPeerReviewStatus(PeerReviewStatus.APPROVED);
        } else {
            word.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
        }
        wordDao.update(word);

        return "redirect:/content/word/edit/" + wordContributionEvent.getWord().getId() + "#contribution-events";
    }
}
