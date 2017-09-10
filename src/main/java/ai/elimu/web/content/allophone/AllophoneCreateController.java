package ai.elimu.web.content.allophone;

import java.util.Calendar;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.allophone.SoundType;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/allophone/create")
public class AllophoneCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private MessageSource messageSource;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Allophone allophone = new Allophone();
        model.addAttribute("allophone", allophone);
        
        model.addAttribute("soundTypes", SoundType.values());

        return "content/allophone/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Allophone allophone,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (StringUtils.isNotBlank(allophone.getValueIpa())) {
            Allophone existingAllophone = allophoneDao.readByValueIpa(allophone.getLocale(), allophone.getValueIpa());
            if (existingAllophone != null) {
                result.rejectValue("valueIpa", "NonUnique");
            }
        }
        
        if (StringUtils.isNotBlank(allophone.getValueSampa())) {
            Allophone existingAllophone = allophoneDao.readByValueSampa(allophone.getLocale(), allophone.getValueSampa());
            if (existingAllophone != null) {
                result.rejectValue("valueSampa", "NonUnique");
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("allophone", allophone);
            model.addAttribute("soundTypes", SoundType.values());
            return "content/allophone/create";
        } else {
            allophone.setTimeLastUpdate(Calendar.getInstance());
            allophoneDao.create(allophone);
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                     contributor.getFirstName() + " just created an Allophone:\n" + 
                     "• Language: \"" + allophone.getLocale().getLanguage() + "\"\n" +  
                     "• IPA: /" + allophone.getValueIpa() + "/\n" + 
                     "• X-SAMPA: \"" + allophone.getValueSampa() + "\"\n" + 
                     "• Sound type: \"" + allophone.getSoundType() + "\"\n" +         
                     "See ") + "http://elimu.ai/content/allophone/edit/" + allophone.getId();
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.CONTENT_CREATION), text, iconUrl, null);
             }
            
            return "redirect:/content/allophone/list#" + allophone.getId();
        }
    }
}
