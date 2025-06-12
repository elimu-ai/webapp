package ai.elimu.web.analytics;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.dao.WordLearningEventDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics")
@RequiredArgsConstructor
@Slf4j
public class MainAnalyticsController {

  private final StudentDao studentDao;

  private final LetterSoundAssessmentEventDao letterSoundAssessmentEventDao;
  private final LetterSoundLearningEventDao letterSoundLearningEventDao;

  private final WordAssessmentEventDao wordAssessmentEventDao;
  private final WordLearningEventDao wordLearningEventDao;

  private final NumberLearningEventDao numberLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  private final VideoLearningEventDao videoLearningEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");
    
    model.addAttribute("studentCount", studentDao.readCount());

    model.addAttribute("letterSoundAssessmentEventCount", letterSoundAssessmentEventDao.readCount());
    model.addAttribute("letterSoundLearningEventCount", letterSoundLearningEventDao.readCount());

    model.addAttribute("wordAssessmentEventCount", wordAssessmentEventDao.readCount());
    model.addAttribute("wordLearningEventCount", wordLearningEventDao.readCount());

    model.addAttribute("numberLearningEventCount", numberLearningEventDao.readCount());
    
    model.addAttribute("storyBookLearningEventCount", storyBookLearningEventDao.readCount());

    model.addAttribute("videoLearningEventCount", videoLearningEventDao.readCount());

    return "analytics/main";
  }
}
