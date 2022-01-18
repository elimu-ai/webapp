package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.AudioDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.enums.Platform;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/{storyBookChapterId}/paragraph/create")
public class StoryBookParagraphCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao; 
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private StoryBooksJsonService storyBooksJsonService;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long storyBookChapterId) {
    	logger.info("handleRequest");
         
        StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
        
        StoryBookChapter storyBookChapter = storyBookChapterDao.read(storyBookChapterId);
        storyBookParagraph.setStoryBookChapter(storyBookChapter);
        
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        storyBookParagraph.setSortOrder(storyBookParagraphs.size());
        
        model.addAttribute("storyBookParagraph", storyBookParagraph);
        
        model.addAttribute("audios", audioDao.readAllOrderedByTitle());
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        return "content/storybook/paragraph/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid StoryBookParagraph storyBookParagraph,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (result.hasErrors()) {
            model.addAttribute("storyBookParagraph", storyBookParagraph);
            model.addAttribute("audios", audioDao.readAllOrderedByTitle());
            model.addAttribute("timeStart", request.getParameter("timeStart"));
            return "content/storybook/paragraph/create";
        } else {
            storyBookParagraphDao.create(storyBookParagraph);
            
            // Update the storybook's metadata
            StoryBook storyBook = storyBookParagraph.getStoryBookChapter().getStoryBook();
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
            storyBookContributionEvent.setComment("Created storybook paragraph in chapter " + (storyBookParagraph.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
            storyBookContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            storyBookContributionEvent.setPlatform(Platform.WEBAPP);
            storyBookContributionEventDao.create(storyBookContributionEvent);
            
            String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
            DiscordHelper.postChatMessage(
                    "Storybook paragraph created: " + contentUrl,
                    "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                    "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
                    null
            );
            
            // Refresh the REST API cache
            storyBooksJsonService.refreshStoryBooksJSONArray();
            
            return "redirect:/content/storybook/edit/" + 
                    storyBookParagraph.getStoryBookChapter().getStoryBook().getId() + 
                    "#ch-id-" + storyBookParagraph.getStoryBookChapter().getId();
        }
    }
}
