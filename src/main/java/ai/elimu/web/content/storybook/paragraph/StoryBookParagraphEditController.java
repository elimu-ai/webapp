package ai.elimu.web.content.storybook.paragraph;

import ai.elimu.dao.AudioContributionEventDao;
import ai.elimu.dao.AudioDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.multimedia.Audio;
import ai.elimu.entity.contributor.AudioContributionEvent;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.entity.enums.PeerReviewStatus;
import ai.elimu.entity.enums.Platform;
import ai.elimu.model.v2.enums.content.AudioFormat;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.audio.GoogleCloudTextToSpeechHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
@RequestMapping("/content/storybook/paragraph/edit")
public class StoryBookParagraphEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookDao storyBookDao;
    
    @Autowired
    private StoryBookContributionEventDao storyBookContributionEventDao;
    
    @Autowired
    private StoryBookParagraphDao storyBookParagraphDao;
    
    @Autowired
    private AudioDao audioDao;
    
    @Autowired
    private AudioContributionEventDao audioContributionEventDao;
    
    @Autowired
    private StoryBooksJsonService storyBooksJsonService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id, HttpSession session) {
    	logger.info("handleRequest");
        
        StoryBookParagraph storyBookParagraph = storyBookParagraphDao.read(id);
        logger.info("storyBookParagraph: " + storyBookParagraph);
        model.addAttribute("storyBookParagraph", storyBookParagraph);
        
        // Generate Audio for this StoryBookParagraph (if it has not been done already)
        List<Audio> paragraphAudios = audioDao.readAll(storyBookParagraph);
        if (paragraphAudios.isEmpty()) {
            Calendar timeStart = Calendar.getInstance();
            Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
            try {
                byte[] audioBytes = GoogleCloudTextToSpeechHelper.synthesizeText(storyBookParagraph.getOriginalText(), language);
                logger.info("audioBytes: " + audioBytes);
                if (audioBytes != null) {
                    Audio audio = new Audio();
                    audio.setTimeLastUpdate(Calendar.getInstance());
                    audio.setContentType(AudioFormat.MP3.getContentType());
                    audio.setStoryBookParagraph(storyBookParagraph);
                    audio.setTitle(
                            "storybook-" + storyBookParagraph.getStoryBookChapter().getStoryBook().getId() + 
                            "-ch-" + (storyBookParagraph.getStoryBookChapter().getSortOrder() + 1) + 
                            "-par-" + (storyBookParagraph.getSortOrder() + 1)
                    );
                    audio.setTranscription(storyBookParagraph.getOriginalText());
                    audio.setBytes(audioBytes);
                    audio.setDurationMs(null); // TODO: Convert from byte[] to File, and extract audio duration
                    audio.setAudioFormat(AudioFormat.MP3);
                    audioDao.create(audio);
                    
                    AudioContributionEvent audioContributionEvent = new AudioContributionEvent();
                    audioContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
                    audioContributionEvent.setTime(Calendar.getInstance());
                    audioContributionEvent.setAudio(audio);
                    audioContributionEvent.setRevisionNumber(audio.getRevisionNumber());
                    audioContributionEvent.setComment("Google Cloud Text-to-Speech (🤖 auto-generated comment)️");
                    audioContributionEvent.setTimeSpentMs(System.currentTimeMillis() - timeStart.getTimeInMillis());
                    audioContributionEvent.setPlatform(Platform.WEBAPP);
                    audioContributionEventDao.create(audioContributionEvent);
                    
                    paragraphAudios = audioDao.readAll(storyBookParagraph);
                }
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
        
        model.addAttribute("audios", paragraphAudios);
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        return "content/storybook/paragraph/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid StoryBookParagraph storyBookParagraph,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        Contributor contributor = (Contributor) session.getAttribute("contributor");
        
        if (result.hasErrors()) {
            model.addAttribute("storyBookParagraph", storyBookParagraph);
            model.addAttribute("timeStart", System.currentTimeMillis());
            return "content/storybook/paragraph/edit";
        } else {
            // Fetch previously stored paragraph to make it possible to check if the text was modified or not when 
            // storing the StoryBookContributionEvent below.
            StoryBookParagraph storyBookParagraphBeforeEdit = storyBookParagraphDao.read(storyBookParagraph.getId());
            
            storyBookParagraphDao.update(storyBookParagraph);
            
            // Update the storybook's metadata
            StoryBook storyBook = storyBookParagraph.getStoryBookChapter().getStoryBook();
            storyBook.setTimeLastUpdate(Calendar.getInstance());
            storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
            storyBook.setPeerReviewStatus(PeerReviewStatus.PENDING);
            storyBookDao.update(storyBook);
            
            // Store contribution event
            StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
            storyBookContributionEvent.setContributor(contributor);
            storyBookContributionEvent.setTime(Calendar.getInstance());
            storyBookContributionEvent.setStoryBook(storyBook);
            storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
            storyBookContributionEvent.setComment("Edited storybook paragraph in chapter " + (storyBookParagraph.getStoryBookChapter().getSortOrder() + 1) + " (🤖 auto-generated comment)");
            if (!storyBookParagraphBeforeEdit.getOriginalText().equals(storyBookParagraph.getOriginalText())) {
                storyBookContributionEvent.setParagraphTextBefore(StringUtils.abbreviate(storyBookParagraphBeforeEdit.getOriginalText(), 1000));
                storyBookContributionEvent.setParagraphTextAfter(StringUtils.abbreviate(storyBookParagraph.getOriginalText(), 1000));
            }
            storyBookContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            storyBookContributionEvent.setPlatform(Platform.WEBAPP);
            storyBookContributionEventDao.create(storyBookContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/storybook/edit/" + storyBook.getId();
                String embedThumbnailUrl = null;
                if (storyBook.getCoverImage() != null) {
                    embedThumbnailUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/image/" + storyBook.getCoverImage().getId() + "_r" + storyBook.getCoverImage().getRevisionNumber() + "." + storyBook.getCoverImage().getImageFormat().toString().toLowerCase();
                }
                DiscordHelper.sendChannelMessage(
                        "Storybook paragraph edited: " + contentUrl,
                        "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
                        "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
                        null,
                        embedThumbnailUrl
                );
            }
            
            // Refresh the REST API cache
            storyBooksJsonService.refreshStoryBooksJSONArray();
            
            return "redirect:/content/storybook/edit/" + 
                    storyBookParagraph.getStoryBookChapter().getStoryBook().getId() + 
                    "#ch-id-" + storyBookParagraph.getStoryBookChapter().getId();
        }
    }
}
