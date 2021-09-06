package ai.elimu.web.content.storybook;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.v2.enums.ReadingLevel;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/list")
public class StoryBookListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        model.addAttribute("storyBooksLevel1", storyBookDao.readAllOrdered(ReadingLevel.LEVEL1));
        model.addAttribute("storyBooksLevel2", storyBookDao.readAllOrdered(ReadingLevel.LEVEL2));
        model.addAttribute("storyBooksLevel3", storyBookDao.readAllOrdered(ReadingLevel.LEVEL3));
        model.addAttribute("storyBooksLevel4", storyBookDao.readAllOrdered(ReadingLevel.LEVEL4));
        model.addAttribute("storyBooksUnleveled", storyBookDao.readAllUnleveled());

        return "content/storybook/list";
    }
}
