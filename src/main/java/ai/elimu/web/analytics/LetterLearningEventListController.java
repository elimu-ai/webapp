package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.entity.analytics.LetterLearningEvent;
import ai.elimu.util.AnalyticsHelper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/letter-learning-event/list")
@RequiredArgsConstructor
@Slf4j
public class LetterLearningEventListController {

  private final LetterLearningEventDao letterLearningEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<LetterLearningEvent> letterLearningEvents = letterLearningEventDao.readAll();
    model.addAttribute("letterLearningEvents", letterLearningEvents);
    for (LetterLearningEvent learningEvent : letterLearningEvents) {
      learningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(learningEvent.getAndroidId()));
    }

    return "analytics/letter-learning-event/list";
  }
}
