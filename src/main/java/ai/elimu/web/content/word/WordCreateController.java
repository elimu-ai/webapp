package ai.elimu.web.content.word;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Allophone;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterToAllophoneMapping;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.AudioContributionEvent;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.enums.Language;
import ai.elimu.model.enums.Platform;
import ai.elimu.model.enums.content.AudioFormat;
import ai.elimu.model.enums.content.SpellingConsistency;
import ai.elimu.model.enums.content.WordType;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.audio.GoogleCloudTextToSpeechHelper;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/word/create")
public class WordCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private AllophoneDao allophoneDao;
    
    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private SyllableDao syllableDao;
    
    @Autowired
    private WordContributionEventDao wordContributionEventDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model, @RequestParam(required = false) String autoFillText) {
    	logger.info("handleRequest");
        
        Word word = new Word();
        
        // Pre-fill the Word's text (if the user arrived from /content/storybook/edit/{id}/)
        if (StringUtils.isNotBlank(autoFillText)) {
            word.setText(autoFillText);
            
            autoSelectLetterToAllophoneMappings(word);
            // TODO: display information message to the Contributor that the Letter-to-Allophone mappings were auto-selected, and that they should be verified
            
            model.addAttribute("audio", audioDao.readByTranscription(word.getText()));
        }
        
        model.addAttribute("word", word);
        model.addAttribute("timeStart", System.currentTimeMillis());
        model.addAttribute("allophones", allophoneDao.readAllOrdered());
        model.addAttribute("letterToAllophoneMappings", letterToAllophoneMappingDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
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
        
        Word existingWord = wordDao.readByText(word.getText());
        if (existingWord != null) {
            result.rejectValue("text", "NonUnique");
        }
        
        List<Allophone> allophones = allophoneDao.readAllOrdered();
        
        if (result.hasErrors()) {
            model.addAttribute("word", word);
            model.addAttribute("timeStart", request.getParameter("timeStart"));
            model.addAttribute("allophones", allophones);
            model.addAttribute("letterToAllophoneMappings", letterToAllophoneMappingDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
            model.addAttribute("rootWords", wordDao.readAllOrdered());
            model.addAttribute("emojisByWordId", getEmojisByWordId());
            model.addAttribute("wordTypes", WordType.values());
            model.addAttribute("spellingConsistencies", SpellingConsistency.values());
            
            model.addAttribute("audio", audioDao.readByTranscription(word.getText()));
            
            return "content/word/create";
        } else {
            word.setTimeLastUpdate(Calendar.getInstance());
            wordDao.create(word);
            
            WordContributionEvent wordContributionEvent = new WordContributionEvent();
            wordContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            wordContributionEvent.setTime(Calendar.getInstance());
            wordContributionEvent.setWord(word);
            wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
            wordContributionEvent.setComment(request.getParameter("contributionComment"));
            wordContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            wordContributionEventDao.create(wordContributionEvent);
            
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
            
            // Generate Audio for this Word (if it has not been done already)
            List<Audio> audios = audioDao.readAll(word);
            if (audios.isEmpty()) {
                Calendar timeStart = Calendar.getInstance();
                Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
                try {
                    byte[] audioBytes = GoogleCloudTextToSpeechHelper.synthesizeText(word.getText(), language);
                    logger.info("audioBytes: " + audioBytes);
                    if (audioBytes != null) {
                        Audio audio = new Audio();
                        audio.setTimeLastUpdate(Calendar.getInstance());
                        audio.setContentType(AudioFormat.MP3.getContentType());
                        audio.setWord(word);
                        audio.setTitle("word-" + word.getId());
                        audio.setTranscription(word.getText());
                        audio.setBytes(audioBytes);
                        audio.setDurationMs(null); // TODO: Convert from byte[] to File, and extract audio duration
                        audio.setAudioFormat(AudioFormat.MP3);
                        audioDao.create(audio);

                        audios.add(audio);
                        model.addAttribute("audios", audios);

                        AudioContributionEvent audioContributionEvent = new AudioContributionEvent();
                        audioContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
                        audioContributionEvent.setTime(Calendar.getInstance());
                        audioContributionEvent.setAudio(audio);
                        audioContributionEvent.setRevisionNumber(audio.getRevisionNumber());
                        audioContributionEvent.setComment("Google Cloud Text-to-Speech (🤖 auto-generated comment)️");
                        audioContributionEvent.setTimeSpentMs(System.currentTimeMillis() - timeStart.getTimeInMillis());
                        audioContributionEvent.setPlatform(Platform.WEBAPP);
                        audioContributionEventDao.create(audioContributionEvent);
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                }
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
    
    private void autoSelectLetterToAllophoneMappings(Word word) {
        logger.info("autoSelectLetterToAllophoneMappings");
        
        String wordText = word.getText();
        
        List<LetterToAllophoneMapping> letterToAllophoneMappings = new ArrayList<>();
        
        List<LetterToAllophoneMapping> allLetterToAllophoneMappingsOrderedByLettersLength = letterToAllophoneMappingDao.readAllOrderedByLettersLength();
        while (StringUtils.isNotBlank(wordText)) {
            logger.info("wordText: \"" + wordText + "\"");
            
            boolean isMatch = false;
            for (LetterToAllophoneMapping letterToAllophoneMapping : allLetterToAllophoneMappingsOrderedByLettersLength) {
                String letterToAllophoneMappingLetters = letterToAllophoneMapping.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
                logger.info("letterToAllophoneMappingLetters: \"" + letterToAllophoneMappingLetters + "\"");

                if (wordText.startsWith(letterToAllophoneMappingLetters)) {
                    isMatch = true;
                    logger.info("Found match at the beginning of \"" + wordText + "\"");
                    letterToAllophoneMappings.add(letterToAllophoneMapping);

                    // Remove the match from the word
                    wordText = wordText.substring(letterToAllophoneMappingLetters.length());
                    
                    break;
                }
            }
            if (!isMatch) {
                // Skip auto-selection for the subsequent letters
                break;
            }
        }
        
        word.setLetterToAllophoneMappings(letterToAllophoneMappings);
    }
}
