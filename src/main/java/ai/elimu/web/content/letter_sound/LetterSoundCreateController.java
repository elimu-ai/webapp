package ai.elimu.web.content.letter_sound;

import java.util.List;
import javax.validation.Valid;

import ai.elimu.model.content.LetterSound;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundContributionEventDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.LetterSoundCorrespondenceContributionEvent;
import ai.elimu.model.enums.Platform;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import java.util.Calendar;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import ai.elimu.dao.SoundDao;

@Controller
@RequestMapping("/content/letter-sound/create")
public class LetterSoundCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @Autowired
    private LetterSoundContributionEventDao letterSoundContributionEventDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private SoundDao soundDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        LetterSound letterSound = new LetterSound();
        model.addAttribute("letterSound", letterSound);
        
        List<Letter> letters = letterDao.readAllOrdered();
        model.addAttribute("letters", letters);
        
        List<Sound> sounds = soundDao.readAllOrdered();
        model.addAttribute("sounds", sounds);
        
        model.addAttribute("timeStart", System.currentTimeMillis());

        return "content/letter-sound/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
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
        if (existingLetterSound != null) {
            result.rejectValue("letters", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letterSound", letterSound);
            
            List<Letter> letters = letterDao.readAllOrdered();
            model.addAttribute("letters", letters);
            
            List<Sound> sounds = soundDao.readAllOrdered();
            model.addAttribute("sounds", sounds);
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            return "content/letter-sound/create";
        } else {
            letterSound.setTimeLastUpdate(Calendar.getInstance());
            letterSoundDao.create(letterSound);
            
            LetterSoundCorrespondenceContributionEvent letterSoundContributionEvent = new LetterSoundCorrespondenceContributionEvent();
            letterSoundContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            letterSoundContributionEvent.setTime(Calendar.getInstance());
            letterSoundContributionEvent.setLetterSoundCorrespondence(letterSound);
            letterSoundContributionEvent.setRevisionNumber(letterSound.getRevisionNumber());
            letterSoundContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            letterSoundContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            letterSoundContributionEvent.setPlatform(Platform.WEBAPP);
            letterSoundContributionEventDao.create(letterSoundContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter-sound/edit/" + letterSound.getId();
                DiscordHelper.sendChannelMessage(
                        "Letter-sound correspondence created: " + contentUrl,
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
