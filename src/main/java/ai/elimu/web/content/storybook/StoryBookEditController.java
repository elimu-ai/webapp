package ai.elimu.web.content.storybook;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.enums.GradeLevel;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.LetterFrequencyHelper;
import ai.elimu.util.WordFrequencyHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/storybook/edit")
public class StoryBookEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private ImageDao imageDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        StoryBook storyBook = storyBookDao.read(id);
        model.addAttribute("storyBook", storyBook);
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        List<Image> coverImages = imageDao.readAllOrdered(language);
        model.addAttribute("coverImages", coverImages);
        
        model.addAttribute("gradeLevels", GradeLevel.values());
        
        Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(storyBook);
        model.addAttribute("wordFrequencyMap", wordFrequencyMap);
        
        Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(storyBook);
        model.addAttribute("letterFrequencyMap", letterFrequencyMap);
        
        return "content/storybook/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid StoryBook storyBook,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
    	logger.info("handleSubmit");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        StoryBook existingStoryBook = storyBookDao.readByTitle(language, storyBook.getTitle());
        if ((existingStoryBook != null) && !existingStoryBook.getId().equals(storyBook.getId())) {
            result.rejectValue("title", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("storyBook", storyBook);
            
            model.addAttribute("contentLicenses", ContentLicense.values());
            
            List<Image> coverImages = imageDao.readAllOrdered(language);
            model.addAttribute("coverImages", coverImages);
            
            model.addAttribute("gradeLevels", GradeLevel.values());
            
            Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(storyBook);
            model.addAttribute("wordFrequencyMap", wordFrequencyMap);
            
            Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(storyBook);
            model.addAttribute("letterFrequencyMap", letterFrequencyMap);
            
            return "content/storybook/edit";
        } else {
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
            storyBookDao.update(storyBook);
            
            return "redirect:/content/storybook/list#" + storyBook.getId();
        }
    }
}
