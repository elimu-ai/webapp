package ai.elimu.web.admin.application;

import ai.elimu.dao.ApplicationDao;
import ai.elimu.entity.application.Application;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/application/list")
@RequiredArgsConstructor
@Slf4j
public class ApplicationListController {

  private final ApplicationDao applicationDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    // List count of active Android applications for each EGRA/EGMA skill

    List<Application> activeApplications = applicationDao.readAllByStatus(ApplicationStatus.ACTIVE);
    log.info("activeApplications.size(): " + activeApplications.size());

    Map<LiteracySkill, Integer> literacySkillCountMap = new LinkedHashMap<>();
    for (LiteracySkill literacySkill : LiteracySkill.values()) {
      literacySkillCountMap.put(literacySkill, 0);
    }
    for (Application application : activeApplications) {
      for (LiteracySkill literacySkill : application.getLiteracySkills()) {
        literacySkillCountMap.put(literacySkill, literacySkillCountMap.getOrDefault(literacySkill, 0) + 1);
      }
    }
    model.addAttribute("literacySkillCountMap", literacySkillCountMap);
    int maxLiteracySkillCount = 0;
    Collection<Integer> literacySkillCountCollection = literacySkillCountMap.values();
    for (int count : literacySkillCountCollection) {
      if (count > maxLiteracySkillCount) {
        maxLiteracySkillCount = count;
      }
    }
    model.addAttribute("maxLiteracySkillCount", maxLiteracySkillCount);

    Map<NumeracySkill, Integer> numeracySkillCountMap = new LinkedHashMap<>();
    for (NumeracySkill numeracySkill : NumeracySkill.values()) {
      numeracySkillCountMap.put(numeracySkill, 0);
    }
    for (Application application : activeApplications) {
      for (NumeracySkill numeracySkill : application.getNumeracySkills()) {
        numeracySkillCountMap.put(numeracySkill, numeracySkillCountMap.getOrDefault(numeracySkill, 0) + 1);
      }
    }
    model.addAttribute("numeracySkillCountMap", numeracySkillCountMap);
    int maxNumeracySkillCount = 0;
    Collection<Integer> numeracySkillCountCollection = numeracySkillCountMap.values();
    for (int count : numeracySkillCountCollection) {
      if (count > maxNumeracySkillCount) {
        maxNumeracySkillCount = count;
      }
    }
    model.addAttribute("maxNumeracySkillCount", maxNumeracySkillCount);

    List<Application> applications = applicationDao.readAll();
    model.addAttribute("applications", applications);

    return "application/list";
  }
}
