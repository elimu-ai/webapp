package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordLearningEventDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor
@Slf4j
public class MainAnalyticsController {

  private final LetterLearningEventDao letterLearningEventDao;

  private final WordLearningEventDao wordLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  private final VideoLearningEventDao videoLearningEventDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    model.addAttribute("letterLearningEventCount", letterLearningEventDao.readCount());
    model.addAttribute("wordLearningEventCount", wordLearningEventDao.readCount());
    model.addAttribute("storyBookLearningEventCount", storyBookLearningEventDao.readCount());
    model.addAttribute("videoLearningEventCount", videoLearningEventDao.readCount());

    return "analytics/main";
  }
}
