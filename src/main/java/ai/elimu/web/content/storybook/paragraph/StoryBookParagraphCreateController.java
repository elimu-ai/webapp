package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.StoryBookChapterDao;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
    private StoryBookChapterDao storyBookChapterDao; 
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long storyBookChapterId) {
    	logger.info("handleRequest");
         
        StoryBookParagraph storyBookParagraph = new StoryBookParagraph();
        
        StoryBookChapter storyBookChapter = storyBookChapterDao.read(storyBookChapterId);
        storyBookParagraph.setStoryBookChapter(storyBookChapter);
        
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        storyBookParagraph.setSortOrder(storyBookParagraphs.size());
        
        model.addAttribute("storyBookParagraph", storyBookParagraph);
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        return "content/storybook/paragraph/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            @Valid StoryBookParagraph storyBookParagraph,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        if (result.hasErrors()) {
            model.addAttribute("storyBookParagraph", storyBookParagraph);
            model.addAttribute("timeStart", request.getParameter("timeStart"));
            return "content/storybook/paragraph/create";
        } else {
            storyBookParagraphDao.create(storyBookParagraph);
            
            // Update the storybook's metadata
            // TODO
            
            // Store contribution event
            // TODO
            
            return "redirect:/content/storybook/edit/" + 
                    storyBookParagraph.getStoryBookChapter().getStoryBook().getId() + 
                    "#ch-id-" + storyBookParagraph.getStoryBookChapter().getId();
        }
    }
}
