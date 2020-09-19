package ai.elimu.web.content.contributor;

import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.EmailValidator;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ContributorDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.util.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * This controller handles users redirected from SignOnControllerGitHub because of missing e-mail.
 */
@Controller
@RequestMapping("/content/contributor/add-email")
public class AddEmailController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private ContributorDao contributorDao;

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
        
        if (isRedirectFromRegistrationPage) {
            // Send welcome e-mail
            String to = contributor.getEmail();
            String from = "elimu.ai <info@elimu.ai>";
            String subject = "Welcome to the elimu.ai Community";
            String title = "Welcome!";
            String firstName = StringUtils.isBlank(contributor.getFirstName()) ? "" : contributor.getFirstName();
            String htmlText = "<p>Hi, " + firstName + "</p>";
            htmlText += "<p>Thank you very much for registering as a contributor to the elimu.ai Community. We are glad to see you join us!</p>";
            htmlText += "<h2>Purpose</h2>";
            htmlText += "<p>The purpose of elimu.ai is to provide <i>every child</i> with access to quality basic education.</p>";
            htmlText += "<h2>Why?</h2>";
            htmlText += "<p>The word \"elimu\" is Swahili for \"education\". We believe that a quality basic education is the right of every child no matter her social or geographical background.</p>";
            htmlText += "<h2>How?</h2>";
            htmlText += "<p>With your help, this is what we aim to achieve:</p>";
            htmlText += "<p><blockquote>\"The elimu.ai Community develops open source software for teaching children the basics of reading, writing and arithmetic.\"</blockquote></p>";
            htmlText += "<p><img src=\"https://gallery.mailchimp.com/1a69583fdeec7d1888db043c0/images/72b31d67-58fd-443e-a6be-3ef2095cfe3b.jpg\" alt=\"\" style=\"width: 564px; max-width: 100%;\" /></p>";
            htmlText += "<h2>Chat</h2>";
            htmlText += "<p>In Slack you can chat with the other elimu.ai Community members:</p>";
            Mailer.sendHtmlWithButton(to, null, from, subject, title, htmlText, "Open chat", "https://join.slack.com/t/elimu-ai/shared_invite/zt-eoc921ow-0cfjATlIF2X~zHhSgSyaAw");
        }
    	
        return "redirect:/content";
    }
}
