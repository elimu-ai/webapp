package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.AudioDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.multimedia.Audio;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.PeerReviewStatus;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.enums.Role;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/paragraph/delete")
public class StoryBookParagraphDeleteController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private StoryBooksJsonService storyBooksJsonService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(HttpSession session, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor.getRoles(): " + contributor.getRoles());
        if (!contributor.getRoles().contains(Role.EDITOR)) {
            // TODO: return HttpStatus.FORBIDDEN
            throw new IllegalAccessError("Missing role for access");
        }
        
        StoryBookParagraph storyBookParagraphToBeDeleted = storyBookParagraphDao.read(id);
        logger.info("storyBookParagraphToBeDeleted: " + storyBookParagraphToBeDeleted);
        logger.info("storyBookParagraphToBeDeleted.getSortOrder(): " + storyBookParagraphToBeDeleted.getSortOrder());
        
        String paragraphTextBeforeDeletion = storyBookParagraphToBeDeleted.getOriginalText();
        
        // Delete the paragraph's reference from corresponding audios (if any)
        List<Audio> paragraphAudios = audioDao.readAll(storyBookParagraphToBeDeleted);
        for (Audio paragraphAudio : paragraphAudios) {
            paragraphAudio.setStoryBookParagraph(null);
            audioDao.update(paragraphAudio);
        }
        
        // Delete the paragraph
        logger.info("Deleting StoryBookParagraph with ID " + storyBookParagraphToBeDeleted.getId());
        storyBookParagraphDao.delete(storyBookParagraphToBeDeleted);
        
        // Update the storybook's metadata
        StoryBook storyBook = storyBookParagraphToBeDeleted.getStoryBookChapter().getStoryBook();
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
        storyBookContributionEvent.setComment("Deleted storybook paragraph in chapter " + (storyBookParagraphToBeDeleted.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
        storyBookContributionEvent.setParagraphTextBefore(paragraphTextBeforeDeletion);
        storyBookContributionEvent.setTimeSpentMs(0L);
        storyBookContributionEvent.setPlatform(Platform.WEBAPP);
        storyBookContributionEventDao.create(storyBookContributionEvent);
        
        String contentUrl = "http://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
        DiscordHelper.postChatMessage(
                "Storybook paragraph deleted: " + contentUrl,
                "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
                null
        );
        
        // Update the sorting order of the remaining paragraphs
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookParagraphToBeDeleted.getStoryBookChapter());
        logger.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
            logger.info("storyBookParagraph.getId(): " + storyBookParagraph.getId() + ", storyBookParagraph.getSortOrder(): " + storyBookParagraph.getSortOrder());
            if (storyBookParagraph.getSortOrder() > storyBookParagraphToBeDeleted.getSortOrder()) {
                // Reduce sort order by 1
                storyBookParagraph.setSortOrder(storyBookParagraph.getSortOrder() - 1);
                storyBookParagraphDao.update(storyBookParagraph);
                logger.info("storyBookParagraph.getSortOrder() (after update): " + storyBookParagraph.getSortOrder());
            }
        }
        
        // Refresh the REST API cache
        storyBooksJsonService.refreshStoryBooksJSONArray();
        
        return "redirect:/content/storybook/edit/" + storyBook.getId() + "#ch-id-" + storyBookParagraphToBeDeleted.getStoryBookChapter().getId();
    }
}
