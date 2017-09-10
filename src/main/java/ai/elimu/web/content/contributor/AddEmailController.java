package ai.elimu.web.content.contributor;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;

import org.apache.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.dao.SignOnEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.contributor.SignOnEvent;
import ai.elimu.model.enums.Environment;
import ai.elimu.util.Mailer;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

// Redirected from SignOnControllerGitHub because of missing e-mail
@Controller
@RequestMapping("/content/contributor/add-email")
public class AddEmailController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;
    
    @Autowired
    private SignOnEventDao signOnEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
    	logger.info("handleRequest");
    	
        return "content/contributor/add-email";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @RequestParam String email,
            Model model) {
    	logger.info("handleSubmit");
        
        if (!EmailValidator.getInstance().isValid(email)) {
            // TODO: display error message
            return "content/contributor/add-email";
        }
        
        Contributor existingContributor = contributorDao.read(email);
        if (existingContributor != null) {
            // TODO: display error message
            return "content/contributor/add-email";
        }
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        boolean isRedirectFromRegistrationPage = contributor.getEmail() == null;
        contributor.setEmail(email);
        contributorDao.create(contributor);
        
        session.setAttribute("contributor", contributor);
        
        SignOnEvent signOnEvent = new SignOnEvent();
        signOnEvent.setContributor(contributor);
        signOnEvent.setCalendar(Calendar.getInstance());
        signOnEventDao.create(signOnEvent);
        
        if (isRedirectFromRegistrationPage) {
            // Send welcome e-mail
            String to = contributor.getEmail();
            String from = "elimu.ai <info@elimu.ai>";
            String subject = "Welcome to the community";
            String title = "Welcome!";
            String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
            String htmlText = "<p>Hi, " + firstName + "</p>";
            htmlText += "<p>Thank you very much for registering as a contributor to the elimu.ai community. We are glad to see you join us!</p>";
            htmlText += "<p>With your help, this is what we aim to achieve:</p>";
            htmlText += "<p><blockquote>\"The mission of the elimu.ai project is to build software that will enable children without access to school to learn how to read and write <i>on their own</i>.\"</blockquote></p>";
            htmlText += "<p><img src=\"http://elimu.ai/static/img/banner-en.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
            htmlText += "<h2>Chat</h2>";
            htmlText += "<p>At http://slack.elimu.ai you can chat with the other community members.</p>";
            htmlText += "<h2>Feedback</h2>";
            htmlText += "<p>If you have any questions or suggestions, please contact us by replying to this e-mail or messaging us in the Slack chat room.</p>";
            Mailer.sendHtml(to, null, from, subject, title, htmlText);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 // Post notification in Slack
                 String name = "";
                 if (StringUtils.isNotBlank(contributor.getFirstName())) {
                     name += "(";
                     name += contributor.getFirstName();
                     if (StringUtils.isNotBlank(contributor.getLastName())) {
                         name += " " + contributor.getLastName();
                     }
                     name += ")";
                 }
                 String text = URLEncoder.encode("A new contributor " + name + " just joined the community: ") + "http://elimu.ai/content/community/contributors";
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(null, text, iconUrl, null);
             }
        }
    	
        return "redirect:/content";
    }
}
