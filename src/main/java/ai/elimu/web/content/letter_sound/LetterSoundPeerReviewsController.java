package ai.elimu.web.content.letter_sound;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundContributionEvent;
import ai.elimu.model.contributor.LetterSoundPeerReviewEvent;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/letter-sound/peer-reviews")
@RequiredArgsConstructor
public class LetterSoundPeerReviewsController {

  private Logger logger = LogManager.getLogger();

  private final LetterSoundContributionEventDao letterSoundContributionEventDao;

  private final LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;

  private final LetterSoundDao letterSoundDao;

  private final EmojiDao emojiDao;

  /**
   * Get {@link LetterSoundContributionEvent}s pending a {@link LetterSoundPeerReviewEvent} for the current {@link Contributor}.
   */
  @GetMapping
  public String handleGetRequest(HttpSession session, Model model) {
    logger.info("handleGetRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    logger.info("contributor: " + contributor);

    // Get the most recent LetterSoundContributionEvent for each LetterSound, including those made by the current Contributor
    List<LetterSoundContributionEvent> mostRecentLetterSoundContributionEvents = letterSoundContributionEventDao.readMostRecentPerLetterSound();
    logger.info("mostRecentLetterSoundContributionEvents.size(): " + mostRecentLetterSoundContributionEvents.size());

    // For each LetterSoundContributionEvent, check if the Contributor has already performed a peer-review.
    // If not, add it to the list of pending peer reviews.
    List<LetterSoundContributionEvent> letterSoundContributionEventsPendingPeerReview = new ArrayList<>();
    for (LetterSoundContributionEvent mostRecentLetterSoundContributionEvent : mostRecentLetterSoundContributionEvents) {
      // Ignore LetterSoundContributionEvents made by the current Contributor
      if (mostRecentLetterSoundContributionEvent.getContributor().getId().equals(contributor.getId())) {
        continue;
      }

      // Check if the current Contributor has already peer-reviewed this LetterSound contribution
      List<LetterSoundPeerReviewEvent> letterSoundPeerReviewEvents = letterSoundPeerReviewEventDao.readAll(mostRecentLetterSoundContributionEvent, contributor);
      if (letterSoundPeerReviewEvents.isEmpty()) {
        letterSoundContributionEventsPendingPeerReview.add(mostRecentLetterSoundContributionEvent);
      }
    }
    logger.info("letterSoundContributionEventsPendingPeerReview.size(): " + letterSoundContributionEventsPendingPeerReview.size());
    model.addAttribute("letterSoundContributionEventsPendingPeerReview", letterSoundContributionEventsPendingPeerReview);

    return "content/letter-sound/peer-reviews/pending";
  }
}
