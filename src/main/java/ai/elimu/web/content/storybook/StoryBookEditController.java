package ai.elimu.web.content.storybook;

import ai.elimu.dao.EmojiDao;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.StoryBook;
import ai.elimu.model.content.StoryBookChapter;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.StoryBookContributionEvent;
import ai.elimu.model.enums.ContentLicense;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.LetterFrequencyHelper;
import ai.elimu.util.WordFrequencyHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.ArrayList;
import java.util.HashMap;
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

@Controller
@RequestMapping("/content/storybook/edit")
public class StoryBookEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;
    
    @Autowired
    private StoryBookChapterDao storyBookChapterDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Autowired
    private ImageDao imageDao;
    
    @Autowired
    private WordDao wordDao;
    
    @Autowired
    private EmojiDao emojiDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private StoryBooksJsonService storyBooksJsonService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
    	logger.info("handleRequest");
        
        StoryBook storyBook = storyBookDao.read(id);
        model.addAttribute("storyBook", storyBook);
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        model.addAttribute("contentLicenses", ContentLicense.values());
        
        List<Image> coverImages = imageDao.readAllOrdered();
        model.addAttribute("coverImages", coverImages);
        
        model.addAttribute("readingLevels", ReadingLevel.values());
        
        List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
        model.addAttribute("storyBookChapters", storyBookChapters);
        
        // Map<StoryBookChapter.id, List<StoryBookParagraph>>
        Map<Long, List<StoryBookParagraph>> paragraphsPerStoryBookChapterMap = new HashMap<>();
        for (StoryBookChapter storyBookChapter : storyBookChapters) {
            paragraphsPerStoryBookChapterMap.put(storyBookChapter.getId(), storyBookParagraphDao.readAll(storyBookChapter));
        }
        model.addAttribute("paragraphsPerStoryBookChapterMap", paragraphsPerStoryBookChapterMap);
        
        List<String> paragraphs = new ArrayList<>();
        for (StoryBookChapter storyBookChapter : storyBookChapters) {
            List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
            for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
                paragraphs.add(storyBookParagraph.getOriginalText());
            }
        }
        
        model.addAttribute("storyBookContributionEvents", storyBookContributionEventDao.readAll(storyBook));
        model.addAttribute("storyBookPeerReviewEvents", storyBookPeerReviewEventDao.readAll(storyBook));
        
        Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
        Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, language);
        model.addAttribute("wordFrequencyMap", wordFrequencyMap);
        Map<String, Word> wordMap = new HashMap<>();
        for (Word word : wordDao.readAllOrdered()) {
            wordMap.put(word.getText(), word);
        }
        model.addAttribute("wordMap", wordMap);
        model.addAttribute("emojisByWordId", getEmojisByWordId());
        
        Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
        model.addAttribute("letterFrequencyMap", letterFrequencyMap);
        Map<String, Letter> letterMap = new HashMap<>();
        for (Letter letter : letterDao.readAllOrdered()) {
            letterMap.put(letter.getText(), letter);
        }
        model.addAttribute("letterMap", letterMap);
        
        return "content/storybook/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            @Valid StoryBook storyBook,
            BindingResult result,
            Model model,
            HttpServletRequest request,
            HttpSession session) {
    	logger.info("handleSubmit");
        
        StoryBook existingStoryBook = storyBookDao.readByTitle(storyBook.getTitle());
        if ((existingStoryBook != null) && !existingStoryBook.getId().equals(storyBook.getId())) {
            result.rejectValue("title", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("storyBook", storyBook);
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            model.addAttribute("contentLicenses", ContentLicense.values());
            
            List<Image> coverImages = imageDao.readAllOrdered();
            model.addAttribute("coverImages", coverImages);
            
            model.addAttribute("readingLevels", ReadingLevel.values());
            
            List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
            model.addAttribute("storyBookChapters", storyBookChapters);

            // Map<StoryBookChapter.id, List<StoryBookParagraph>>
            Map<Long, List<StoryBookParagraph>> paragraphsPerStoryBookChapterMap = new HashMap<>();
            for (StoryBookChapter storyBookChapter : storyBookChapters) {
                paragraphsPerStoryBookChapterMap.put(storyBookChapter.getId(), storyBookParagraphDao.readAll(storyBookChapter));
            }
            model.addAttribute("paragraphsPerStoryBookChapterMap", paragraphsPerStoryBookChapterMap);

            List<String> paragraphs = new ArrayList<>();
            for (StoryBookChapter storyBookChapter : storyBookChapters) {
                List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
                for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
                    paragraphs.add(storyBookParagraph.getOriginalText());
                }
            }

            model.addAttribute("storyBookContributionEvents", storyBookContributionEventDao.readAll(storyBook));
            model.addAttribute("storyBookPeerReviewEvents", storyBookPeerReviewEventDao.readAll(storyBook));
            
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
            Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, language);
            model.addAttribute("wordFrequencyMap", wordFrequencyMap);
            Map<String, Word> wordMap = new HashMap<>();
            for (Word word : wordDao.readAllOrdered()) {
                wordMap.put(word.getText(), word);
            }
            model.addAttribute("wordMap", wordMap);
            model.addAttribute("emojisByWordId", getEmojisByWordId());

            Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
            model.addAttribute("letterFrequencyMap", letterFrequencyMap);
            Map<String, Letter> letterMap = new HashMap<>();
            for (Letter letter : letterDao.readAllOrdered()) {
                letterMap.put(letter.getText(), letter);
            }
            model.addAttribute("letterMap", letterMap);

            return "content/storybook/edit";
        } else {
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
            storyBookDao.update(storyBook);
            
            StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
            storyBookContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            storyBookContributionEvent.setTime(Calendar.getInstance());
            storyBookContributionEvent.setStoryBook(storyBook);
            storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            storyBookContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            storyBookContributionEventDao.create(storyBookContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
                String embedThumbnailUrl = null;
                if (storyBook.getCoverImage() != null) {
                    embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + storyBook.getCoverImage().getId() + "_r" + storyBook.getCoverImage().getRevisionNumber() + "." + storyBook.getCoverImage().getImageFormat().toString().toLowerCase();
                }
                DiscordHelper.sendChannelMessage("Storybook edited: " + contentUrl,
                        "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                        "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
                        null,
                        embedThumbnailUrl
                );
            }
            
            // Refresh REST API cache
            storyBooksJsonService.refreshStoryBooksJSONArray();
            
            return "redirect:/content/storybook/list#" + storyBook.getId();
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
}
