package ai.elimu.web.content.storybook.paragraph;

import org.apache.log4j.Logger;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.enums.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/paragraph/edit")
public class StoryBookParagraphEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        StoryBookParagraph storyBookParagraph = storyBookParagraphDao.read(id);
        logger.info("storyBookParagraph: " + storyBookParagraph);
        
        model.addAttribute("storyBookParagraph", storyBookParagraph);
        
        return "content/storybook/paragraph/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid StoryBookParagraph storyBookParagraph,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        logger.info("contributor.getRoles(): " + contributor.getRoles());
        if (!contributor.getRoles().contains(Role.ADMIN)) {
            // TODO: return HttpStatus.FORBIDDEN
            throw new IllegalAccessError("Missing role for access");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("storyBookParagraph", storyBookParagraph);
            return "content/storybook/paragraph/edit";
        } else {
            storyBookParagraphDao.update(storyBookParagraph);
            return "redirect:/content/storybook/edit/" + 
                    storyBookParagraph.getStoryBookChapter().getStoryBook().getId() + 
                    "#ch-id-" + storyBookParagraph.getStoryBookChapter().getId();
        }
    }
}
