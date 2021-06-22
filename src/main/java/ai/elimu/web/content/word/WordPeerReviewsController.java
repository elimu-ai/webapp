package ai.elimu.web.content.word;

import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.rest.v2.crowdsource.WordPeerReviewsRestController;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * This controller has similar functionality as the {@link WordPeerReviewsRestController}.
 */
@Controller
@RequestMapping("/content/word/peer-reviews")
public class WordPeerReviewsController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    /**
     * Get {@link WordContributionEvent}s pending a {@link WordPeerReviewEvent} for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpSession session, Model model) {
        logger.info("handleGetRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor: " + contributor);
        
        List<WordContributionEvent> allWordContributionEvents = wordContributionEventDao.readAll();
        logger.info("allWordContributionEvents.size(): " + allWordContributionEvents.size());
        model.addAttribute("wordContributionEvents", allWordContributionEvents);
        
        // TODO
        
        return "content/word/peer-reviews/pending";
    }
}
