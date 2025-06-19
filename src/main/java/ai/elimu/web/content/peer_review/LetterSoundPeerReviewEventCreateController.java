package ai.elimu.web.content.peer_review;

import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.LetterSoundContributionEvent;
import ai.elimu.entity.contributor.LetterSoundPeerReviewEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/letter-sound-peer-review-event/create")
@RequiredArgsConstructor
@Slf4j
public class LetterSoundPeerReviewEventCreateController {

  private final LetterSoundContributionEventDao letterSoundContributionEventDao;

  private final LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;

  private final LetterSoundDao letterSoundDao;

  @PostMapping
  public String handleSubmit(
      @RequestParam Long letterSoundContributionEventId,
      @RequestParam Boolean approved,
      @RequestParam(required = false) String comment,
      HttpSession session
  ) {
    log.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    log.info("letterSoundContributionEventId: " + letterSoundContributionEventId);
    LetterSoundContributionEvent letterSoundContributionEvent = letterSoundContributionEventDao.read(letterSoundContributionEventId);
    log.info("letterSoundContributionEvent: " + letterSoundContributionEvent);

    // Store the peer review event
    LetterSoundPeerReviewEvent letterSoundPeerReviewEvent = new LetterSoundPeerReviewEvent();
    letterSoundPeerReviewEvent.setContributor(contributor);
    letterSoundPeerReviewEvent.setLetterSoundContributionEvent(letterSoundContributionEvent);
    letterSoundPeerReviewEvent.setApproved(approved);
    letterSoundPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
    letterSoundPeerReviewEvent.setTimestamp(Calendar.getInstance());
    letterSoundPeerReviewEventDao.create(letterSoundPeerReviewEvent);

    String contentUrl = DomainHelper.getBaseUrl() + "/content/letter-sound/edit/" + letterSoundContributionEvent.getLetterSound().getId();
    DiscordHelper.sendChannelMessage(
        "Letter-sound peer-reviewed: " + contentUrl,
        "\"" + letterSoundContributionEvent.getLetterSound().getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
        "Comment: \"" + letterSoundPeerReviewEvent.getComment() + "\"",
        letterSoundPeerReviewEvent.getApproved(),
        null
    );

    // Update the peer review status
    int approvedCount = 0;
    int notApprovedCount = 0;
    for (LetterSoundPeerReviewEvent peerReviewEvent : letterSoundPeerReviewEventDao.readAll(letterSoundContributionEvent)) {
      if (peerReviewEvent.getApproved()) {
        approvedCount++;
      } else {
        notApprovedCount++;
      }
    }
    log.info("approvedCount: " + approvedCount);
    log.info("notApprovedCount: " + notApprovedCount);
    LetterSound letterSound = letterSoundContributionEvent.getLetterSound();
    updatePeerReviewStatus(letterSound, approvedCount, notApprovedCount);

    return "redirect:/content/letter-sound/edit/" + letterSoundContributionEvent.getLetterSound().getId() + "#contribution-events";
  }

  private void updatePeerReviewStatus(LetterSound letterSound, int approvedCount, int notApprovedCount) {
    if (approvedCount > notApprovedCount) {
      letterSound.setPeerReviewStatus(PeerReviewStatus.APPROVED);
    } else {
      letterSound.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
    }
    letterSoundDao.update(letterSound);
  }
}
