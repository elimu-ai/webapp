package ai.elimu.web.content.contributor;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.enums.Environment;
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
                         contributor.getFirstName() + " just updated his/her personal motivation:\n" + 
                         "> \"" + contributor.getMotivation() + "\"\n" +
                         "See ") + "http://elimu.ai/content/community/contributors";
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(null, text, iconUrl, null);
             }

            return "redirect:/content";
        }
    }
}
