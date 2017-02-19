package org.literacyapp.web.content;

import java.security.Principal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ApplicationDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.admin.Application;
import org.literacyapp.model.enums.admin.ApplicationStatus;
import org.literacyapp.model.enums.content.LiteracySkill;
import org.literacyapp.model.enums.content.NumeracySkill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content")
public class MainContentController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ApplicationDao applicationDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            HttpServletRequest request, 
            HttpSession session, 
            Principal principal, 
            Model model) {
    	logger.info("handleRequest");
        
        // Check if the Contributor has not yet provided all required details
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        if (StringUtils.isBlank(contributor.getEmail())) {
            return "redirect:/content/contributor/add-email";
        } else if (StringUtils.isBlank(contributor.getFirstName()) || StringUtils.isBlank(contributor.getLastName())) {
            return "redirect:/content/contributor/edit-name";
        } else if (contributor.getLocale() == null) {
            return "redirect:/content/contributor/edit-locale";
        } else if ((contributor.getTeams() == null) || contributor.getTeams().isEmpty()) {
            return "redirect:/content/contributor/edit-teams";
        } else if (StringUtils.isBlank(contributor.getMotivation())) {
            return "redirect:/content/contributor/edit-motivation";
        } else if (contributor.getTimePerWeek() == null) {
            return "redirect:/content/contributor/edit-time";
        }
        
        // List count of active Android applications for each EGRA/EGMA skill
        
        List<Application> applications = applicationDao.readAllByStatus(contributor.getLocale(), ApplicationStatus.ACTIVE);
        logger.info("applications.size(): " + applications.size());
        
        Map<LiteracySkill, Integer> literacySkillCountMap = new LinkedHashMap<>();
        for (LiteracySkill literacySkill : LiteracySkill.values()) {
            literacySkillCountMap.put(literacySkill, 0);
        }
        for (Application application : applications) {
            for (LiteracySkill literacySkill : application.getLiteracySkills()) {
                int count = literacySkillCountMap.get(literacySkill);
                literacySkillCountMap.put(literacySkill, count + 1);
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
        for (Application application : applications) {
            for (NumeracySkill numeracySkill : application.getNumeracySkills()) {
                int count = numeracySkillCountMap.get(numeracySkill);
                numeracySkillCountMap.put(numeracySkill, count + 1);
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
    	
        return "content/main";
    }
}
