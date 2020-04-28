package ai.elimu.web.content.letter_to_allophone_mapping;

import java.util.List;
import org.apache.log4j.Logger;
import ai.elimu.dao.LetterToAllophoneMappingDao;
import ai.elimu.model.content.LetterToAllophoneMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/letter-to-allophone-mapping/list")
public class LetterToAllophoneMappingListController {
    
    private final Logger logger = Logger.getLogger(getClass());
    
    @Autowired
    private LetterToAllophoneMappingDao letterToAllophoneMappingDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<LetterToAllophoneMapping> letterToAllophoneMappings = letterToAllophoneMappingDao.readAll();
        model.addAttribute("letterToAllophoneMappings", letterToAllophoneMappings);
        
        int maxUsageCount = 0;
        for (LetterToAllophoneMapping letterToAllophoneMapping : letterToAllophoneMappings) {
            if (letterToAllophoneMapping.getUsageCount() > maxUsageCount) {
                maxUsageCount = letterToAllophoneMapping.getUsageCount();
            }
        }
        model.addAttribute("maxUsageCount", maxUsageCount);

        return "content/letter-to-allophone-mapping/list";
    }
}
