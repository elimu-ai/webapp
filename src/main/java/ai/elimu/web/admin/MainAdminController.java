package ai.elimu.web.admin;

import ai.elimu.dao.ApplicationDao;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class MainAdminController {

  private final Logger logger = LogManager.getLogger();

  private final ApplicationDao applicationDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    model.addAttribute("applicationCount", applicationDao.readCount());

    return "admin/main";
  }
}
