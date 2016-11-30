package org.literacyapp.web.content.word;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Allophone;
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
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Word word = new Word();
        model.addAttribute("word", word);
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("allophones", allophones);

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
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrdered(contributor.getLocale());
        
        // Verify that only valid Allophones are used
        Set<String> allophoneSet = new HashSet<String>();
        for (Allophone allophone : allophones) {
            allophoneSet.add(allophone.getValueIpa());
        }
        if (StringUtils.isNotBlank(word.getPhonetics())) {
            for (char allophoneCharacter : word.getPhonetics().toCharArray()) {
                String allophone = String.valueOf(allophoneCharacter);
                logger.info("allophone: " + allophone);
                if (!allophoneSet.contains(allophone)) {
                    result.rejectValue("phonetics", "Invalid");
                    break;
                }
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("allophones", allophones);
            return "content/word/create";
        } else {
            word.setText(word.getText().toLowerCase());
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
            
            return "redirect:/content/word/list";
        }
    }
}
