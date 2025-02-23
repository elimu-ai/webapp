package ai.elimu.web.contributor;

import ai.elimu.dao.ContributorDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/contributor/list")
@RequiredArgsConstructor
@Slf4j
public class ContributorListController {

  private final ContributorDao contributorDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    model.addAttribute("contributors", contributorDao.readAllOrderedDesc());

    return "contributor/list";
  }
}
