package ai.elimu.web.content.peer_review;

import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.contributor.WordPeerReviewEvent;
import ai.elimu.model.enums.PeerReviewStatus;
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
@RequestMapping("/content/word-peer-review-event/create")
@RequiredArgsConstructor
@Slf4j
public class WordPeerReviewEventCreateController {

  private final WordContributionEventDao wordContributionEventDao;

  private final WordPeerReviewEventDao wordPeerReviewEventDao;

  private final WordDao wordDao;

  @PostMapping
  public String handleSubmit(
      @RequestParam Long wordContributionEventId,
      @RequestParam Boolean approved,
      @RequestParam(required = false) String comment,
      HttpSession session
  ) {
    log.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    log.info("wordContributionEventId: " + wordContributionEventId);
    WordContributionEvent wordContributionEvent = wordContributionEventDao.read(wordContributionEventId);
    log.info("wordContributionEvent: " + wordContributionEvent);

    // Store the peer review event
    WordPeerReviewEvent wordPeerReviewEvent = new WordPeerReviewEvent();
    wordPeerReviewEvent.setContributor(contributor);
    wordPeerReviewEvent.setWordContributionEvent(wordContributionEvent);
    wordPeerReviewEvent.setApproved(approved);
    wordPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
    wordPeerReviewEvent.setTimestamp(Calendar.getInstance());
    wordPeerReviewEventDao.create(wordPeerReviewEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl =
          "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + wordContributionEvent.getWord().getId();
      DiscordHelper.sendChannelMessage(
          "Word peer-reviewed: " + contentUrl,
          "\"" + wordContributionEvent.getWord().getText() + "\"",
          "Comment: \"" + wordPeerReviewEvent.getComment() + "\"",
          wordPeerReviewEvent.isApproved(),
          null
      );
    }

    // Update the word's peer review status
    int approvedCount = 0;
    int notApprovedCount = 0;
    for (WordPeerReviewEvent peerReviewEvent : wordPeerReviewEventDao.readAll(wordContributionEvent)) {
      if (peerReviewEvent.isApproved()) {
        approvedCount++;
      } else {
        notApprovedCount++;
      }
    }
    log.info("approvedCount: " + approvedCount);
    log.info("notApprovedCount: " + notApprovedCount);
    Word word = wordContributionEvent.getWord();
    if (approvedCount >= notApprovedCount) {
      word.setPeerReviewStatus(PeerReviewStatus.APPROVED);
    } else {
      word.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
    }
    wordDao.update(word);

    return "redirect:/content/word/edit/" + wordContributionEvent.getWord().getId() + "#contribution-events";
  }
}
