package ai.elimu.web.content.contributor;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
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
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        model.addAttribute("teams", Team.values());
        
        // TODO: fetch from MailChimp and pre-select
    	
        return "content/contributor/edit-teams";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam(required = false) Set<Team> teams,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        logger.info("teams: " + teams);
        
        if (teams == null) {
            model.addAttribute("errorCode", "NotNull.teams");
            model.addAttribute("teams", Team.values());
            return "content/contributor/edit-teams";
        } else {        
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            contributor.setTeams(teams);
            contributorDao.update(contributor);
            session.setAttribute("contributor", contributor);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                         contributor.getFirstName() + " just updated his/her team(s):\n" + 
                         contributor.getTeams() + "\n" + 
                         "See ") + "http://elimu.ai/content/community/contributors";
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(null, text, iconUrl, null);
             }
            
            return "redirect:/content";
        }
    }
}
