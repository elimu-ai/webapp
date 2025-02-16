package ai.elimu.web.content.number;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.NumberDao;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/number/delete")
public class NumberDeleteController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private NumberDao numberDao;

    @GetMapping(value = "/{id}")
    public String handleRequest(Model model, @PathVariable Long id) {
        logger.info("handleRequest");
        
//        Number number = numberDao.read(id);
//        numberDao.delete(number);

        return "redirect:/content/number/list";
    }
}
