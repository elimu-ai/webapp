package org.literacyapp.web.content.letter;

import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Letter;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/edit")
public class LetterEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private ContentCreationEventDao contentCreationEventDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Letter letter = letterDao.read(id);
        model.addAttribute("letter", letter);
        
        model.addAttribute("contentCreationEvents", contentCreationEventDao.readAll(letter));

        return "content/letter/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Letter letter,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Letter existingLetter = letterDao.readByText(letter.getLocale(), letter.getText());
        if ((existingLetter != null) && !existingLetter.getId().equals(letter.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letter", letter);
            model.addAttribute("contentCreationEvents", contentCreationEventDao.readAll(letter));
            return "content/letter/edit";
        } else {
            letter.setText(letter.getText().toLowerCase());
            letter.setTimeLastUpdate(Calendar.getInstance());
            letter.setRevisionNumber(letter.getRevisionNumber() + 1);
            letterDao.update(letter);
            
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            
            ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
            contentCreationEvent.setContributor(contributor);
            contentCreationEvent.setContent(letter);
            contentCreationEvent.setCalendar(Calendar.getInstance());
            contentCreationEventDao.update(contentCreationEvent);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just edited a Letter:\n" + 
                        "• Language: \"" + letter.getLocale().getLanguage() + "\"\n" + 
                        "• Text: '" + letter.getText() + "'\n" +
                        "See ") + "http://literacyapp.org/content/letter/list";
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
            }
            
            return "redirect:/content/letter/list";
        }
    }
}
