package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.util.ConfigHelper;
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
    private EmojiDao emojiDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Word word = wordDao.read(id);
        model.addAttribute("word", word);
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(language);
        model.addAttribute("allophones", allophones);
        
        model.addAttribute("wordTypes", WordType.values());
        
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());
        
        Audio audio = audioDao.read(word.getText(), language);
        model.addAttribute("audio", audio);
        
        // Look up Multimedia content that has been labeled with this Word
        // TODO: labeled Audios
        List<Emoji> labeledEmojis = emojiDao.readAllLabeled(word, language);
        model.addAttribute("labeledEmojis", labeledEmojis);
        List<Image> labeledImages = imageDao.readAllLabeled(word, language);
        model.addAttribute("labeledImages", labeledImages);
        // TODO: labeled Videos

        return "content/word/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Word word,
            BindingResult result,
            Model model,
            HttpServletRequest request) {
    	logger.info("handleSubmit");
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        
        Word existingWord = wordDao.readByText(language, word.getText());
        if ((existingWord != null) && !existingWord.getId().equals(word.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage(language);
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("allophones", allophones);
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            Audio audio = audioDao.read(word.getText(), language);
            model.addAttribute("audio", audio);
            return "content/word/edit";
        } else {
            if (language == Language.ENG) {
                if (!"I".equals(word.getText())) {
                    word.setText(word.getText().toLowerCase());
                }
            }
            word.setTimeLastUpdate(Calendar.getInstance());
            word.setRevisionNumber(word.getRevisionNumber() + 1);
            wordDao.update(word);
            
            // Delete syllables that are actual words
            Syllable syllable = syllableDao.readByText(language, word.getText());
            if (syllable != null) {
                syllableDao.delete(syllable);
            }
            
            return "redirect:/content/word/list#" + word.getId();
        }
    }
}
