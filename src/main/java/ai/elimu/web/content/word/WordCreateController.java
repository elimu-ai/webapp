package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
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
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
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
        model.addAttribute("allophones", allophoneDao.readAllOrdered());
        model.addAttribute("letterToAllophoneMappings", letterToAllophoneMappingDao.readAll());
        model.addAttribute("rootWords", wordDao.readAllOrdered());
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
        
        Word existingWord = wordDao.readByText(word.getText());
        if (existingWord != null) {
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
            return "content/word/create";
        } else {
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
            
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
            
            // Label Image with Word of matching title
            Image matchingImage = imageDao.read(word.getText());
            if (matchingImage != null) {
                Set<Word> labeledWords = matchingImage.getWords();
                if (!labeledWords.contains(word)) {
                    labeledWords.add(word);
                    matchingImage.setWords(labeledWords);
                    imageDao.update(matchingImage);
                }
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
