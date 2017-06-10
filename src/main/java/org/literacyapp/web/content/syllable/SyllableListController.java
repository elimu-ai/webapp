package org.literacyapp.web.content.syllable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.literacyapp.dao.StoryBookDao;
import org.literacyapp.dao.SyllableDao;
import org.literacyapp.dao.WordDao;
import org.literacyapp.model.Contributor;
import org.literacyapp.model.content.StoryBook;
import org.literacyapp.model.content.Syllable;
import org.literacyapp.model.content.Word;
import org.literacyapp.model.enums.Locale;
import org.literacyapp.util.SyllableFrequencyHelper;
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
        if (syllables.isEmpty()) {
            triggerTaskCode(contributor.getLocale());
            syllables = syllableDao.readAllOrdered(contributor.getLocale());
        }
        model.addAttribute("syllables", syllables);

        return "content/syllable/list";
    }
    
    // TODO: remove once task has executed at least once successfully
    private void triggerTaskCode(Locale locale) {
        logger.info("Calculating usage count for Syllables with locale " + locale);
            
        Map<String, Integer> syllableFrequencyMap = new HashMap<>();

        List<StoryBook> storyBooks = storyBookDao.readAllOrdered(locale);
        logger.info("storyBooks.size(): " + storyBooks.size());
        for (StoryBook storyBook : storyBooks) {
            logger.info("storyBook.getTitle(): " + storyBook.getTitle());

            Map<String, Integer> syllableFrequencyMapForBook = SyllableFrequencyHelper.getSyllableFrequency(storyBook);
            for (String key : syllableFrequencyMapForBook.keySet()) {
                String syllableText = key;
                int syllableFrequency = syllableFrequencyMapForBook.get(key);
                if (!syllableFrequencyMap.containsKey(syllableText)) {
                    syllableFrequencyMap.put(syllableText, syllableFrequency);
                } else {
                    syllableFrequencyMap.put(syllableText, syllableFrequencyMap.get(syllableText) + syllableFrequency);
                }
            }
        }

        logger.info("syllableFrequencyMap: " + syllableFrequencyMap);

        for (String key : syllableFrequencyMap.keySet()) {
            String syllableText = key;
            
            // Skip syllables that are actual words
            // TODO: add logic to Word editing
            Word word = wordDao.readByText(locale, syllableText);
            if (word != null) {
                continue;
            }

            // Skip syllables that are not digrams
            // TODO: add support for trigrams
            if (syllableText.length() != 2) {
                continue;
            }
            
            Syllable existingSyllable = syllableDao.readByText(locale, syllableText);
            if (existingSyllable == null) {
                Syllable syllable = new Syllable();
                syllable.setLocale(locale);
                syllable.setTimeLastUpdate(Calendar.getInstance());
                syllable.setText(syllableText);
                syllable.setUsageCount(syllableFrequencyMap.get(syllableText));
                syllableDao.create(syllable);
            } else {
                existingSyllable.setUsageCount(syllableFrequencyMap.get(syllableText));
                syllableDao.update(existingSyllable);
            }
        }
    }
    
    private List<Syllable> generateSyllables(Locale locale) {
        List<Syllable> syllables = new ArrayList<>();
        
        // TODO
        
        return syllables;
    }
}
