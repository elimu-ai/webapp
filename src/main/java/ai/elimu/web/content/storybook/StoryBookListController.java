package ai.elimu.web.content.storybook;

import org.apache.log4j.Logger;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.ReadingLevel;
import ai.elimu.util.ConfigHelper;
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
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        model.addAttribute("storyBooksLevel1", storyBookDao.readAllOrdered(language, ReadingLevel.LEVEL1));
        model.addAttribute("storyBooksLevel2", storyBookDao.readAllOrdered(language, ReadingLevel.LEVEL2));
        model.addAttribute("storyBooksLevel3", storyBookDao.readAllOrdered(language, ReadingLevel.LEVEL3));
        model.addAttribute("storyBooksLevel4", storyBookDao.readAllOrdered(language, ReadingLevel.LEVEL4));
        model.addAttribute("storyBooksUnleveled", storyBookDao.readAllUnleveled(language));

        return "content/storybook/list";
    }
}
