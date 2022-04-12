package ai.elimu.web.content.contributor;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/contributor/edit-motivation")
public class EditMotivationController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private ContributorDao contributorDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");

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
                
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/contributor/" + contributor.getId();
                String embedThumbnailUrl = null;
                if (StringUtils.isNotBlank(contributor.getImageUrl())) {
                    embedThumbnailUrl = contributor.getImageUrl();
                }
                DiscordHelper.sendChannelMessage(
                        "Contributor joined: " + contentUrl,
                        contributor.getFirstName() + " " + contributor.getLastName(),
                        "Motivation: \"" + motivation + "\"",
                        null,
                        embedThumbnailUrl
                );
            }
            
            contributor.setMotivation(motivation);
            contributorDao.update(contributor);
            session.setAttribute("contributor", contributor);
            
            return "redirect:/content";
        }
    }
}
