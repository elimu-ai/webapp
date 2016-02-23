package org.literacyapp.web.content.number;

import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.Number;
import org.literacyapp.model.enums.Language;
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
    public String handleRequest(HttpSession session, Model model) {
    	logger.info("handleRequest");
        
        List<Number> numbers = numberDao.readAllOrdered(Language.ENGLISH); // TODO: fetch Contributor's chosen language
        model.addAttribute("numbers", numbers);

        return "content/number/list";
    }
}
