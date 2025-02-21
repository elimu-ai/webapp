package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Letter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/list")
@RequiredArgsConstructor
public class LetterListController {

  private final Logger logger = LogManager.getLogger();

  private final LetterDao letterDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    List<Letter> letters = letterDao.readAllOrderedByUsage();
    model.addAttribute("letters", letters);

    int maxUsageCount = 0;
    for (Letter letter : letters) {
      if (letter.getUsageCount() > maxUsageCount) {
        maxUsageCount = letter.getUsageCount();
      }
    }
    model.addAttribute("maxUsageCount", maxUsageCount);

    return "content/letter/list";
  }
}
