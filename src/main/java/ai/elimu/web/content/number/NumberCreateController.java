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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/create")
public class NumberCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(
            Model model) {
    	logger.info("handleRequest");
        
        Number number = new Number();
        model.addAttribute("number", number);
        
        List<Word> words = wordDao.readAllOrdered();
        model.addAttribute("words", words);
        
        return "content/number/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Number number,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Number existingNumber = numberDao.readByValue(number.getValue());
        if (existingNumber != null) {
            result.rejectValue("value", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("number", number);

            List<Word> words = wordDao.readAllOrdered();
            model.addAttribute("words", words);
            
            return "content/number/create";
        } else {            
            number.setTimeLastUpdate(Calendar.getInstance());
            numberDao.create(number);
            
            return "redirect:/content/number/list#" + number.getId();
        }
    }
}
