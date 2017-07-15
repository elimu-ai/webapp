package ai.elimu.web.content.letter;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter/edit")
public class LetterEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Letter letter = letterDao.read(id);
        model.addAttribute("letter", letter);
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        model.addAttribute("allophones", allophones);

        return "content/letter/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Letter letter,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        Letter existingLetter = letterDao.readByText(letter.getLocale(), letter.getText());
        if ((existingLetter != null) && !existingLetter.getId().equals(letter.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letter", letter);
            
            List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
            model.addAttribute("allophones", allophones);
            
            return "content/letter/edit";
        } else {
            letter.setTimeLastUpdate(Calendar.getInstance());
            letter.setRevisionNumber(letter.getRevisionNumber() + 1);
            letterDao.update(letter);
            
            return "redirect:/content/letter/list#" + letter.getId();
        }
    }
}
