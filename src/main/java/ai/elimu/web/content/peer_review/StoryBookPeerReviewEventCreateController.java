package ai.elimu.web.content.peer_review;

import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/storybook-peer-review-event/create")
@RequiredArgsConstructor
@Slf4j
public class StoryBookPeerReviewEventCreateController {

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;

  private final StoryBookDao storyBookDao;

  @PostMapping
  public String handleSubmit(
      @RequestParam Long storyBookContributionEventId,
      @RequestParam Boolean approved,
      @RequestParam(required = false) String comment,
      HttpSession session
  ) {
    log.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    log.info("storyBookContributionEventId: " + storyBookContributionEventId);
    StoryBookContributionEvent storyBookContributionEvent = storyBookContributionEventDao.read(storyBookContributionEventId);
    log.info("storyBookContributionEvent: " + storyBookContributionEvent);

    // Store the peer review event
    StoryBookPeerReviewEvent storyBookPeerReviewEvent = new StoryBookPeerReviewEvent();
    storyBookPeerReviewEvent.setContributor(contributor);
    storyBookPeerReviewEvent.setStoryBookContributionEvent(storyBookContributionEvent);
    storyBookPeerReviewEvent.setApproved(approved);
    storyBookPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
    storyBookPeerReviewEvent.setTimestamp(Calendar.getInstance());
    storyBookPeerReviewEventDao.create(storyBookPeerReviewEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl =
          "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBookContributionEvent.getStoryBook()
              .getId();
      String embedThumbnailUrl = null;
      if (storyBookContributionEvent.getStoryBook().getCoverImage() != null) {
        embedThumbnailUrl = storyBookContributionEvent.getStoryBook().getCoverImage().getUrl();
      }
      DiscordHelper.sendChannelMessage(
          "Storybook peer-reviewed: " + contentUrl,
          "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
          "Comment: \"" + storyBookPeerReviewEvent.getComment() + "\"",
          storyBookPeerReviewEvent.getApproved(),
          embedThumbnailUrl
      );
    }

    // Update the storybook's peer review status
    int approvedCount = 0;
    int notApprovedCount = 0;
    for (StoryBookPeerReviewEvent peerReviewEvent : storyBookPeerReviewEventDao.readAll(storyBookContributionEvent)) {
      if (peerReviewEvent.getApproved()) {
        approvedCount++;
      } else {
        notApprovedCount++;
      }
    }
    log.info("approvedCount: " + approvedCount);
    log.info("notApprovedCount: " + notApprovedCount);
    StoryBook storyBook = storyBookContributionEvent.getStoryBook();
    if (approvedCount > notApprovedCount) {
      storyBook.setPeerReviewStatus(PeerReviewStatus.APPROVED);
    } else {
      storyBook.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
    }
    storyBookDao.update(storyBook);

    return "redirect:/content/storybook/edit/" + storyBookContributionEvent.getStoryBook().getId() + "#contribution-events";
  }
}
