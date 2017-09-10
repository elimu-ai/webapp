package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordRevisionEventDao;
import ai.elimu.model.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.WordRevisionEvent;
import ai.elimu.model.enums.Environment;
import ai.elimu.model.enums.Team;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.util.SlackApiHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/create")
public class WordCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private WordRevisionEventDao wordRevisionEventDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, HttpSession session) {
    	logger.info("handleRequest");
        
        Word word = new Word();
        model.addAttribute("word", word);
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        model.addAttribute("allophones", allophones);
        
        model.addAttribute("wordTypes", WordType.values());
        
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());

        return "content/word/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Word word,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByText(word.getLocale(), word.getText());
        if (existingWord != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLocale());
        
        // Verify that only valid Allophones are used
        String allAllophonesCombined = "";
        for (Allophone allophone : allophones) {
            allAllophonesCombined += allophone.getValueIpa();
        }
        if (StringUtils.isNotBlank(word.getPhonetics())) {
            for (char allophoneCharacter : word.getPhonetics().toCharArray()) {
                String allophone = String.valueOf(allophoneCharacter);
                if (!allAllophonesCombined.contains(allophone)) {
                    result.rejectValue("phonetics", "Invalid");
                    break;
                }
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("allophones", allophones);
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            return "content/word/create";
        } else {
            if (!"I".equals(word.getText())) {
                word.setText(word.getText().toLowerCase());
            }
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
 
            WordRevisionEvent wordRevisionEvent = new WordRevisionEvent();
            wordRevisionEvent.setContributor(contributor);
            wordRevisionEvent.setCalendar(Calendar.getInstance());
            wordRevisionEvent.setWord(word);
            wordRevisionEvent.setText(word.getText());
            wordRevisionEvent.setPhonetics(word.getPhonetics());
            wordRevisionEventDao.create(wordRevisionEvent);
            
            // Label Image with Word of matching title
            Image matchingImage = imageDao.read(word.getText(), word.getLocale());
            if (matchingImage != null) {
                Set<Word> labeledWords = matchingImage.getWords();
                if (!labeledWords.contains(word)) {
                    labeledWords.add(word);
                    matchingImage.setWords(labeledWords);
                    imageDao.update(matchingImage);
                }
            }
            
            // Delete syllables that are actual words
            Syllable syllable = syllableDao.readByText(contributor.getLocale(), word.getText());
            if (syllable != null) {
                syllableDao.delete(syllable);
            }
            
            if (EnvironmentContextLoaderListener.env == Environment.PROD) {
                 String text = URLEncoder.encode(
                     contributor.getFirstName() + " just added a new Word:\n" +
                     "• Language: \"" + word.getLocale().getLanguage() + "\"\n" +
                     "• Text: \"" + word.getText() + "\"\n" +
                     "• Phonetics (IPA): /" + word.getPhonetics() + "/\n" +
                     "• Word type: " + word.getWordType() + "\n" +
                     "• Spelling consistency: " + word.getSpellingConsistency() + "\n" +
                     "See ") + "http://elimu.ai/content/word/edit/" + word.getId();
                 String iconUrl = contributor.getImageUrl();
                 SlackApiHelper.postMessage(SlackApiHelper.getChannelId(Team.CONTENT_CREATION), text, iconUrl, null);
            }
            
            return "redirect:/content/word/list#" + word.getId();
        }
    }
}
