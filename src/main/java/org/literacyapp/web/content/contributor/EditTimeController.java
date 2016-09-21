package org.literacyapp.web.content.contributor;

import java.net.URLEncoder;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContributorDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-time")
public class EditTimeController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");

        return "content/contributor/edit-time";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam Integer timePerWeek,
            Model model) {
    	logger.info("handleSubmit");
        
        logger.info("timePerWeek: " + timePerWeek);
        // TODO: validate timePerWeek
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        contributor.setTimePerWeek(timePerWeek);
        contributorDao.update(contributor);
        session.setAttribute("contributor", contributor);
        
        if (EnvironmentContextLoaderListener.env == Environment.PROD) {
            String text = URLEncoder.encode(
                    contributor.getFirstName() + " just updated his/her available time:\n" + 
                    contributor.getTimePerWeek() + " minutes per week\n" +
                    "See ") + "http://literacyapp.org/content/community/contributors";
            String iconUrl = contributor.getImageUrl();
            SlackApiHelper.postMessage(null, text, iconUrl, null);
        }
    	
        return "redirect:/content";
    }
}
