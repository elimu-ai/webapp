package ai.elimu.web.content.letter;

import java.util.Calendar;
import java.util.List;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/create")
public class LetterCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        Letter letter = new Letter();
        model.addAttribute("letter", letter);
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(language);
        model.addAttribute("allophones", allophones);

        return "content/letter/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Letter letter,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        Letter existingLetter = letterDao.readByText(language, letter.getText());
        if (existingLetter != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letter", letter);
            
            List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(language);
            model.addAttribute("allophones", allophones);
            
            return "content/letter/create";
        } else {
            letter.setTimeLastUpdate(Calendar.getInstance());
            letterDao.create(letter);
            
            return "redirect:/content/letter/list#" + letter.getId();
        }
    }
}
