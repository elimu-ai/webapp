package org.literacyapp.web.content.allophone;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.AllophoneDao;
import org.literacyapp.dao.ContentCreationEventDao;
import org.literacyapp.model.content.Allophone;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.literacyapp.model.contributor.ContentCreationEvent;
import org.literacyapp.model.enums.Environment;
import org.literacyapp.model.enums.Team;
import org.literacyapp.util.SlackApiHelper;
import org.literacyapp.web.context.EnvironmentContextLoaderListener;
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
    
    @Autowired
    private ContentCreationEventDao contentCreationEventDao;

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

                ContentCreationEvent contentCreationEvent = new ContentCreationEvent();
                contentCreationEvent.setContributor(contributor);
                contentCreationEvent.setContent(allophone);
                contentCreationEvent.setCalendar(Calendar.getInstance());
                contentCreationEventDao.create(contentCreationEvent);
                
                if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                    String text = URLEncoder.encode(
                            contributor.getFirstName() + " just added a new Allophone (speech sound):\n" + 
                            "• Language: \"" + allophone.getLocale().getLanguage() + "\"\n" + 
                            "• IPA: \"" + allophone.getValueIpa() + "\"\n" + 
                            "• X-SAMPA: \"" + allophone.getValueSampa() + "\"\n" + 
                            "See ") + "http://literacyapp.org/content/allophone/list";
                    String iconUrl = contributor.getImageUrl();
                    SlackApiHelper.postMessage(Team.CONTENT_CREATION, text, iconUrl, null);
                }
            }
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrdered(contributor.getLocale());
        model.addAttribute("allophones", allophones);

        return "content/allophone/list";
    }
    
    private List<Allophone> generateAllophones(Locale locale) {
        List<Allophone> allophones = new ArrayList<>();
        
        // TODO
        
        return allophones;
    }
}
