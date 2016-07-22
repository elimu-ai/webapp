package org.literacyapp.web.content.number;

import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.literacyapp.dao.NumberDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Number;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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
        number.setRevisionNumber(1);
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
        
        if (number.getLocale() == Locale.AR) {
            if (StringUtils.isBlank(number.getSymbol())) {
                result.rejectValue("symbol", "NotNull");
            }
        }
        
        Number existingNumber = numberDao.readByValue(number.getLocale(), number.getValue()); // TODO: fetch Contributor's chosen locale
        if (existingNumber != null) {
            result.rejectValue("value", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("number", number);
            return "content/number/create";
        } else {            
            number.setTimeLastUpdate(Calendar.getInstance());
            numberDao.create(number);
            
            Contributor contributor = (Contributor) session.getAttribute("contributor");
            
            ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
            contentCreationEvent.setContributor(contributor);
            contentCreationEvent.setContent(number);
            contentCreationEvent.setCalendar(Calendar.getInstance());
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                String text = URLEncoder.encode(
                        contributor.getFirstName() + " just added a new Number:\n" + 
                        "• Language: \"" + number.getLocale().getLanguage() + "\"\n" + 
                        "• Value: \"" + number.getValue() + "\"" + (!StringUtils.isEmpty(number.getSymbol()) ? " (" + number.getSymbol() + ")" : "") + "\n" +
                        "• Word: \"" + number.getWord() + "\"\n" + 
                        "See ") + "http://literacyapp.org/content/number/list";
                String iconUrl = contributor.getImageUrl();
                SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
            }
            
            return "redirect:/content/number/list";
        }
    }
}
