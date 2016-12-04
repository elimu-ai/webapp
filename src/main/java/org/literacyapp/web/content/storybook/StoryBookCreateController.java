package org.literacyapp.web.content.storybook;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/create")
public class StoryBookCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storybookDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        StoryBook storyBook = new StoryBook();
        model.addAttribute("storyBook", storyBook);

        return "content/storybook/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid StoryBook storyBook,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        StoryBook existingStoryBook = storybookDao.readByTitle(storyBook.getLocale(), storyBook.getTitle());
        if (existingStoryBook != null) {
            result.rejectValue("title", "NonUnique");
        }
        
        List<String> paragraphs = storyBook.getParagraphs();
        logger.info("paragraphs: " + paragraphs);
        
        if (result.hasErrors()) {
            model.addAttribute("storybook", storyBook);
            return "content/storybook/create";
        } else {
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storybookDao.create(storyBook);
            
            // TODO: store RevisionEvent
 
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(contributor.getFirstName() + " just added a new StoryBook:\n" + 
                    "• Language: \"" + storyBook.getLocale().getLanguage() + "\"\n" + 
                    "• Title: \"" + storyBook.getTitle() + "\"\n" + 
                    "• Paragraphs: " + storyBook.getParagraphs() + "\n" + 
                    "See ") + "http://literacyapp.org/content/storybook/edit/" + storyBook.getId();
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
            }
            
            return "redirect:/content/storybook/list#" + storyBook.getId();
        }
    }
}
