package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.model.analytics.LetterLearningEvent;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/letter-learning-event/list")
@RequiredArgsConstructor
@Slf4j
public class LetterLearningEventListController {

  private final LetterLearningEventDao letterLearningEventDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<LetterLearningEvent> letterLearningEvents = letterLearningEventDao.readAll();
    model.addAttribute("letterLearningEvents", letterLearningEvents);

    return "analytics/letter-learning-event/list";
  }
}
