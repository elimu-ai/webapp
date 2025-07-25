package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.dao.VideoContributionEventDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.entity.contributor.NumberPeerReviewEvent;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.contributor.StoryBookPeerReviewEvent;
import ai.elimu.entity.contributor.WordContributionEvent;
import ai.elimu.entity.contributor.WordPeerReviewEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contributor/{contributorId}")
@RequiredArgsConstructor
@Slf4j
public class ContributorController {

  private final ContributorDao contributorDao;

  private final WordContributionEventDao wordContributionEventDao;
  private final WordPeerReviewEventDao wordPeerReviewEventDao;

  private final NumberContributionEventDao numberContributionEventDao;
  private final NumberPeerReviewEventDao numberPeerReviewEventDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;
  private final StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;

  private final VideoContributionEventDao videoContributionEventDao;

  @GetMapping
  public String handleRequest(
      @PathVariable Long contributorId,
      Model model
  ) {
    log.info("handleRequest");

    Contributor contributor = contributorDao.read(contributorId);
    model.addAttribute("contributor2", contributor);

    // For contributor-summarized.jsp
    model.addAttribute("wordContributionsCount", wordContributionEventDao.readCount(contributor));
    model.addAttribute("wordPeerReviewsCount", wordPeerReviewEventDao.readCount(contributor));
    model.addAttribute("numberContributionsCount", numberContributionEventDao.readCount(contributor));
    model.addAttribute("numberPeerReviewsCount", numberPeerReviewEventDao.readCount(contributor));
    model.addAttribute("storyBookContributionsCount", storyBookContributionEventDao.readCount(contributor));
    model.addAttribute("storyBookPeerReviewsCount", storyBookPeerReviewEventDao.readCount(contributor));
    model.addAttribute("videoContributionsCount", videoContributionEventDao.readCount(contributor));

    // For contributor-words.jsp
    List<WordContributionEvent> wordContributionEvents = wordContributionEventDao.readAll(contributor);
    model.addAttribute("wordContributionEvents", wordContributionEvents);
    model.addAttribute("wordPeerReviewEvents", wordPeerReviewEventDao.readAll(contributor));
    Map<Long, List<WordPeerReviewEvent>> wordPeerReviewEventsByContributionMap = new HashMap<>();
    for (WordContributionEvent wordContributionEvent : wordContributionEvents) {
      wordPeerReviewEventsByContributionMap.put(wordContributionEvent.getId(), wordPeerReviewEventDao.readAll(wordContributionEvent));
    }
    model.addAttribute("wordPeerReviewEventsByContributionMap", wordPeerReviewEventsByContributionMap);

    // For contributor-numbers.jsp
    List<NumberContributionEvent> numberContributionEvents = numberContributionEventDao.readAll(contributor);
    model.addAttribute("numberContributionEvents", numberContributionEvents);
    model.addAttribute("numberPeerReviewEvents", numberPeerReviewEventDao.readAll(contributor));
    Map<Long, List<NumberPeerReviewEvent>> numberPeerReviewEventsByContributionMap = new HashMap<>();
    for (NumberContributionEvent numberContributionEvent : numberContributionEvents) {
      numberPeerReviewEventsByContributionMap.put(numberContributionEvent.getId(), numberPeerReviewEventDao.readAll(numberContributionEvent));
    }
    model.addAttribute("numberPeerReviewEventsByContributionMap", numberPeerReviewEventsByContributionMap);

    // For contributor-storybooks.jsp
    List<StoryBookContributionEvent> storyBookContributionEvents = storyBookContributionEventDao.readAll(contributor);
    model.addAttribute("storyBookContributionEvents", storyBookContributionEvents);
    model.addAttribute("storyBookPeerReviewEvents", storyBookPeerReviewEventDao.readAll(contributor));
    Map<Long, List<StoryBookPeerReviewEvent>> storyBookPeerReviewEventsByContributionMap = new HashMap<>();
    for (StoryBookContributionEvent storyBookContributionEvent : storyBookContributionEvents) {
      storyBookPeerReviewEventsByContributionMap.put(storyBookContributionEvent.getId(), storyBookPeerReviewEventDao.readAll(storyBookContributionEvent));
    }
    model.addAttribute("storyBookPeerReviewEventsByContributionMap", storyBookPeerReviewEventsByContributionMap);

    return "contributor/contributor";
  }
}
