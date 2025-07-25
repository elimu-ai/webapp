package ai.elimu.web.content.peer_review;

import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.NumberPeerReviewEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
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
@RequestMapping("/content/number-peer-review-event/create")
@RequiredArgsConstructor
@Slf4j
public class NumberPeerReviewEventCreateController {

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
    log.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    log.info("numberContributionEventId: " + numberContributionEventId);
    NumberContributionEvent numberContributionEvent = numberContributionEventDao.read(numberContributionEventId);
    log.info("numberContributionEvent: " + numberContributionEvent);

    // Store the peer review event
    NumberPeerReviewEvent numberPeerReviewEvent = new NumberPeerReviewEvent();
    numberPeerReviewEvent.setContributor(contributor);
    numberPeerReviewEvent.setNumberContributionEvent(numberContributionEvent);
    numberPeerReviewEvent.setApproved(approved);
    numberPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
    numberPeerReviewEvent.setTimestamp(Calendar.getInstance());
    numberPeerReviewEventDao.create(numberPeerReviewEvent);

    String contentUrl = DomainHelper.getBaseUrl() + "/content/number/edit/" + numberContributionEvent.getNumber().getId();
    DiscordHelper.postToChannel(
        Channel.CONTENT,
        "Number peer-reviewed: " + contentUrl,
        "\"" + numberContributionEvent.getNumber().getValue() + "\"",
        "Comment: \"" + numberPeerReviewEvent.getComment() + "\"",
        numberPeerReviewEvent.getApproved(),
        null
    );

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
    log.info("approvedCount: " + approvedCount);
    log.info("notApprovedCount: " + notApprovedCount);
    Number number = numberContributionEvent.getNumber();
    if (approvedCount > notApprovedCount) {
      number.setPeerReviewStatus(PeerReviewStatus.APPROVED);
    } else {
      number.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
    }
    numberDao.update(number);

    return "redirect:/content/number/edit/" + numberContributionEvent.getNumber().getId() + "#contribution-events";
  }
}
