package org.literacyapp.web.content.syllable;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.dao.SyllableDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.Syllable;
import org.literacyapp.model.enums.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/syllable/list")
public class SyllableListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private SyllableDao syllableDao;
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        // To ease development/testing, auto-generate Syllables
        List<Syllable> syllablesGenerated = generateSyllables(contributor.getLocale());
        for (Syllable syllable : syllablesGenerated) {
            logger.info("syllable.getText(): " + syllable.getText());
            Syllable existingSyllable = syllableDao.readByText(syllable.getLocale(), syllable.getText());
            if (existingSyllable == null) {
                syllableDao.create(syllable);
            }
        }
        
        List<Syllable> syllables = syllableDao.readAllOrdered(contributor.getLocale());
        logger.info("syllables.size(): " + syllables.size());
        model.addAttribute("syllables", syllables);

        return "content/syllable/list";
    }
    
    private List<Syllable> generateSyllables(Locale locale) {
        List<Syllable> syllables = new ArrayList<>();
        
        // TODO
        
        return syllables;
    }
}
