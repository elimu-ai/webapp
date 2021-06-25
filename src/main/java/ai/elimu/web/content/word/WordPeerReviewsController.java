package ai.elimu.web.content.word;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.contributor.WordPeerReviewEvent;
import ai.elimu.rest.v2.crowdsource.WordPeerReviewsRestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
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
    
    @Autowired
    private WordPeerReviewEventDao wordPeerReviewEventDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    /**
     * Get {@link WordContributionEvent}s pending a {@link WordPeerReviewEvent} for the current {@link Contributor}.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String handleGetRequest(HttpSession session, Model model) {
        logger.info("handleGetRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor: " + contributor);
        
        List<WordContributionEvent> allWordContributionEvents = wordContributionEventDao.readAllOrderedByTimeDesc();
        logger.info("allWordContributionEvents.size(): " + allWordContributionEvents.size());
        
        // Get the most recent WordContributionEvent for each Word
        List<WordContributionEvent> mostRecentWordContributionEvents = new ArrayList<>();
        HashMap<Long, Void> idsOfWordsWithContributionEventHashMap = new HashMap<>();
        for (WordContributionEvent wordContributionEvent : allWordContributionEvents) {
            // Ignore WordContributionEvent if one has already been added for this Word
            if (idsOfWordsWithContributionEventHashMap.containsKey(wordContributionEvent.getWord().getId())) {
                continue;
            }
            
            // Keep track of the Word ID
            idsOfWordsWithContributionEventHashMap.put(wordContributionEvent.getWord().getId(), null);
            
            // Ignore WordContributionEvents made by the current Contributor
            if (wordContributionEvent.getContributor().getId().equals(contributor.getId())) {
                continue;
            }
            
            mostRecentWordContributionEvents.add(wordContributionEvent);
        }
        logger.info("mostRecentWordContributionEvents.size(): " + mostRecentWordContributionEvents.size());
        
        // For each WordContributionEvent, check if the Contributor has already performed a peer review.
        // If not, add it to the list of pending peer reviews.
        List<WordContributionEvent> wordContributionEventsPendingPeerReview = new ArrayList<>();
        for (WordContributionEvent mostRecentWordContributionEvent : mostRecentWordContributionEvents) {
            List<WordPeerReviewEvent> wordPeerReviewEvents = wordPeerReviewEventDao.readAll(mostRecentWordContributionEvent, contributor);
            if (wordPeerReviewEvents.isEmpty()) {
                wordContributionEventsPendingPeerReview.add(mostRecentWordContributionEvent);
            }
        }
        logger.info("wordContributionEventsPendingPeerReview.size(): " + wordContributionEventsPendingPeerReview.size());
        model.addAttribute("wordContributionEventsPendingPeerReview", wordContributionEventsPendingPeerReview);
        
        model.addAttribute("emojisByWordId", getEmojisByWordId());
        
        return "content/word/peer-reviews/pending";
    }
    
    private Map<Long, String> getEmojisByWordId() {
        logger.info("getEmojisByWordId");
        
        Map<Long, String> emojisByWordId = new HashMap<>();
        
        for (Word word : wordDao.readAll()) {
            String emojiGlyphs = "";
            
            List<Emoji> emojis = emojiDao.readAllLabeled(word);
            for (Emoji emoji : emojis) {
                emojiGlyphs += emoji.getGlyph();
            }
            
            if (StringUtils.isNotBlank(emojiGlyphs)) {
                emojisByWordId.put(word.getId(), emojiGlyphs);
            }
        }
        
        return emojisByWordId;
    }
}
