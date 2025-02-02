package ai.elimu.web.content.letter_sound;

import java.util.List;
import jakarta.validation.Valid;

import ai.elimu.model.contributor.LetterSoundContributionEvent;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.LetterSoundPeerReviewEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.util.DiscordHelper;
import java.util.Calendar;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.stream.Collectors;
import ai.elimu.dao.SoundDao;

@Controller
@RequestMapping("/content/letter-sound/edit")
public class LetterSoundEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @Autowired
    private LetterSoundContributionEventDao letterSoundContributionEventDao;
    
    @Autowired
    private LetterSoundPeerReviewEventDao letterSoundPeerReviewEventDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private SoundDao soundDao;
    
    @Autowired
    private WordDao wordDao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
        logger.info("handleRequest");

        LetterSound letterSound = letterSoundDao.read(id);
        model.addAttribute("letterSound", letterSound);
        
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        List<Letter> letters = letterDao.readAllOrdered();
        model.addAttribute("letters", letters);
        
        List<Sound> sounds = soundDao.readAllOrdered();
        model.addAttribute("sounds", sounds);
        
        model.addAttribute("letterSoundContributionEvents", letterSoundContributionEventDao.readAll(letterSound));
        model.addAttribute("letterSoundPeerReviewEvents", letterSoundPeerReviewEventDao.readAll(letterSound));
        
        List<Word> words = wordDao.readAllOrderedByUsage();
        model.addAttribute("words", words);
        
        return "content/letter-sound/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid LetterSound letterSound,
            BindingResult result,
            Model model
    ) {
        logger.info("handleSubmit");
        
        // Check if the LetterSound already exists
        LetterSound existingLetterSound = letterSoundDao.read(letterSound.getLetters(), letterSound.getSounds());
        if ((existingLetterSound != null) && !existingLetterSound.getId().equals(letterSound.getId())) {
            result.rejectValue("letters", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letterSound", letterSound);
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            List<Letter> letters = letterDao.readAllOrdered();
            model.addAttribute("letters", letters);
            
            List<Sound> sounds = soundDao.readAllOrdered();
            model.addAttribute("sounds", sounds);
            
            model.addAttribute("letterSoundContributionEvents", letterSoundContributionEventDao.readAll(letterSound));
            model.addAttribute("letterSoundPeerReviewEvents", letterSoundPeerReviewEventDao.readAll(letterSound));
            
            return "content/letter-sound/edit";
        } else {
            letterSound.setTimeLastUpdate(Calendar.getInstance());
            letterSound.setRevisionNumber(letterSound.getRevisionNumber() + 1);
            letterSoundDao.update(letterSound);
            
            LetterSoundContributionEvent letterSoundContributionEvent = new LetterSoundContributionEvent();
            letterSoundContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            letterSoundContributionEvent.setTimestamp(Calendar.getInstance());
            letterSoundContributionEvent.setLetterSound(letterSound);
            letterSoundContributionEvent.setRevisionNumber(letterSound.getRevisionNumber());
            letterSoundContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            letterSoundContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            letterSoundContributionEventDao.create(letterSoundContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter-sound/edit/" + letterSound.getId();
                DiscordHelper.sendChannelMessage(
                        "Letter-sound correspondence edited: " + contentUrl,
                        "\"" + letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
                        "Comment: \"" + letterSoundContributionEvent.getComment() + "\"",
                        null,
                        null
                );
            }
            
            return "redirect:/content/letter-sound/list#" + letterSound.getId();
        }
    }
}
