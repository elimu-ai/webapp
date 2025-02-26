package ai.elimu.web.content.peer_review;

import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.model.contributor.NumberPeerReviewEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/number-peer-review-event/create")
@RequiredArgsConstructor
public class NumberPeerReviewEventCreateController {

  private final Logger logger = LogManager.getLogger();

  private final NumberContributionEventDao numberContributionEventDao;

  private final NumberPeerReviewEventDao numberPeerReviewEventDao;

  private final NumberDao numberDao;

  @PostMapping
  public String handleSubmit(
      @RequestParam Long numberContributionEventId,
      @RequestParam Boolean approved,
      @RequestParam(required = false) String comment,
      HttpSession session
  ) {
    logger.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    logger.info("numberContributionEventId: " + numberContributionEventId);
    NumberContributionEvent numberContributionEvent = numberContributionEventDao.read(numberContributionEventId);
    logger.info("numberContributionEvent: " + numberContributionEvent);

    // Store the peer review event
    NumberPeerReviewEvent numberPeerReviewEvent = new NumberPeerReviewEvent();
    numberPeerReviewEvent.setContributor(contributor);
    numberPeerReviewEvent.setNumberContributionEvent(numberContributionEvent);
    numberPeerReviewEvent.setApproved(approved);
    numberPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
    numberPeerReviewEvent.setTimestamp(Calendar.getInstance());
    numberPeerReviewEventDao.create(numberPeerReviewEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl =
          "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/number/edit/" + numberContributionEvent.getNumber().getId();
      DiscordHelper.sendChannelMessage(
          "Number peer-reviewed: " + contentUrl,
          "\"" + numberContributionEvent.getNumber().getValue() + "\"",
          "Comment: \"" + numberPeerReviewEvent.getComment() + "\"",
          numberPeerReviewEvent.getApproved(),
          null
      );
    }

    // Update the number's peer review status
    int approvedCount = 0;
    int notApprovedCount = 0;
    for (NumberPeerReviewEvent peerReviewEvent : numberPeerReviewEventDao.readAll(numberContributionEvent)) {
      if (peerReviewEvent.getApproved()) {
        approvedCount++;
      } else {
        notApprovedCount++;
      }
    }
    logger.info("approvedCount: " + approvedCount);
    logger.info("notApprovedCount: " + notApprovedCount);
    Number number = numberContributionEvent.getNumber();
    if (approvedCount >= notApprovedCount) {
      number.setPeerReviewStatus(PeerReviewStatus.APPROVED);
    } else {
      number.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
    }
    numberDao.update(number);

    return "redirect:/content/number/edit/" + numberContributionEvent.getNumber().getId() + "#contribution-events";
  }
}
