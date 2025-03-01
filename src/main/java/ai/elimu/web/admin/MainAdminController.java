package ai.elimu.web.admin;

import ai.elimu.dao.ApplicationDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class MainAdminController {

  private final ApplicationDao applicationDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    model.addAttribute("applicationCount", applicationDao.readCount());

    return "admin/main";
  }
}
