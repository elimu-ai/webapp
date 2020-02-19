package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/edit/{storyBookId}/chapter/delete")
public class StoryBookChapterDeleteController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(@PathVariable Long storyBookId, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        StoryBookChapter storyBookChapter = storyBookChapterDao.read(id);
        logger.info("storyBookChapter: " + storyBookChapter);
        
        // Delete the chapter's paragraphs
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        logger.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
            logger.info("Deleting StoryBookParagraph with ID " + storyBookParagraph.getId());
            storyBookParagraphDao.delete(storyBookParagraph);
        }
        
        // Delete the chapter
        storyBookChapterDao.delete(storyBookChapter);

        return "redirect:/content/storybook/edit/" + storyBookId;
    }
}
