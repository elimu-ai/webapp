package org.literacyapp.web.content.contributor;

import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

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
@RequestMapping("/content/contributor/edit-motivation")
public class EditMotivationController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");
        
        // TODO: fetch from MailChimp and pre-fill

        return "content/contributor/edit-motivation";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam String motivation,
            Model model) {
    	logger.info("handleSubmit");
        
        logger.info("motivation: " + motivation);
        
        if (StringUtils.isBlank(motivation)) {
            model.addAttribute("errorCode", "NotNull.motivation");
            return "content/contributor/edit-motivation";
        } else {
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            if (StringUtils.isBlank(contributor.getMotivation())) {
                // The Contributor completed the on-boarding wizard for the first time
                // Update registration time to trigger ContributorRegistrationSummaryScheduler
                contributor.setRegistrationTime(Calendar.getInstance());
            }
            contributor.setMotivation(motivation);
            contributorDao.update(contributor);
            session.setAttribute("contributor", contributor);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just updated his/her information:\n" + 
                        "• Language: \"" + contributor.getLocale().getLanguage() + "\"\n" + 
                        "• Teams: " + contributor.getTeams() + "\n" + 
                        "• Personal motivation: \"" + contributor.getMotivation() + "\"\n" +
                        "See ") + "http://literacyapp.org/content/community/contributors";
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(null, text, iconUrl, null);
            }

            return "redirect:/content";
        }
    }
}
