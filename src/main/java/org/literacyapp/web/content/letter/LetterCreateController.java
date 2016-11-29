package org.literacyapp.web.content.letter;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.literacyapp.dao.LetterDao;
import org.literacyapp.model.content.Letter;
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

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Letter letter = new Letter();
        letter.setRevisionNumber(1);
        model.addAttribute("letter", letter);

        return "content/letter/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Letter letter,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Letter existingLetter = letterDao.readByText(letter.getLocale(), letter.getText());
        if (existingLetter != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letter", letter);
            return "content/letter/create";
        } else {
            letter.setText(letter.getText().toLowerCase());
            letter.setTimeLastUpdate(Calendar.getInstance());
            letterDao.create(letter);
            
            return "redirect:/content/letter/list";
        }
    }
}
