package ai.elimu.web.admin;

import ai.elimu.dao.ApplicationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class MainAdminController {

  private final ApplicationDao applicationDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    model.addAttribute("applicationCount", applicationDao.readCount());

    return "admin/main";
  }
}
