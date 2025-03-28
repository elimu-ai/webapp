package ai.elimu.web.content.storybook;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/peer-reviews")
@RequiredArgsConstructor
@Slf4j
public class StoryBookPeerReviewsController {

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;

  private final StoryBookDao storyBookDao;
    
  private final EmojiDao emojiDao;

  /**
   * Get {@link StoryBookContributionEvent}s pending a {@link StoryBookPeerReviewEvent} for the current {@link Contributor}.
   */
  @GetMapping
  public String handleGetRequest(HttpSession session, Model model) {
    log.info("handleGetRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    log.info("contributor: " + contributor);

    List<StoryBookContributionEvent> allStoryBookContributionEvents = storyBookContributionEventDao.readAllOrderedByTimeDesc();
    log.info("allStoryBookContributionEvents.size(): " + allStoryBookContributionEvents.size());

    // Get the most recent StoryBookContributionEvent for each StoryBook, including those made by the current Contributor
    List<StoryBookContributionEvent> mostRecentStoryBookContributionEvents = storyBookContributionEventDao.readMostRecentPerStoryBook();
    log.info("mostRecentStoryBookContributionEvents.size(): " + mostRecentStoryBookContributionEvents.size());

    // For each StoryBookContributionEvent, check if the Contributor has already performed a peer-review.
    // If not, add it to the list of pending peer reviews.
    List<StoryBookContributionEvent> storyBookContributionEventsPendingPeerReview = new ArrayList<>();
    for (StoryBookContributionEvent mostRecentStoryBookContributionEvent : mostRecentStoryBookContributionEvents) {
      // Ignore StoryBookContributionEvents made by the current Contributor
      if (mostRecentStoryBookContributionEvent.getContributor().getId().equals(contributor.getId())) {
        continue;
      }

      // Check if the current Contributor has already peer-reviewed this StoryBook contribution
      List<StoryBookPeerReviewEvent> storyBookPeerReviewEvents = storyBookPeerReviewEventDao.readAll(mostRecentStoryBookContributionEvent, contributor);
      if (storyBookPeerReviewEvents.isEmpty()) {
        storyBookContributionEventsPendingPeerReview.add(mostRecentStoryBookContributionEvent);
      }
    }
    log.info("storyBookContributionEventsPendingPeerReview.size(): " + storyBookContributionEventsPendingPeerReview.size());
    model.addAttribute("storyBookContributionEventsPendingPeerReview", storyBookContributionEventsPendingPeerReview);

    return "content/storybook/peer-reviews/pending";
  }
}
