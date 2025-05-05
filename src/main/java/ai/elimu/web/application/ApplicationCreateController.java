package ai.elimu.web.application;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.entity.application.Application;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/application/create")
@RequiredArgsConstructor
@Slf4j
public class ApplicationCreateController {

  private final ApplicationDao applicationDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    Application application = new Application();
    application.setApplicationStatus(ApplicationStatus.MISSING_APK);
    model.addAttribute("application", application);

    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    return "application/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @Valid Application application,
      BindingResult result,
      Model model
  ) {
    log.info("handleSubmit");

    Application existingApplication = applicationDao.readByPackageName(application.getPackageName());
    if (existingApplication != null) {
      result.rejectValue("packageName", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("application", application);
      model.addAttribute("literacySkills", LiteracySkill.values());
      model.addAttribute("numeracySkills", NumeracySkill.values());
      return "application/create";
    } else {
      applicationDao.create(application);
      return "redirect:/application/list#" + application.getId();
    }
  }
}
