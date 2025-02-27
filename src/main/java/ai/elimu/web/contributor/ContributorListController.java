package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contributor/list")
@RequiredArgsConstructor
public class ContributorListController {

  private final Logger logger = LogManager.getLogger();

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    model.addAttribute("contributors", contributorDao.readAllOrderedDesc());

    return "contributor/list";
  }
}
