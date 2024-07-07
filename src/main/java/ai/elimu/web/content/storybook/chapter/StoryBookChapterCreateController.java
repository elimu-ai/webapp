package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.entity.enums.Platform;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/create")
public class StoryBookChapterCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            @PathVariable Long storyBookId,
            Model model
    ) {
    	logger.info("handleRequest");
        
        StoryBookChapter storyBookChapter = new StoryBookChapter();
        
        StoryBook storyBook = storyBookDao.read(storyBookId);
        storyBookChapter.setStoryBook(storyBook);
        
        List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
        storyBookChapter.setSortOrder(storyBookChapters.size());
        
        model.addAttribute("storyBookChapter", storyBookChapter);
        
        List<Image> images = imageDao.readAllOrdered();
        model.addAttribute("images", images);
        
        return "content/storybook/chapter/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @PathVariable Long storyBookId,
            @Valid StoryBookChapter storyBookChapter,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (result.hasErrors()) {
            model.addAttribute("storyBookChapter", storyBookChapter);

            List<Image> images = imageDao.readAllOrdered();
            model.addAttribute("images", images);
            
            return "content/storybook/chapter/create";
        } else {
            storyBookChapterDao.create(storyBookChapter);
            
            // Update the storybook's metadata
            StoryBook storyBook = storyBookChapter.getStoryBook();
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
            storyBook.setPeerReviewStatus(PeerReviewStatus.PENDING);
            storyBookDao.update(storyBook);
            
            // Store contribution event
            StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
            storyBookContributionEvent.setContributor(contributor);
            storyBookContributionEvent.setTime(Calendar.getInstance());
            storyBookContributionEvent.setStoryBook(storyBook);
            storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookContributionEvent.setComment("Created storybook chapter " + (storyBookChapter.getSortOrder() + 1) + " (🤖 auto-generated comment)");
            storyBookContributionEvent.setTimeSpentMs(0L);
            storyBookContributionEvent.setPlatform(Platform.WEBAPP);
            storyBookContributionEventDao.create(storyBookContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
                String embedThumbnailUrl = null;
                if (storyBook.getCoverImage() != null) {
                    embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + storyBook.getCoverImage().getId() + "_r" + storyBook.getCoverImage().getRevisionNumber() + "." + storyBook.getCoverImage().getImageFormat().toString().toLowerCase();
                }
                DiscordHelper.sendChannelMessage(
                        "Storybook chapter created: " + contentUrl,
                        "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                        "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
                        null,
                        embedThumbnailUrl
                );
            }
            
            return "redirect:/content/storybook/edit/" + storyBookId + "#ch-id-" + storyBookChapter.getId();
        }
    }
}
