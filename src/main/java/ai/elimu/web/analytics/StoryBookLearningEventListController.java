package ai.elimu.web.analytics;

import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.model.analytics.StoryBookLearningEvent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/storybook-learning-event/list")
public class StoryBookLearningEventListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private StoryBookLearningEventDao storyBookLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAllOrderedByTime();
        model.addAttribute("storyBookLearningEvents", storyBookLearningEvents);
        
        // Prepare data for chart in UI
        List<String> monthList = new ArrayList<>();
        List<Integer> eventCountList = new ArrayList<>();
        if (!storyBookLearningEvents.isEmpty()) {
            Calendar calendar4YearsAgo = Calendar.getInstance();
            calendar4YearsAgo.add(Calendar.YEAR, -4);

            Calendar calendarNow = Calendar.getInstance();
            
            Calendar month = calendar4YearsAgo;
            while (!month.after(calendarNow)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-yyyy");
                String monthAsString = simpleDateFormat.format(month.getTime());
                monthList.add(monthAsString);

                int eventCount = 0;
                for (StoryBookLearningEvent storyBookLearningEvent : storyBookLearningEvents) {
                    String eventMonthAsString = simpleDateFormat.format(storyBookLearningEvent.getTimestamp().getTime());
                    if (eventMonthAsString.equals(monthAsString)) {
                        eventCount++;
                    }
                }
                eventCountList.add(eventCount);

                month.add(Calendar.MONTH, 1);
            }
        }
        model.addAttribute("monthList", monthList);
        model.addAttribute("eventCountList", eventCountList);

        return "analytics/storybook-learning-event/list";
    }
}
