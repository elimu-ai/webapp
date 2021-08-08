package ai.elimu.web.content.letter_to_allophone_mapping;

import java.util.List;
import org.apache.logging.log4j.Logger;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter-to-allophone-mapping/list")
public class LetterToAllophoneMappingListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterToAllophoneMappingDao letterSoundCorrespondenceDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<LetterToAllophoneMapping> letterSoundCorrespondences = letterSoundCorrespondenceDao.readAllOrderedByUsage();
        model.addAttribute("letterSoundCorrespondences", letterSoundCorrespondences);
        
        int maxUsageCount = 0;
        for (LetterToAllophoneMapping letterSoundCorrespondence : letterSoundCorrespondences) {
            if (letterSoundCorrespondence.getUsageCount() > maxUsageCount) {
                maxUsageCount = letterSoundCorrespondence.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/letter-to-allophone-mapping/list";
    }
}
