package org.literacyapp.web.content.storybook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/list")
public class StoryBookListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate StoryBooks
        List<StoryBook> storyBooksGenerated = generateStoryBooks(contributor.getLocale());
        for (StoryBook storyBook : storyBooksGenerated) {
            StoryBook existingStoryBook = storyBookDao.readByTitle(storyBook.getLocale(), storyBook.getTitle());
            if (existingStoryBook == null) {
                storyBookDao.create(storyBook);
            }
        }
        
        List<StoryBook> storyBooks = storyBookDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("storyBooks", storyBooks);

        return "content/storybook/list";
    }
    
    private List<StoryBook> generateStoryBooks(Locale locale) {
        List<StoryBook> storyBooks = new ArrayList<>();
        
        // TODO
        
        return storyBooks;
    }
}
