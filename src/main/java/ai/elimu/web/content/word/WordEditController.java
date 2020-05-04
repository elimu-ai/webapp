package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordExtractionHelper;
import java.util.ArrayList;
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
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Autowired
    private SyllableDao syllableDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        Word word = wordDao.read(id);
                
        model.addAttribute("word", wordDao.read(id));
        model.addAttribute("allophones", allophoneDao.readAllOrdered());
        model.addAttribute("letterToAllophoneMappings", letterToAllophoneMappingDao.readAll());
        model.addAttribute("rootWords", wordDao.readAllOrdered());
        model.addAttribute("wordTypes", WordType.values());
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());
        model.addAttribute("audio", audioDao.read(word.getText()));
        
        // Look up variants of the same wordByTextMatch
        model.addAttribute("wordInflections", wordDao.readInflections(word));
        
        // Look up Multimedia content that has been labeled with this Word
        // TODO: labeled Audios
        List<Emoji> labeledEmojis = emojiDao.readAllLabeled(word);
        model.addAttribute("labeledEmojis", labeledEmojis);
        List<Image> labeledImages = imageDao.readAllLabeled(word);
        model.addAttribute("labeledImages", labeledImages);
        // TODO: labeled Videos

        return "content/word/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid Word word,
            BindingResult result,
            Model model) {
    	logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByText(word.getText());
        if ((existingWord != null) && !existingWord.getId().equals(word.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrdered();
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("allophones", allophones);
            model.addAttribute("letterToAllophoneMappings", letterToAllophoneMappingDao.readAll());
            model.addAttribute("rootWords", wordDao.readAllOrdered());
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            model.addAttribute("audio", audioDao.read(word.getText()));
            model.addAttribute("wordInflections", wordDao.readInflections(word));
            return "content/word/edit";
        } else {
            word.setTimeLastUpdate(Calendar.getInstance());
            word.setRevisionNumber(word.getRevisionNumber() + 1);
            wordDao.update(word);
            
            // Refresh the list of Words StoryBookParagraphs where the Word is being used
            List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAllContainingWord(word.getText());
            logger.info("storyBookParagraphs.size(): " + storyBookParagraphs.size());
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
            for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
                List<String> wordsInOriginalText = WordExtractionHelper.getWords(storyBookParagraph.getOriginalText(), language);
                logger.info("wordsInOriginalText.size(): " + wordsInOriginalText.size());
                List<Word> words = new ArrayList<>();
                logger.info("words.size(): " + words.size());
                for (String wordInOriginalText : wordsInOriginalText) {
                    logger.info("wordInOriginalText: \"" + wordInOriginalText + "\"");
                    wordInOriginalText = wordInOriginalText.toLowerCase();
                    logger.info("wordInOriginalText (lower-case): \"" + wordInOriginalText + "\"");
                    Word wordByTextMatch = wordDao.readByText(wordInOriginalText);
                    logger.info("wordByTextMatch: " + wordByTextMatch);
                    words.add(wordByTextMatch);
                }
                storyBookParagraph.setWords(words);
                storyBookParagraphDao.update(storyBookParagraph);
            }
            
            // Delete syllables that are actual words
            Syllable syllable = syllableDao.readByText(word.getText());
            if (syllable != null) {
                syllableDao.delete(syllable);
            }
            
            return "redirect:/content/word/list#" + word.getId();
        }
    }
}
