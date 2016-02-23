package org.literacyapp.web.content.contributor;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-teams")
public class EditTeamsController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleEditTeamsRequest() {
    	logger.info("handleEditTeamsRequest");
    	
        return "content/contributor/edit-teams";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleEditTeamsSubmit(
            HttpSession session,
            @RequestParam Set<Team> teams,
            Model model
    ) {
    	logger.info("handleEditTeamsSubmit");
        
        // TODO: validate selection
        
        logger.info("teams: " + teams);
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        contributor.setTeams(teams);
        contributorDao.update(contributor);
        session.setAttribute("contributor", contributor);
    	
        return "redirect:/content";
    }
}
