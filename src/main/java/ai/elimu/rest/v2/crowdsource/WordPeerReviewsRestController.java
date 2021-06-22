package ai.elimu.rest.v2.crowdsource;

import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.web.content.word.WordPeerReviewsController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API for the Crowdsource application: https://github.com/elimu-ai/crowdsource
 * <p>
 * 
 * This controller has similar functionality as the {@link WordPeerReviewsController}.
 */
@RestController
@RequestMapping(value = "/rest/v2/crowdsource/word-peer-reviews", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class WordPeerReviewsRestController {
    
    private Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    /**
     * Get {@link WordContributionEvent}s pending a {@link WordPeerReviewEvent} for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest() {
        logger.info("handleGetRequest");
        
        // TODO
        return null;
    }
}
