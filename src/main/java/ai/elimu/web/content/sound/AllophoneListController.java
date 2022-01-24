package ai.elimu.web.content.sound;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.AllophoneDao;
import ai.elimu.model.content.Allophone;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/sound/list")
public class AllophoneListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private AllophoneDao allophoneDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<Allophone> allophones = allophoneDao.readAllOrderedByUsage();
        model.addAttribute("allophones", allophones);
        
        int maxUsageCount = 0;
        for (Allophone allophone : allophones) {
            if (allophone.getUsageCount() > maxUsageCount) {
                maxUsageCount = allophone.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/sound/list";
    }
}
