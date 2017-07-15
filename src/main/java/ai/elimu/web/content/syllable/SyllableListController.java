package ai.elimu.web.content.syllable;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.enums.Locale;
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
