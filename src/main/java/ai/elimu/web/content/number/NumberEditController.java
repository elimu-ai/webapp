package ai.elimu.web.content.number;

import java.util.Calendar;
import java.util.List;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/edit")
public class NumberEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            Model model, 
            @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Number number = numberDao.read(id);
        model.addAttribute("number", number);
        
        List<Word> words = wordDao.readAllOrdered();
        model.addAttribute("words", words);

        return "content/number/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Number number,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Number existingNumber = numberDao.readByValue(number.getValue());
        if ((existingNumber != null) && !existingNumber.getId().equals(number.getId())) {
            result.rejectValue("value", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("number", number);
            
            List<Word> words = wordDao.readAllOrdered();
            model.addAttribute("words", words);
            
            return "content/number/edit";
        } else {
            number.setTimeLastUpdate(Calendar.getInstance());
            number.setRevisionNumber(number.getRevisionNumber() + 1);
            numberDao.update(number);
            
            return "redirect:/content/number/list#" + number.getId();
        }
    }
}
