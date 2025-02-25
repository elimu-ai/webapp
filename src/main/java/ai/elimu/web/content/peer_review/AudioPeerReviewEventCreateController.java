package ai.elimu.web.content.peer_review;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.AudioPeerReviewEventDao;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.AudioPeerReviewEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpSession;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/audio-peer-review-event/create")
@RequiredArgsConstructor
@Slf4j
public class AudioPeerReviewEventCreateController {

  private final AudioContributionEventDao audioContributionEventDao;

  private final AudioPeerReviewEventDao audioPeerReviewEventDao;

  private final AudioDao audioDao;

  /**
   * Note: The logic in this method is similar to the one used at {@link AudioPeerReviewsRestController#uploadAudioPeerReview}
   */
  @RequestMapping(method = RequestMethod.POST)
  public String handleSubmit(
      @RequestParam Long audioContributionEventId,
      @RequestParam Boolean approved,
      @RequestParam(required = false) String comment,
      HttpSession session
  ) {
    log.info("handleSubmit");

    Contributor contributor = (Contributor) session.getAttribute("contributor");

    log.info("audioContributionEventId: " + audioContributionEventId);
    AudioContributionEvent audioContributionEvent = audioContributionEventDao.read(audioContributionEventId);
    log.info("audioContributionEvent: " + audioContributionEvent);

    // Store the peer review event
    AudioPeerReviewEvent audioPeerReviewEvent = new AudioPeerReviewEvent();
    audioPeerReviewEvent.setContributor(contributor);
    audioPeerReviewEvent.setAudioContributionEvent(audioContributionEvent);
    audioPeerReviewEvent.setApproved(approved);
    audioPeerReviewEvent.setComment(StringUtils.abbreviate(comment, 1000));
    audioPeerReviewEvent.setTimestamp(Calendar.getInstance());
    audioPeerReviewEventDao.create(audioPeerReviewEvent);

    if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
      String contentUrl =
          "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/multimedia/audio/edit/" + audioContributionEvent.getAudio()
              .getId();
      DiscordHelper.sendChannelMessage(
          "Audio peer-reviewed: " + contentUrl,
          "\"" + audioContributionEvent.getAudio().getTitle() + "\"",
          "Comment: \"" + audioPeerReviewEvent.getComment() + "\"",
          audioPeerReviewEvent.getApproved(),
          null
      );
    }

    // Update the audio's peer review status
    int approvedCount = 0;
    int notApprovedCount = 0;
    for (AudioPeerReviewEvent peerReviewEvent : audioPeerReviewEventDao.readAll(audioContributionEvent)) {
      if (peerReviewEvent.getApproved()) {
        approvedCount++;
      } else {
        notApprovedCount++;
      }
    }
    log.info("approvedCount: " + approvedCount);
    log.info("notApprovedCount: " + notApprovedCount);
    Audio audio = audioContributionEvent.getAudio();
    if (approvedCount >= notApprovedCount) {
      audio.setPeerReviewStatus(PeerReviewStatus.APPROVED);
    } else {
      audio.setPeerReviewStatus(PeerReviewStatus.NOT_APPROVED);
    }
    audioDao.update(audio);

    return "redirect:/content/multimedia/audio/edit/" + audioContributionEvent.getAudio().getId() + "#contribution-events";
  }
}
