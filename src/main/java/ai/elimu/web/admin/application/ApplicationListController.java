package ai.elimu.web.admin.application;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ApplicationDao;
import ai.elimu.model.admin.Application;
import ai.elimu.model.v2.enums.admin.ApplicationStatus;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin/application/list")
public class ApplicationListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ApplicationDao applicationDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        
        // List count of active Android applications for each EGRA/EGMA skill
        
        List<Application> activeApplications = applicationDao.readAllByStatus(ApplicationStatus.ACTIVE);
        logger.info("activeApplications.size(): " + activeApplications.size());
        
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

        return "admin/application/list";
    }
}
