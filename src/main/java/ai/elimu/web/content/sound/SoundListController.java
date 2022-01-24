package ai.elimu.web.content.sound;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.model.content.Allophone;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ai.elimu.dao.SoundDao;

@Controller
@RequestMapping("/content/sound/list")
public class SoundListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private SoundDao soundDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<Allophone> sounds = soundDao.readAllOrderedByUsage();
        model.addAttribute("allophones", sounds);
        
        int maxUsageCount = 0;
        for (Allophone sound : sounds) {
            if (sound.getUsageCount() > maxUsageCount) {
                maxUsageCount = sound.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/sound/list";
    }
}
