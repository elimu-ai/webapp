package ai.elimu.web.analytics;

import ai.elimu.dao.LetterLearningEventDao;
import ai.elimu.entity.analytics.LetterLearningEvent;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/letter-learning-event/list")
public class LetterLearningEventListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private LetterLearningEventDao letterLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
    	logger.info("handleRequest");
        
        List<LetterLearningEvent> letterLearningEvents = letterLearningEventDao.readAll();
        model.addAttribute("letterLearningEvents", letterLearningEvents);

        return "analytics/letter-learning-event/list";
    }
}
