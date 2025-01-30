package ai.elimu.web.content.letter;

import java.util.Calendar;
import jakarta.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterContributionEventDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Letter;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/create")
public class LetterCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private LetterContributionEventDao letterContributionEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            Model model) {
        logger.info("handleRequest");
        
        Letter letter = new Letter();
        model.addAttribute("letter", letter);
        model.addAttribute("timeStart", System.currentTimeMillis());    

        return "content/letter/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid Letter letter,
            BindingResult result,
            Model model) {
        logger.info("handleSubmit");
        
        Letter existingLetter = letterDao.readByText(letter.getText());
        if (existingLetter != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letter", letter);
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            return "content/letter/create";
        } else {
            letter.setTimeLastUpdate(Calendar.getInstance());
            letterDao.create(letter);
            
            LetterContributionEvent letterContributionEvent = new LetterContributionEvent();
            letterContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            letterContributionEvent.setTimestamp(Calendar.getInstance());
            letterContributionEvent.setLetter(letter);
            letterContributionEvent.setRevisionNumber(letter.getRevisionNumber());
            letterContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            letterContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            letterContributionEventDao.create(letterContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter/edit/" + letter.getId();
                DiscordHelper.sendChannelMessage(
                        "Letter created: " + contentUrl,
                        "\"" + letterContributionEvent.getLetter().getText() + "\"",
                        "Comment: \"" + letterContributionEvent.getComment() + "\"",
                        null,
                        null
                );
            }
            
            return "redirect:/content/letter/list#" + letter.getId();
        }
    }
}
