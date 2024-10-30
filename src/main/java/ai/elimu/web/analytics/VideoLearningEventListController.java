package ai.elimu.web.analytics;

import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.model.analytics.VideoLearningEvent;
import ai.elimu.util.AnalyticsHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/analytics/video-learning-event/list")
public class VideoLearningEventListController {
    
    private final Logger logger = LogManager.getLogger();
    
    @Autowired
    private VideoLearningEventDao videoLearningEventDao;

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest(Model model) {
        logger.info("handleRequest");
        
        List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAll();
        for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
            videoLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(videoLearningEvent.getAndroidId()));
        }
        model.addAttribute("videoLearningEvents", videoLearningEvents);

        // Prepare chart data
        List<String> monthList = new ArrayList<>();
        List<Integer> eventCountList = new ArrayList<>();
        if (!videoLearningEvents.isEmpty()) {
            // Group event count by month (e.g. "Aug-2024", "Sep-2024")
            Map<String, Integer> eventCountByMonthMap = new HashMap<>();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-yyyy");
            for (VideoLearningEvent event : videoLearningEvents) {
                String eventMonth = simpleDateFormat.format(event.getTimestamp().getTime());
                eventCountByMonthMap.put(eventMonth, eventCountByMonthMap.getOrDefault(eventMonth, 0) + 1);
            }
            
            // Iterate each month from 4 years ago until now
            Calendar calendar4YearsAgo = Calendar.getInstance();
            calendar4YearsAgo.add(Calendar.YEAR, -4);
            Calendar calendarNow = Calendar.getInstance();
            Calendar month = calendar4YearsAgo;
            while (!month.after(calendarNow)) {
                String monthAsString = simpleDateFormat.format(month.getTime());
                monthList.add(monthAsString);
                
                eventCountList.add(eventCountByMonthMap.getOrDefault(monthAsString, 0));

                // Increase the date by 1 month
                month.add(Calendar.MONTH, 1);
            }
        }
        model.addAttribute("monthList", monthList);
        model.addAttribute("eventCountList", eventCountList);

        return "analytics/video-learning-event/list";
    }
}
