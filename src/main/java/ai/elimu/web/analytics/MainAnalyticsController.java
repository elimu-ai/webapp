package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordLearningEventDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class MainAnalyticsController {

  private final Logger logger = LogManager.getLogger();

  private final LetterLearningEventDao letterLearningEventDao;

  private final WordLearningEventDao wordLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  private final VideoLearningEventDao videoLearningEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    model.addAttribute("letterLearningEventCount", letterLearningEventDao.readCount());
    model.addAttribute("wordLearningEventCount", wordLearningEventDao.readCount());
    model.addAttribute("storyBookLearningEventCount", storyBookLearningEventDao.readCount());
    model.addAttribute("videoLearningEventCount", videoLearningEventDao.readCount());

    return "analytics/main";
  }
}
