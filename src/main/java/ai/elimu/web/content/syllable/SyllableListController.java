package ai.elimu.web.content.syllable;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.SyllableDao;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.enums.Language;
import ai.elimu.util.ConfigHelper;
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

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        // To ease development/testing, auto-generate Syllables
        List<Syllable> syllablesGenerated = generateSyllables(language);
        for (Syllable syllable : syllablesGenerated) {
            logger.info("syllable.getText(): " + syllable.getText());
            Syllable existingSyllable = syllableDao.readByText(syllable.getLanguage(), syllable.getText());
            if (existingSyllable == null) {
                syllableDao.create(syllable);
            }
        }
        
        List<Syllable> syllables = syllableDao.readAllOrdered(language);
        logger.info("syllables.size(): " + syllables.size());
        model.addAttribute("syllables", syllables);

        return "content/syllable/list";
    }
    
    private List<Syllable> generateSyllables(Language language) {
        List<Syllable> syllables = new ArrayList<>();
        
        // TODO
        
        return syllables;
    }
}
