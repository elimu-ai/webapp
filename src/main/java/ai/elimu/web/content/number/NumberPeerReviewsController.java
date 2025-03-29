package ai.elimu.web.content.number;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.NumberPeerReviewEvent;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This controller has similar functionality as the {@link NumberPeerReviewsRestController}.
 */
@Controller
@RequestMapping("/content/number/peer-reviews")
@RequiredArgsConstructor
@Slf4j
public class NumberPeerReviewsController {

  private final NumberContributionEventDao numberContributionEventDao;

  private final NumberPeerReviewEventDao numberPeerReviewEventDao;

  private final NumberDao numberDao;

  private final EmojiDao emojiDao;

  /**
   * Get {@link NumberContributionEvent}s pending a {@link NumberPeerReviewEvent} for the current {@link Contributor}.
   */
  @GetMapping
  public String handleGetRequest(HttpSession session, Model model) {
    log.info("handleGetRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    log.info("contributor: " + contributor);

    // Get the most recent NumberContributionEvent for each Number, including those made by the current Contributor
    List<NumberContributionEvent> mostRecentNumberContributionEvents = numberContributionEventDao.readMostRecentPerNumber();
    log.info("mostRecentNumberContributionEvents.size(): " + mostRecentNumberContributionEvents.size());

    // For each NumberContributionEvent, check if the Contributor has already performed a peer-review.
    // If not, add it to the list of pending peer reviews.
    List<NumberContributionEvent> numberContributionEventsPendingPeerReview = new ArrayList<>();
    for (NumberContributionEvent mostRecentNumberContributionEvent : mostRecentNumberContributionEvents) {
      // Ignore NumberContributionEvents made by the current Contributor
      if (mostRecentNumberContributionEvent.getContributor().getId().equals(contributor.getId())) {
        continue;
      }

      // Check if the current Contributor has already peer-reviewed this Number contribution
      List<NumberPeerReviewEvent> numberPeerReviewEvents = numberPeerReviewEventDao.readAll(mostRecentNumberContributionEvent, contributor);
      if (numberPeerReviewEvents.isEmpty()) {
        numberContributionEventsPendingPeerReview.add(mostRecentNumberContributionEvent);
      }
    }
    log.info("numberContributionEventsPendingPeerReview.size(): " + numberContributionEventsPendingPeerReview.size());
    model.addAttribute("numberContributionEventsPendingPeerReview", numberContributionEventsPendingPeerReview);

    return "content/number/peer-reviews/pending";
  }
}
