package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

@Controller
@RequestMapping("/content/word/create")
public class WordCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @RequestParam(required = false) String autoFillText) {
        logger.info("handleRequest");
        
        Word word = new Word();
        
        // Pre-fill the Word's text (if the user arrived from /content/storybook/edit/{id}/)
        if (StringUtils.isNotBlank(autoFillText)) {
            word.setText(autoFillText);
            
            autoSelectLetterSounds(word);
            // TODO: display information message to the Contributor that the letter-sound correspondences were auto-selected, and that they should be verified
        }
        
        model.addAttribute("word", word);
        model.addAttribute("timeStart", System.currentTimeMillis());
        model.addAttribute("letterSounds", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
        model.addAttribute("rootWords", wordDao.readAllOrdered());
        model.addAttribute("emojisByWordId", getEmojisByWordId());
        model.addAttribute("wordTypes", WordType.values());
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());

        return "content/word/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid Word word,
            BindingResult result,
            Model model) {
        logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByTextAndType(word.getText(), word.getWordType());
        if (existingWord != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (StringUtils.containsAny(word.getText(), " ")) {
            result.rejectValue("text", "WordSpace");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("timeStart", request.getParameter("timeStart"));
            model.addAttribute("letterSounds", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
            model.addAttribute("rootWords", wordDao.readAllOrdered());
            model.addAttribute("emojisByWordId", getEmojisByWordId());
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            
            return "content/word/create";
        } else {
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
            
            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            wordContributionEvent.setTimestamp(Calendar.getInstance());
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
            wordContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            wordContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            wordContributionEventDao.create(wordContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + word.getId();
                DiscordHelper.sendChannelMessage(
                        "Word created: " + contentUrl,
                        "\"" + wordContributionEvent.getWord().getText() + "\"",
                        "Comment: \"" + wordContributionEvent.getComment() + "\"",
                        null,
                        null
                );
            }
            
            // Note: updating the list of Words in StoryBookParagraphs is handled by the ParagraphWordScheduler
            
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
    
    private Map<Long, String> getEmojisByWordId() {
        logger.info("getEmojisByWordId");
        
        Map<Long, String> emojisByWordId = new HashMap<>();
        
        for (Word word : wordDao.readAll()) {
            String emojiGlyphs = "";
            
            List<Emoji> emojis = emojiDao.readAllLabeled(word);
            for (Emoji emoji : emojis) {
                emojiGlyphs += emoji.getGlyph();
            }
            
            if (StringUtils.isNotBlank(emojiGlyphs)) {
                emojisByWordId.put(word.getId(), emojiGlyphs);
            }
        }
        
        return emojisByWordId;
    }
    
    private void autoSelectLetterSounds(Word word) {
        logger.info("autoSelectLetterSounds");
        
        String wordText = word.getText();
        
        List<LetterSound> letterSounds = new ArrayList<>();
        
        List<LetterSound> allLetterSoundsOrderedByLettersLength = letterSoundDao.readAllOrderedByLettersLength();
        while (StringUtils.isNotBlank(wordText)) {
            logger.info("wordText: \"" + wordText + "\"");
            
            boolean isMatch = false;
            for (LetterSound letterSound : allLetterSoundsOrderedByLettersLength) {
                String letterSoundLetters = letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
                logger.info("letterSoundLetters: \"" + letterSoundLetters + "\"");

                if (wordText.startsWith(letterSoundLetters)) {
                    isMatch = true;
                    logger.info("Found match at the beginning of \"" + wordText + "\"");
                    letterSounds.add(letterSound);

                    // Remove the match from the word
                    wordText = wordText.substring(letterSoundLetters.length());
                    
                    break;
                }
            }
            if (!isMatch) {
                // Skip auto-selection for the subsequent letters
                break;
            }
        }
        
        word.setLetterSounds(letterSounds);
    }
}
