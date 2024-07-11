package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;

@Controller
@RequestMapping("/content/word/edit")
public class WordEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    @Autowired
    private WordPeerReviewEventDao wordPeerReviewEventDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(
            HttpSession session,
            Model model,
            @PathVariable Long id
    ) {
    	logger.info("handleRequest");
        
        Word word = wordDao.read(id);
        
        if (word.getLetterSoundCorrespondences().isEmpty()) {
            autoSelectLetterSoundCorrespondences(word);
            // TODO: display information message to the Contributor that the letter-sound correspondences were auto-selected, and that they should be verified
        }
                
        model.addAttribute("word", word);
        model.addAttribute("timeStart", System.currentTimeMillis());
        model.addAttribute("letterSoundCorrespondences", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
        model.addAttribute("rootWords", wordDao.readAllOrdered());
        model.addAttribute("emojisByWordId", getEmojisByWordId());
        model.addAttribute("wordTypes", WordType.values());
        model.addAttribute("spellingConsistencies", SpellingConsistency.values());
        
        model.addAttribute("wordContributionEvents", wordContributionEventDao.readAll(word));
        model.addAttribute("wordPeerReviewEvents", wordPeerReviewEventDao.readAll(word));
        
        List<Audio> audios = audioDao.readAll(word);
        model.addAttribute("audios", audios);
        
        // Look up variants of the same wordByTextMatch
        model.addAttribute("wordInflections", wordDao.readInflections(word));
        
        // Look up Multimedia content that has been labeled with this Word
        // TODO: labeled Audios
        List<Emoji> labeledEmojis = emojiDao.readAllLabeled(word);
        model.addAttribute("labeledEmojis", labeledEmojis);
        List<Image> labeledImages = imageDao.readAllLabeled(word);
        model.addAttribute("labeledImages", labeledImages);
        // TODO: labeled Videos
        
        // Look up StoryBook Paragraphs that contain this Word
        List<StoryBookParagraph> storyBookParagraphsContainingWord = storyBookParagraphDao.readAllContainingWord(word.getText());
        model.addAttribute("storyBookParagraphsContainingWord", storyBookParagraphsContainingWord);

        return "content/word/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid Word word,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        Word existingWord = wordDao.readByTextAndType(word.getText(), word.getWordType());
        if ((existingWord != null) && !existingWord.getId().equals(word.getId())) {
            result.rejectValue("text", "NonUnique");
        }
        
        if (StringUtils.containsAny(word.getText(), " ")) {
            result.rejectValue("text", "WordSpace");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("timeStart", request.getParameter("timeStart"));
            model.addAttribute("letterSoundCorrespondences", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
            model.addAttribute("rootWords", wordDao.readAllOrdered());
            model.addAttribute("emojisByWordId", getEmojisByWordId());
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            
            model.addAttribute("wordContributionEvents", wordContributionEventDao.readAll(word));
            model.addAttribute("wordPeerReviewEvents", wordPeerReviewEventDao.readAll(word));
            
            model.addAttribute("audios", audioDao.readAll(word));
            
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
        } else {
            word.setTimeLastUpdate(Calendar.getInstance());
            word.setRevisionNumber(word.getRevisionNumber() + 1);
            wordDao.update(word);
            
            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            wordContributionEvent.setTime(Calendar.getInstance());
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
            wordContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            wordContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            wordContributionEventDao.create(wordContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + word.getId();
                DiscordHelper.sendChannelMessage(
                        "Word edited: " + contentUrl, 
                        "\"" + word.getText() + "\"",
                        "Comment: \"" + wordContributionEvent.getComment() + "\"",
                        null,
                        null
                );
            }
            
            // Note: updating the list of Words in StoryBookParagraphs is handled by the ParagraphWordScheduler
            
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
    
    private void autoSelectLetterSoundCorrespondences(Word word) {
        logger.info("autoSelectLetterSoundCorrespondences");
        
        String wordText = word.getText();
        
        List<LetterSoundCorrespondence> letterSoundCorrespondences = new ArrayList<>();
        
        List<LetterSoundCorrespondence> allLetterSoundCorrespondencesOrderedByLettersLength = letterSoundDao.readAllOrderedByLettersLength();
        while (StringUtils.isNotBlank(wordText)) {
            logger.info("wordText: \"" + wordText + "\"");
            
            boolean isMatch = false;
            for (LetterSoundCorrespondence letterSoundCorrespondence : allLetterSoundCorrespondencesOrderedByLettersLength) {
                String letterSoundCorrespondenceLetters = letterSoundCorrespondence.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
                logger.info("letterSoundCorrespondenceLetters: \"" + letterSoundCorrespondenceLetters + "\"");

                if (wordText.startsWith(letterSoundCorrespondenceLetters)) {
                    isMatch = true;
                    logger.info("Found match at the beginning of \"" + wordText + "\"");
                    letterSoundCorrespondences.add(letterSoundCorrespondence);

                    // Remove the match from the word
                    wordText = wordText.substring(letterSoundCorrespondenceLetters.length());
                    
                    break;
                }
            }
            if (!isMatch) {
                // Skip auto-selection for the subsequent letters
                break;
            }
        }
        
        word.setLetterSoundCorrespondences(letterSoundCorrespondences);
    }
}
