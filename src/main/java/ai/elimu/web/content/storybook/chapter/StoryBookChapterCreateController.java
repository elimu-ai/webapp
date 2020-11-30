package ai.elimu.web.content.storybook.chapter;

import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.PeerReviewStatus;
import java.util.Calendar;
import java.util.List;
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
            @PathVariable Long storyBookId,
            @Valid StoryBookChapter storyBookChapter,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
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
            // TODO
            
            return "redirect:/content/storybook/edit/" + storyBookId + "#ch-id-" + storyBookChapter.getId();
        }
    }
}
