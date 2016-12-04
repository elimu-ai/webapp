package org.literacyapp.web.content.storybook;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.util.WordFrequencyHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/edit")
public class StoryBookEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id, HttpSession session) {
    	logger.info("handleRequest");
        
        StoryBook storyBook = storyBookDao.read(id);
        model.addAttribute("storyBook", storyBook);
        
        Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(storyBook);
        model.addAttribute("wordFrequencyMap", wordFrequencyMap);

        return "content/storybook/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid StoryBook storyBook,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        StoryBook existingStoryBook = storyBookDao.readByTitle(storyBook.getLocale(), storyBook.getTitle());
        if ((existingStoryBook != null) && !existingStoryBook.getId().equals(storyBook.getId())) {
            result.rejectValue("title", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("storyBook", storyBook);
            
            Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(storyBook);
            model.addAttribute("wordFrequencyMap", wordFrequencyMap);
            
            return "content/storybook/edit";
        } else {
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
            storyBookDao.update(storyBook);
            
            // TODO: store RevisionEvent
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(contributor.getFirstName() + " just edited a StoryBook:\n" + 
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
