package ai.elimu.web.content.letter_sound;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.content.LetterSoundCorrespondence;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.LetterSoundDao;

@Controller
@RequestMapping("/content/letter-sound/list")
public class LetterSoundListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterSoundDao letterSoundDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<LetterSoundCorrespondence> letterSounds = letterSoundDao.readAllOrderedByUsage();
        model.addAttribute("letterSounds", letterSounds);
        
        int maxUsageCount = 0;
        for (LetterSoundCorrespondence letterSound : letterSounds) {
            if (letterSound.getUsageCount() > maxUsageCount) {
                maxUsageCount = letterSound.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/letter-sound/list";
    }
}
