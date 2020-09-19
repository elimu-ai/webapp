package ai.elimu.web.content.letter_to_allophone_mapping;

import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter-to-allophone-mapping/edit")
public class LetterToAllophoneMappingEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        LetterToAllophoneMapping letterToAllophoneMapping = letterToAllophoneMappingDao.read(id);
        model.addAttribute("letterToAllophoneMapping", letterToAllophoneMapping);
        
        List<Letter> letters = letterDao.readAllOrdered();
        model.addAttribute("letters", letters);
        
        List<Allophone> allophones = allophoneDao.readAllOrdered();
        model.addAttribute("allophones", allophones);

        return "content/letter-to-allophone-mapping/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid LetterToAllophoneMapping letterToAllophoneMapping,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
//        LetterToAllophoneMapping existingLetterToAllophoneMapping = letterToAllophoneMappingDao.read(letterToAllophoneMapping.getLetter(), letterToAllophoneMapping.getAllophones());
//        if (existingLetterToAllophoneMapping != null) {
//            result.rejectValue("letter", "NonUnique");
//        }
        
        if (result.hasErrors()) {
            model.addAttribute("letterToAllophoneMapping", letterToAllophoneMapping);
            
            List<Letter> letters = letterDao.readAllOrdered();
            model.addAttribute("letters", letters);
            
            List<Allophone> allophones = allophoneDao.readAllOrdered();
            model.addAttribute("allophones", allophones);
            
            return "content/letter-to-allophone-mapping/edit";
        } else {
            letterToAllophoneMappingDao.update(letterToAllophoneMapping);
            
            return "redirect:/content/letter-to-allophone-mapping/list#" + letterToAllophoneMapping.getId();
        }
    }
}
