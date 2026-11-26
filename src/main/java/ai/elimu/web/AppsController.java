package ai.elimu.web;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.entity.application.Application;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Lists the active Android applications within each EGRA (literacy) and EGMA (numeracy) skill category.
 */
@Controller
@RequestMapping("/apps")
@RequiredArgsConstructor
@Slf4j
public class AppsController {

  private final ApplicationDao applicationDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Application> activeApplications = applicationDao.readAllByStatus(ApplicationStatus.ACTIVE);
    log.info("activeApplications.size(): " + activeApplications.size());
    activeApplications.sort(Comparator.comparing(Application::getPackageName));

    // Include all skills, also the ones that no application covers yet
    Map<LiteracySkill, List<Application>> literacySkillApplicationsMap = new LinkedHashMap<>();
    for (LiteracySkill literacySkill : LiteracySkill.values()) {
      literacySkillApplicationsMap.put(literacySkill, new ArrayList<>());
    }
    Map<NumeracySkill, List<Application>> numeracySkillApplicationsMap = new LinkedHashMap<>();
    for (NumeracySkill numeracySkill : NumeracySkill.values()) {
      numeracySkillApplicationsMap.put(numeracySkill, new ArrayList<>());
    }

    for (Application application : activeApplications) {
      if (application.getLiteracySkills() != null) {
        for (LiteracySkill literacySkill : application.getLiteracySkills()) {
          literacySkillApplicationsMap.get(literacySkill).add(application);
        }
      }
      if (application.getNumeracySkills() != null) {
        for (NumeracySkill numeracySkill : application.getNumeracySkills()) {
          numeracySkillApplicationsMap.get(numeracySkill).add(application);
        }
      }
    }

    model.addAttribute("literacySkillApplicationsMap", literacySkillApplicationsMap);
    model.addAttribute("numeracySkillApplicationsMap", numeracySkillApplicationsMap);

    return "apps";
  }
}
