package ai.elimu.web.content.storybook;

import java.util.Calendar;
import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/create")
public class StoryBookCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storybookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        StoryBook storyBook = new StoryBook();
        model.addAttribute("storyBook", storyBook);
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        List<Image> coverImages = imageDao.readAllOrdered();
        model.addAttribute("coverImages", coverImages);
        
        model.addAttribute("readingLevels", ReadingLevel.values());

        return "content/storybook/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid StoryBook storyBook,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpSession session) {
        logger.info("handleSubmit");
        
        StoryBook existingStoryBook = storybookDao.readByTitle(storyBook.getTitle());
        if (existingStoryBook != null) {
            result.rejectValue("title", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("storybook", storyBook);
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            model.addAttribute("contentLicenses", ContentLicense.values());
            
            List<Image> coverImages = imageDao.readAllOrdered();
            model.addAttribute("coverImages", coverImages);
            
            model.addAttribute("readingLevels", ReadingLevel.values());
            
            return "content/storybook/create";
        } else {
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storybookDao.create(storyBook);
            
            StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
            storyBookContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            storyBookContributionEvent.setTimestamp(Calendar.getInstance());
            storyBookContributionEvent.setStoryBook(storyBook);
            storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            storyBookContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            storyBookContributionEventDao.create(storyBookContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
                String embedThumbnailUrl = null;
                if (storyBook.getCoverImage() != null) {
                    embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + storyBook.getCoverImage().getId() + "_r" + storyBook.getCoverImage().getRevisionNumber() + "." + storyBook.getCoverImage().getImageFormat().toString().toLowerCase();
                }
                DiscordHelper.sendChannelMessage(
                        "Storybook created: " + contentUrl,
                        "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                        "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
                        null,
                        embedThumbnailUrl
                );
            }
            
            return "redirect:/content/storybook/list#" + storyBook.getId();
        }
    }
}
