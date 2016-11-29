package org.literacyapp.web.content.allophone;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/allophone/list")
public class AllophoneListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Allophones
        List<Allophone> allophonesGenerated = generateAllophones(contributor.getLocale());
        for (Allophone allophone : allophonesGenerated) {
            Allophone existingAllophone = allophoneDao.readByValueIpa(contributor.getLocale(), allophone.getValueIpa());
            if (existingAllophone == null) {
                allophoneDao.create(allophone);
            }
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("allophones", allophones);

        return "content/allophone/list";
    }
    
    private List<Allophone> generateAllophones(Locale locale) {
        List<Allophone> allophones = new ArrayList<>();
        
        if (locale == Locale.AR) {
            // TODO
        } else if (locale == Locale.EN) {
            // TODO
        } else if (locale == Locale.ES) {
            // TODO
        } else if (locale == Locale.SW) {
            // TODO
        }
        
        return allophones;
    }
}
