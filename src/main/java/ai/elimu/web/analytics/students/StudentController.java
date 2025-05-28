package ai.elimu.web.analytics.students;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.util.AnalyticsHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/students/{studentId}")
@RequiredArgsConstructor
@Slf4j
public class StudentController {

  private final StudentDao studentDao;

  private final LetterSoundAssessmentEventDao letterSoundAssessmentEventDao;
  private final LetterSoundLearningEventDao letterSoundLearningEventDao;

  private final WordLearningEventDao wordLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  private final VideoLearningEventDao videoLearningEventDao;

  @GetMapping
  public String handleRequest(@PathVariable Long studentId, Model model) {
    log.info("handleRequest");

    Student student = studentDao.read(studentId);
    log.info("student.getAndroidId(): " + student.getAndroidId());


    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());


    List<LetterSoundAssessmentEvent> letterSoundAssessmentEvents = letterSoundAssessmentEventDao.readAll();
    model.addAttribute("letterSoundAssessmentEvents", letterSoundAssessmentEvents);

    List<LetterSoundLearningEvent> letterSoundLearningEvents = letterSoundLearningEventDao.readAll();
    model.addAttribute("letterSoundLearningEvents", letterSoundLearningEvents);

    
    // Prepare chart data - WordLearningEvents
    List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.readAll();
    List<String> wordMonthList = new ArrayList<>();
    List<Integer> wordEventCountList = new ArrayList<>();
    if (!wordLearningEvents.isEmpty()) {
      // Group event count by month (e.g. "Aug-2024", "Sep-2024")
      Map<String, Integer> eventCountByMonthMap = new HashMap<>();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-yyyy");
      for (WordLearningEvent event : wordLearningEvents) {
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
        wordMonthList.add(monthAsString);

        wordEventCountList.add(eventCountByMonthMap.getOrDefault(monthAsString, 0));

        // Increase the date by 1 month
        month.add(Calendar.MONTH, 1);
      }
    }
    model.addAttribute("wordMonthList", wordMonthList);
    model.addAttribute("wordEventCountList", wordEventCountList);
    model.addAttribute("wordLearningEvents", wordLearningEvents);


    // Prepare chart data - StoryBookLearningEvents
    List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAll();
    List<String> storyBookMonthList = new ArrayList<>();
    List<Integer> storyBookEventCountList = new ArrayList<>();
    if (!storyBookLearningEvents.isEmpty()) {
      // Group event count by month (e.g. "Aug-2024", "Sep-2024")
      Map<String, Integer> eventCountByMonthMap = new HashMap<>();
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM-yyyy");
      for (StoryBookLearningEvent event : storyBookLearningEvents) {
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
        storyBookMonthList.add(monthAsString);

        storyBookEventCountList.add(eventCountByMonthMap.getOrDefault(monthAsString, 0));

        // Increase the date by 1 month
        month.add(Calendar.MONTH, 1);
      }
    }
    model.addAttribute("storyBookMonthList", storyBookMonthList);
    model.addAttribute("storyBookEventCountList", storyBookEventCountList);
    model.addAttribute("storyBookLearningEvents", storyBookLearningEvents);


    // Prepare chart data - VideoLearningEvents
    List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAll();
    List<String> videoMonthList = new ArrayList<>();
    List<Integer> videoEventCountList = new ArrayList<>();
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
        videoMonthList.add(monthAsString);

        videoEventCountList.add(eventCountByMonthMap.getOrDefault(monthAsString, 0));

        // Increase the date by 1 month
        month.add(Calendar.MONTH, 1);
      }
    }
    model.addAttribute("videoMonthList", videoMonthList);
    model.addAttribute("videoEventCountList", videoEventCountList);
    model.addAttribute("videoLearningEvents", videoLearningEvents);


    student.setAndroidId(AnalyticsHelper.redactAndroidId(student.getAndroidId()));
    model.addAttribute("student", student);

    return "analytics/students/id";
  }
}
