package ai.elimu.web.content.number;

import ai.elimu.dao.NumberContributionEventDao;
import java.util.Calendar;
import javax.validation.Valid;

import ai.elimu.web.content.emoji.EmojiComponent;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.NumberPeerReviewEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.model.enums.Platform;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/edit")
public class NumberEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private NumberContributionEventDao numberContributionEventDao;
    
    @Autowired
    private NumberPeerReviewEventDao numberPeerReviewEventDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiComponent emojiComponent;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        setModel(model,  numberDao.read(id), String.valueOf(System.currentTimeMillis()));

        return "content/number/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid Number number,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Number existingNumber = numberDao.readByValue(number.getValue());
        if ((existingNumber != null) && !existingNumber.getId().equals(number.getId())) {
            result.rejectValue("value", "NonUnique");
        }
        
        if (result.hasErrors()) {
            setModel(model, number, request.getParameter("timeStart"));
            return "content/number/edit";
        } else {
            number.setTimeLastUpdate(Calendar.getInstance());
            number.setRevisionNumber(number.getRevisionNumber() + 1);
            numberDao.update(number);
            
            NumberContributionEvent numberContributionEvent = new NumberContributionEvent();
            numberContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            numberContributionEvent.setTime(Calendar.getInstance());
            numberContributionEvent.setNumber(number);
            numberContributionEvent.setRevisionNumber(number.getRevisionNumber());
            numberContributionEvent.setComment(request.getParameter("contributionComment"));
            numberContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            numberContributionEvent.setPlatform(Platform.WEBAPP);
            numberContributionEventDao.create(numberContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/number/edit/" + number.getId();
                DiscordHelper.sendChannelMessage(
                        "Number edited: " + contentUrl,
                        String.valueOf(numberContributionEvent.getNumber().getValue()),
                        "Comment: \"" + numberContributionEvent.getComment() + "\"",
                        null,
                        null
                );
            }
            
            return "redirect:/content/number/list#" + number.getId();
        }
    }

    private void setModel(Model model, Number number, String timeStart) {
        model.addAttribute("number", number);
        model.addAttribute("timeStart", timeStart);
        model.addAttribute("words", wordDao.readAllOrdered());
        model.addAttribute("emojisByWordId", emojiComponent.getEmojisByWordId());
        model.addAttribute("numberContributionEvents", numberContributionEventDao.readAll(number));
        model.addAttribute("numberPeerReviewEvents", numberPeerReviewEventDao.readAll(number));
    }

}
