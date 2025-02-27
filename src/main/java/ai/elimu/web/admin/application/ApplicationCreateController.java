package ai.elimu.web.admin.application;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/application/create")
@RequiredArgsConstructor
public class ApplicationCreateController {

  private final Logger logger = LogManager.getLogger();

  private final ApplicationDao applicationDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    Application application = new Application();
    application.setApplicationStatus(ApplicationStatus.MISSING_APK);
    model.addAttribute("application", application);

    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    return "admin/application/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @Valid Application application,
      BindingResult result,
      Model model
  ) {
    logger.info("handleSubmit");

    Application existingApplication = applicationDao.readByPackageName(application.getPackageName());
    if (existingApplication != null) {
      result.rejectValue("packageName", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("application", application);
      model.addAttribute("literacySkills", LiteracySkill.values());
      model.addAttribute("numeracySkills", NumeracySkill.values());
      return "admin/application/create";
    } else {
      applicationDao.create(application);
      return "redirect:/admin/application/list#" + application.getId();
    }
  }
}
