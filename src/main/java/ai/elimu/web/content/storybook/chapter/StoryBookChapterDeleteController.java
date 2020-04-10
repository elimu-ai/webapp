package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.Role;
import ai.elimu.util.ConfigHelper;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/delete")
public class StoryBookChapterDeleteController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(HttpSession session, @PathVariable Long storyBookId, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor: " + contributor);
        logger.info("contributor.getRoles(): " + contributor.getRoles());
        if (!contributor.getRoles().contains(Role.ADMIN)) {
            // TODO: return HttpStatus.FORBIDDEN
            throw new IllegalAccessError("Missing role for access");
        }
        
        StoryBookChapter storyBookChapterToBeDeleted = storyBookChapterDao.read(id);
        logger.info("storyBookChapterToBeDeleted: " + storyBookChapterToBeDeleted);
        
        // Delete the chapter's paragraphs
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapterToBeDeleted);
        logger.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
            logger.info("Deleting StoryBookParagraph with ID " + storyBookParagraph.getId());
            storyBookParagraphDao.delete(storyBookParagraph);
        }
        
        // Delete the chapter
        logger.info("Deleting StoryBookChapter with ID " + storyBookChapterToBeDeleted.getId());
        storyBookChapterDao.delete(storyBookChapterToBeDeleted);
        
        // Delete the chapter's image (if any)
        StoryBook storyBook = storyBookChapterToBeDeleted.getStoryBook();
        String chapterImageTitle = "storybook-" + storyBook.getId() + "-ch-" + (storyBookChapterToBeDeleted.getSortOrder() + 1);
        logger.info("chapterImageTitle: \"" + chapterImageTitle + "\"");
        if (StringUtils.isNotBlank(chapterImageTitle)) {
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
            Image chapterImage = imageDao.read(chapterImageTitle, language);
            logger.info("chapterImage: " + chapterImage);
            if (chapterImage != null) {
                logger.warn("Deleting the chapter image from the database");
                imageDao.delete(chapterImage);
            }
        }
        
        // Update the StoryBook's metadata
        storyBook.setTimeLastUpdate(Calendar.getInstance());
        storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
        storyBookDao.update(storyBook);
        
        // Update the sorting order of the remaining chapters
        logger.info("storyBookChapterToBeDeleted.getSortOrder(): " + storyBookChapterToBeDeleted.getSortOrder());
        List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
        logger.info("storyBookChapters.size(): " + storyBookChapters.size());
        for (StoryBookChapter storyBookChapter : storyBookChapters) {
            logger.info("storyBookChapter.getId(): " + storyBookChapter.getId() + ", storyBookChapter.getSortOrder(): " + storyBookChapter.getSortOrder());
            if (storyBookChapter.getSortOrder() > storyBookChapterToBeDeleted.getSortOrder()) {
                // Reduce sort order by 1
                storyBookChapter.setSortOrder(storyBookChapter.getSortOrder() - 1);
                storyBookChapterDao.update(storyBookChapter);
                logger.info("storyBookChapter.getSortOrder() (after update): " + storyBookChapter.getSortOrder());
            }
        }

        return "redirect:/content/storybook/edit/" + storyBookId;
    }
}
