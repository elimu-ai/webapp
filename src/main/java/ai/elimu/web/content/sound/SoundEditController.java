package ai.elimu.web.content.sound;

import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.SoundContributionEventDao;
import java.util.Calendar;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.commons.lang.StringUtils;

import org.apache.logging.log4j.Logger;
import ai.elimu.model.content.Sound;
import ai.elimu.model.v2.enums.content.sound.SoundType;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.SoundDao;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.SoundContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/content/sound/edit")
public class SoundEditController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private SoundDao soundDao;
    
    @Autowired
    private SoundContributionEventDao soundContributionEventDao;
    
    @Autowired
    private LetterSoundDao letterSoundDao;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String handleRequest(Model model, @PathVariable Long id) {
        logger.info("handleRequest");
        
        Sound sound = soundDao.read(id);
        model.addAttribute("sound", sound);
        model.addAttribute("timeStart", System.currentTimeMillis());
        
        model.addAttribute("soundTypes", SoundType.values());
        
        model.addAttribute("soundContributionEvents", soundContributionEventDao.readAll(sound));
        
        model.addAttribute("letterSounds", letterSoundDao.readAll());

        return "content/sound/edit";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String handleSubmit(
            HttpServletRequest request,
            HttpSession session,
            @PathVariable Long id,
            @Valid Sound sound,
            BindingResult result,
            Model model
    ) {
        logger.info("handleSubmit");
        
        if (StringUtils.isNotBlank(sound.getValueIpa())) {
            Sound existingSound = soundDao.readByValueIpa(sound.getValueIpa());
            if ((existingSound != null) && !existingSound.getId().equals(sound.getId())) {
                result.rejectValue("valueIpa", "NonUnique");
            }
        }
        
        if (StringUtils.isNotBlank(sound.getValueSampa())) {
            Sound existingSound = soundDao.readByValueSampa(sound.getValueSampa());
            if ((existingSound != null) && !existingSound.getId().equals(sound.getId())) {
                result.rejectValue("valueSampa", "NonUnique");
            }
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sound", sound);
            model.addAttribute("timeStart", System.currentTimeMillis());
            model.addAttribute("soundTypes", SoundType.values());
            model.addAttribute("soundContributionEvents", soundContributionEventDao.readAll(sound));
            model.addAttribute("letterSounds", letterSoundDao.readAll());
            return "content/sound/edit";
        } else {
            sound.setTimeLastUpdate(Calendar.getInstance());
            sound.setRevisionNumber(sound.getRevisionNumber() + 1);
            soundDao.update(sound);      
            
            SoundContributionEvent soundContributionEvent = new SoundContributionEvent();
            soundContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
            soundContributionEvent.setTimestamp(Calendar.getInstance());
            soundContributionEvent.setSound(sound);
            soundContributionEvent.setRevisionNumber(sound.getRevisionNumber());
            soundContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
            soundContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
            soundContributionEventDao.create(soundContributionEvent);
            
            if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
                String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/sound/edit/" + sound.getId();
                DiscordHelper.sendChannelMessage(
                        "Sound edited: " + contentUrl,
                        "/" + soundContributionEvent.getSound().getValueIpa() + "/",
                        "Comment: \"" + soundContributionEvent.getComment() + "\"",
                        null,
                        null
                );
            }
            
            return "redirect:/content/sound/list#" + sound.getId();
        }
    }
}
