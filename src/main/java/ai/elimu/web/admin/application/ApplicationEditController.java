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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/application/edit")
@RequiredArgsConstructor
@Slf4j
public class ApplicationEditController {

  private final ApplicationDao applicationDao;

  private final ApplicationVersionDao applicationVersionDao;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public String handleRequest(
      @PathVariable Long id,
      Model model
  ) {
    log.info("handleRequest");

    Application application = applicationDao.read(id);
    model.addAttribute("application", application);

    model.addAttribute("applicationStatuses", ApplicationStatus.values());

    List<ApplicationVersion> applicationVersions = applicationVersionDao.readAll(application);
    model.addAttribute("applicationVersions", applicationVersions);

    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());

    return "admin/application/edit";
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public String handleSubmit(
      HttpSession session,
      @Valid Application application,
      BindingResult result,
      Model model
  ) {
    log.info("handleSubmit");

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
          log.info("Deleting ApplicationVersion: " + applicationVersion.getVersionCode());
          applicationVersionDao.delete(applicationVersion);
        }
      }

      return "redirect:/admin/application/list#" + application.getId();
    }
  }
}
