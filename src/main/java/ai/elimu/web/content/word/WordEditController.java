package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/edit")
public class WordEditController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id, HttpSession session) {
    	logger.info("handleRequest");
        
        Word word = wordDao.read(id);
        model.addAttribute("word", word);
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLanguage());
        model.addAttribute("allophones", allophones);
        
        model.addAttribute("wordTypes", WordType.values());
        
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());
        
        Audio audio = audioDao.read(word.getText(), contributor.getLanguage());
        model.addAttribute("audio", audio);
        
        // Look up Multimedia content that has been labeled with this Word
        List<Image> labeledImages = imageDao.readAllLabeled(word, contributor.getLanguage());
        // TODO: labeled Audios
        model.addAttribute("labeledImages", labeledImages);
        // TODO: labeled Videos

        return "content/word/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpSession session,
            @Valid Word word,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
    	logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByText(word.getLanguage(), word.getText());
        if ((existingWord != null) && !existingWord.getId().equals(word.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(contributor.getLanguage());
        
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
            Audio audio = audioDao.read(word.getText(), contributor.getLanguage());
            model.addAttribute("audio", audio);
            return "content/word/edit";
        } else {
            if (contributor.getLanguage() == Language.ENG) {
                if (!"I".equals(word.getText())) {
                    word.setText(word.getText().toLowerCase());
                }
            }
            word.setTimeLastUpdate(Calendar.getInstance());
            word.setRevisionNumber(word.getRevisionNumber() + 1);
            wordDao.update(word);
            
            // Delete syllables that are actual words
            Syllable syllable = syllableDao.readByText(contributor.getLanguage(), word.getText());
            if (syllable != null) {
                syllableDao.delete(syllable);
            }
            
            return "redirect:/content/word/list#" + word.getId();
        }
    }
}
