package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contributor/list")
@RequiredArgsConstructor
@Slf4j
public class ContributorListController {

  private final ContributorDao contributorDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    model.addAttribute("contributors", contributorDao.readAllOrderedDesc());

    return "contributor/list";
  }
}
