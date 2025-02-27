package ai.elimu.web.admin.application;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.dao.ApplicationVersionDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.admin.ApplicationVersion;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/application/edit/{id}")
@RequiredArgsConstructor
public class ApplicationEditController {

  private final Logger logger = LogManager.getLogger();

  private final ApplicationDao applicationDao;

  private final ApplicationVersionDao applicationVersionDao;

  @GetMapping
  public String handleRequest(
      @PathVariable Long id,
      Model model
  ) {
    logger.info("handleRequest");

    Application application = applicationDao.read(id);
    model.addAttribute("application", application);

    model.addAttribute("applicationStatuses", ApplicationStatus.values());

    List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
    model.addAttribute("applicationVersions", applicationVersions);

    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    return "admin/application/edit";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @Valid Application application,
      BindingResult result,
      Model model
  ) {
    logger.info("handleSubmit");

    if (result.hasErrors()) {
      model.addAttribute("application", application);

      model.addAttribute("applicationStatuses", ApplicationStatus.values());

      List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
      model.addAttribute("applicationVersions", applicationVersions);

      model.addAttribute("literacySkills", LiteracySkill.values());
      model.addAttribute("numeracySkills", NumeracySkill.values());

      return "admin/application/edit";
    } else {
      applicationDao.update(application);

      if (application.getApplicationStatus() == ApplicationStatus.DELETED) {
        // Delete corresponding ApplicationVersions
        List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
        for (ApplicationVersion applicationVersion : applicationVersions) {
          logger.info("Deleting ApplicationVersion: " + applicationVersion.getVersionCode());
          applicationVersionDao.delete(applicationVersion);
        }
      }

      return "redirect:/admin/application/list#" + application.getId();
    }
  }
}
