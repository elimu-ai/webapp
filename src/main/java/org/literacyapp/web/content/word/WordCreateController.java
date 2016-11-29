package org.literacyapp.web.content.word;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.content.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/create")
public class WordCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Word word = new Word();
        word.setRevisionNumber(1);
        model.addAttribute("word", word);

        return "content/word/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Word word,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByText(word.getLocale(), word.getText());
        if (existingWord != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            return "content/word/create";
        } else {
            word.setText(word.getText().toLowerCase());
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
            
            return "redirect:/content/word/list";
        }
    }
}
