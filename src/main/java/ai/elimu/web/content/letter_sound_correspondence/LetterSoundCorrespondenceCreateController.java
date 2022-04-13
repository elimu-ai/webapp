package ai.elimu.web.content.letter_sound_correspondence;

import java.util.List;
import javax.validation.Valid;

import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.LetterSoundCorrespondenceContributionEventDao;
import ai.elimu.model.content.Sound;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSoundCorrespondence;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundCorrespondenceDao;
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
@RequestMapping("/content/letter-sound-correspondence/create")
public class LetterSoundCorrespondenceCreateController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundCorrespondenceDao letterSoundCorrespondenceDao;
    
    @Autowired
    private LetterSoundCorrespondenceContributionEventDao letterSoundCorrespondenceContributionEventDao;
    
    @Autowired
    private LetterDao letterDao;
    
    @Autowired
    private SoundDao soundDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        LetterSoundCorrespondence letterSoundCorrespondence = new LetterSoundCorrespondence();
        model.addAttribute("letterSoundCorrespondence", letterSoundCorrespondence);
        
        List<Letter> letters = letterDao.readAllOrdered();
        model.addAttribute("letters", letters);
        
        List<Sound> sounds = soundDao.readAllOrdered();
        model.addAttribute("sounds", sounds);
        
        model.addAttribute("timeStart", System.currentTimeMillis());

        return "content/letter-sound-correspondence/create";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @Valid LetterSoundCorrespondence letterSoundCorrespondence,
            BindingResult result,
            Model model
    ) {
    	logger.info("handleSubmit");
        
        // Check if the LetterSoundCorrespondence already exists
        LetterSoundCorrespondence existingLetterSoundCorrespondence = letterSoundCorrespondenceDao.read(letterSoundCorrespondence.getLetters(), letterSoundCorrespondence.getSounds());
        if (existingLetterSoundCorrespondence != null) {
            result.rejectValue("letters", "NonUnique");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("letterSoundCorrespondence", letterSoundCorrespondence);
            
            List<Letter> letters = letterDao.readAllOrdered();
            model.addAttribute("letters", letters);
            
            List<Sound> sounds = soundDao.readAllOrdered();
            model.addAttribute("sounds", sounds);
            
            model.addAttribute("timeStart", System.currentTimeMillis());
            
            return "content/letter-sound-correspondence/create";
        } else {
            letterSoundCorrespondence.setTimeLastUpdate(Calendar.getInstance());
            letterSoundCorrespondenceDao.create(letterSoundCorrespondence);
            
            LetterSoundCorrespondenceContributionEvent letterSoundCorrespondenceContributionEvent = new LetterSoundCorrespondenceContributionEvent();
            letterSoundCorrespondenceContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            letterSoundCorrespondenceContributionEvent.setTime(Calendar.getInstance());
            letterSoundCorrespondenceContributionEvent.setLetterSoundCorrespondence(letterSoundCorrespondence);
            letterSoundCorrespondenceContributionEvent.setRevisionNumber(letterSoundCorrespondence.getRevisionNumber());
            letterSoundCorrespondenceContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            letterSoundCorrespondenceContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            letterSoundCorrespondenceContributionEvent.setPlatform(Platform.WEBAPP);
            letterSoundCorrespondenceContributionEventDao.create(letterSoundCorrespondenceContributionEvent);
            
            String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/letter-sound-correspondence/edit/" + letterSoundCorrespondence.getId();
            DiscordHelper.sendChannelMessage(
                    "Letter-sound correspondence created: " + contentUrl,
                    "\"" + letterSoundCorrespondence.getLetters().stream().map(Letter::getText).collect(Collectors.joining()) + "\"",
                    "Comment: \"" + letterSoundCorrespondenceContributionEvent.getComment() + "\"",
                    null,
                    null
            );
            
            return "redirect:/content/letter-sound-correspondence/list#" + letterSoundCorrespondence.getId();
        }
    }
}
