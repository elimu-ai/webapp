package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.util.ConfigHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/word/create")
public class WordCreateController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @RequestParam(required = false) String autoFillText) {
    	logger.info("handleRequest");
        
        Word word = new Word();
        
        // Pre-fill the Word's autoFillText (if the user arrived from /content/storybook/edit/{id}/)
        if (StringUtils.isNotBlank(autoFillText)) {
            word.setText(autoFillText);
        }
        
        model.addAttribute("word", word);
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Allophone> allophones = allophoneDao.readAllOrdered(language);
        model.addAttribute("allophones", allophones);
        
        model.addAttribute("wordTypes", WordType.values());
        
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());

        return "content/word/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Word word,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        Word existingWord = wordDao.readByText(language, word.getText());
        if (existingWord != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrdered(language);
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("allophones", allophones);
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            return "content/word/create";
        } else {
            if (language == Language.ENG) {
                if (!"I".equals(word.getText())) {
                    word.setText(word.getText().toLowerCase());
                }
            }
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
            
            // Label Image with Word of matching title
            Image matchingImage = imageDao.read(word.getText(), language);
            if (matchingImage != null) {
                Set<Word> labeledWords = matchingImage.getWords();
                if (!labeledWords.contains(word)) {
                    labeledWords.add(word);
                    matchingImage.setWords(labeledWords);
                    imageDao.update(matchingImage);
                }
            }
            
            // Delete syllables that are actual words
            Syllable syllable = syllableDao.readByText(language, word.getText());
            if (syllable != null) {
                syllableDao.delete(syllable);
            }
            
            return "redirect:/content/word/list#" + word.getId();
        }
    }
}
