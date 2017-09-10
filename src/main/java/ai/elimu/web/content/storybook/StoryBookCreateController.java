package ai.elimu.web.content.storybook;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.GradeLevel;
import ai.elimu.model.enums.Team;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
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
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        StoryBook storyBook = new StoryBook();
        model.addAttribute("storyBook", storyBook);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        List<Image> coverImages = imageDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("coverImages", coverImages);
        
        model.addAttribute("gradeLevels", GradeLevel.values());

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
            
            model.addAttribute("contentLicenses", ContentLicense.values());
            
            List<Image> coverImages = imageDao.readAllOrdered(contributor.getLocale());
            model.addAttribute("coverImages", coverImages);
            
            model.addAttribute("gradeLevels", GradeLevel.values());
            
            return "content/storybook/create";
        } else {
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storybookDao.create(storyBook);
            
            // TODO: store RevisionEvent
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(contributor.getFirstName() + " just added a new StoryBook:\n" + 
                     "• Language: \"" + storyBook.getLocale().getLanguage() + "\"\n" + 
                     "• Title: \"" + storyBook.getTitle() + "\"\n" + 
                     "• Grade level: " + storyBook.getGradeLevel() + "\n" +
                     "• Paragraphs: " + storyBook.getParagraphs() + "\n" + 
                     "See ") + "http://elimu.ai/content/storybook/edit/" + storyBook.getId();
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.CONTENT_CREATION), text, iconUrl, null);
            }
            
            return "redirect:/content/storybook/list#" + storyBook.getId();
        }
    }
}
