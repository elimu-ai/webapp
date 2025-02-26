package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.model.analytics.LetterLearningEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/letter-learning-event/list")
@RequiredArgsConstructor
public class LetterLearningEventListController {

  private final Logger logger = LogManager.getLogger();

  private final LetterLearningEventDao letterLearningEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    List<LetterLearningEvent> letterLearningEvents = letterLearningEventDao.readAll();
    model.addAttribute("letterLearningEvents", letterLearningEvents);

    return "analytics/letter-learning-event/list";
  }
}
