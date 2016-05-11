package org.literacyapp.web.content.number;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.Number;
import org.literacyapp.model.enums.Language;
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

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Number number = new Number();
        model.addAttribute("number", number);

        return "content/number/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Number number,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        if (number.getLanguage() == Language.ARABIC) {
            if (StringUtils.isBlank(number.getSymbol())) {
                result.rejectValue("symbol", "NotNull");
            }
        }
        
        Number existingNumber = numberDao.readByValue(number.getLanguage(), number.getValue()); // TODO: fetch Contributor's chosen language
        if (existingNumber != null) {
            result.rejectValue("value", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("number", number);
            return "content/number/create";
        } else {
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            number.setContributor(contributor);
            number.setCalendar(Calendar.getInstance());
            numberDao.create(number);
            
            // TODO: store event
            
            return "redirect:/content";
        }
    }
}
