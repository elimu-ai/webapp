package ai.elimu.web.analytics;

import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.enums.OrderDirection;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.util.AnalyticsHelper;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/video-learning-event/list")
@RequiredArgsConstructor
@Slf4j
public class VideoLearningEventListController {

  private final VideoLearningEventDao videoLearningEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAllOrderedByTimestamp(OrderDirection.DESC);
    log.info("videoLearningEvents.size(): " + videoLearningEvents.size());
    for (VideoLearningEvent videoLearningEvent : videoLearningEvents) {
      videoLearningEvent.setAndroidId(AnalyticsHelper.redactAndroidId(videoLearningEvent.getAndroidId()));
    }

    model.addAttribute("videoLearningEventCount", videoLearningEvents.size());
    if (videoLearningEvents.size() <= 100) {
      model.addAttribute("videoLearningEvents", videoLearningEvents);
    } else {
      model.addAttribute("videoLearningEvents", videoLearningEvents.subList(0, 100));
    }

    // Prepare chart data
    List<String> weekList = new ArrayList<>();
    List<Integer> eventCountList = new ArrayList<>();
    if (!videoLearningEvents.isEmpty()) {
      // Group event count by week (e.g. "32-2024", "37-2024")
      Map<String, Integer> eventCountByMonthMap = new HashMap<>();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ww-yyyy");
      for (VideoLearningEvent event : videoLearningEvents) {
        String eventMonth = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByMonthMap.put(eventMonth, eventCountByMonthMap.getOrDefault(eventMonth, 0) + 1);
      }

      // Iterate each week from 6 months ago until now
      Calendar calendar6MonthsAgo = Calendar.getInstance();
      calendar6MonthsAgo.add(Calendar.MONTH, -6); // 26 weeks
      Calendar calendarNow = Calendar.getInstance();
      Calendar week = calendar6MonthsAgo;
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        weekList.add(weekAsString);

        eventCountList.add(eventCountByMonthMap.getOrDefault(weekAsString, 0));

        // Increase the date by 1 week
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("weekList", weekList);
    model.addAttribute("eventCountList", eventCountList);

    return "analytics/video-learning-event/list";
  }
}
