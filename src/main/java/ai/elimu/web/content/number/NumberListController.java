package ai.elimu.web.content.number;

import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.NumberDao;
import ai.elimu.model.content.Number;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/list")
public class NumberListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private NumberDao numberDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        List<Number> numbers = numberDao.readAllOrdered(language);
        model.addAttribute("numbers", numbers);

        return "content/number/list";
    }
}
