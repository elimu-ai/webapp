package ai.elimu.web.analytics.students;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StudentDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.LetterSoundAssessmentEvent;
import ai.elimu.entity.analytics.LetterSoundLearningEvent;
import ai.elimu.entity.analytics.NumberLearningEvent;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.analytics.VideoLearningEvent;
import ai.elimu.entity.analytics.WordAssessmentEvent;
import ai.elimu.entity.analytics.WordLearningEvent;
import ai.elimu.entity.analytics.students.Student;
import ai.elimu.model.v2.enums.content.LiteracySkill;
import ai.elimu.model.v2.enums.content.NumeracySkill;
import ai.elimu.util.AnalyticsHelper;
import io.micrometer.common.util.StringUtils;
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

  private final WordAssessmentEventDao wordAssessmentEventDao;
  private final WordLearningEventDao wordLearningEventDao;

  private final NumberLearningEventDao numberLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;

  private final VideoLearningEventDao videoLearningEventDao;

  @GetMapping
  public String handleRequest(@PathVariable Long studentId, Model model) {
    log.info("handleRequest");

    Student student = studentDao.read(studentId);
    log.info("student.getAndroidId(): " + student.getAndroidId());


    model.addAttribute("literacySkills", LiteracySkill.values());
    model.addAttribute("numeracySkills", NumeracySkill.values());


    // Generate a list of weeks from 6 months ago until now
    List<String> weekList = new ArrayList<>();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-ww");
    Calendar calendar6MonthsAgo = Calendar.getInstance();
    calendar6MonthsAgo.add(Calendar.MONTH, -6);
    Calendar calendarNow = Calendar.getInstance();
    Calendar week = (Calendar) calendar6MonthsAgo.clone();
    while (!week.after(calendarNow)) {
      String weekAsString = simpleDateFormat.format(week.getTime());
      weekList.add(weekAsString);
      week.add(Calendar.WEEK_OF_YEAR, 1);
    }
    log.info("weekList: " + weekList);
    model.addAttribute("weekList", weekList);

    // Prepare chart data - LetterSoundAssessmentEvents
    List<LetterSoundAssessmentEvent> letterSoundAssessmentEvents = letterSoundAssessmentEventDao.readAll(student.getAndroidId());
    model.addAttribute("letterSoundAssessmentEvents", letterSoundAssessmentEvents);
    List<Integer> letterSoundAssessmentEventCountList = new ArrayList<>();
    if (!letterSoundAssessmentEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      for (LetterSoundAssessmentEvent event : letterSoundAssessmentEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        letterSoundAssessmentEventCountList.add(eventCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("letterSoundAssessmentEventCountList", letterSoundAssessmentEventCountList);

    // Prepare chart data - LetterSoundLearningEvents
    List<LetterSoundLearningEvent> letterSoundLearningEvents = letterSoundLearningEventDao.readAll(student.getAndroidId());
    model.addAttribute("letterSoundLearningEvents", letterSoundLearningEvents);
    List<Integer> letterSoundLearningEventCountList = new ArrayList<>();
    if (!letterSoundLearningEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      for (LetterSoundLearningEvent event : letterSoundLearningEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        letterSoundLearningEventCountList.add(eventCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("letterSoundLearningEventCountList", letterSoundLearningEventCountList);

    
    // Prepare chart data - WordAssessmentEvents
    List<WordAssessmentEvent> wordAssessmentEvents = wordAssessmentEventDao.readAll(student.getAndroidId());
    model.addAttribute("wordAssessmentEvents", wordAssessmentEvents);
    List<Integer> wordAssessmentEventCorrectCountList = new ArrayList<>();
    List<Integer> wordAssessmentEventIncorrectCountList = new ArrayList<>();
    if (!wordAssessmentEvents.isEmpty()) {
      Map<String, Integer> eventCorrectCountByWeekMap = new HashMap<>();
      Map<String, Integer> eventIncorrectCountByWeekMap = new HashMap<>();
      for (WordAssessmentEvent event : wordAssessmentEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        if (event.getMasteryScore() < 0.5) {
          eventIncorrectCountByWeekMap.put(eventWeek, eventIncorrectCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
        } else {
          eventCorrectCountByWeekMap.put(eventWeek, eventCorrectCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
        }
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        wordAssessmentEventCorrectCountList.add(eventCorrectCountByWeekMap.getOrDefault(weekAsString, 0));
        wordAssessmentEventIncorrectCountList.add(eventIncorrectCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("wordAssessmentEventCorrectCountList", wordAssessmentEventCorrectCountList);
    model.addAttribute("wordAssessmentEventIncorrectCountList", wordAssessmentEventIncorrectCountList);

    // Prepare chart data - Letter identification speed (correct letter-sounds per minute)
    List<Double> letterIdentificationSpeedAvgList = new ArrayList<>();
    if (!wordAssessmentEvents.isEmpty()) {
      Map<String, Integer> letterCountByWeekMap = new HashMap<>();
      Map<String, Long> timeSpentMsSumByWeekMap = new HashMap<>();
      for (WordAssessmentEvent event : wordAssessmentEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        if (event.getMasteryScore() >= 0.5) {
          int letterCount = StringUtils.isNotBlank(event.getWordText()) ? event.getWordText().length() : 0;
          if (letterCount > 0) {
            letterCountByWeekMap.put(eventWeek, letterCountByWeekMap.getOrDefault(eventWeek, 0) + letterCount);
            timeSpentMsSumByWeekMap.put(eventWeek, letterCountByWeekMap.getOrDefault(eventWeek, 0) + event.getTimeSpentMs());
          }
        }
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        Integer lettersIdentifiedCount = letterCountByWeekMap.getOrDefault(weekAsString, 0);
        log.info("lettersIdentifiedCount: " + lettersIdentifiedCount);
        Long timeSpentMsSum = timeSpentMsSumByWeekMap.getOrDefault(weekAsString, 0L);
        log.info("timeSpentMsSum: " + timeSpentMsSum);
        Double timeSpentInMinutes = (double) (timeSpentMsSum / 1_000);
        log.info("timeSpentInMinutes: " + timeSpentInMinutes);
        Double lettersPerMinute = 0.00;
        if (timeSpentInMinutes > 0) {
          lettersPerMinute = lettersIdentifiedCount / timeSpentInMinutes;
          log.info("lettersPerMinute: " + lettersPerMinute);
        }
        letterIdentificationSpeedAvgList.add(lettersPerMinute);
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("letterIdentificationSpeedAvgList", letterIdentificationSpeedAvgList);
    
    // Prepare chart data - Reading speed (correct words per minute)
    List<Double> readingSpeedAvgList = new ArrayList<>();
    if (!wordAssessmentEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      Map<String, Long> timeSpentMsSumByWeekMap = new HashMap<>();
      for (WordAssessmentEvent event : wordAssessmentEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        if (event.getMasteryScore() >= 0.5) {
          eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
          timeSpentMsSumByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + event.getTimeSpentMs());
        }
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        Integer wordsReadCount = eventCountByWeekMap.getOrDefault(weekAsString, 0);
        log.info("wordsReadCount: " + wordsReadCount);
        Long timeSpentMsSum = timeSpentMsSumByWeekMap.getOrDefault(weekAsString, 0L);
        log.info("timeSpentMsSum: " + timeSpentMsSum);
        Double timeSpentInMinutes = (double) (timeSpentMsSum / 1_000);
        log.info("timeSpentInMinutes: " + timeSpentInMinutes);
        Double wordsPerMinute = 0.00;
        if (timeSpentInMinutes > 0) {
          wordsPerMinute = wordsReadCount / timeSpentInMinutes;
          log.info("wordsPerMinute: " + wordsPerMinute);
        }
        readingSpeedAvgList.add(wordsPerMinute);
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("readingSpeedAvgList", readingSpeedAvgList);
    
    // Prepare chart data - WordLearningEvents
    List<WordLearningEvent> wordLearningEvents = wordLearningEventDao.readAll(student.getAndroidId());
    model.addAttribute("wordLearningEvents", wordLearningEvents);
    List<Integer> wordEventCountList = new ArrayList<>();
    if (!wordLearningEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      for (WordLearningEvent event : wordLearningEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        wordEventCountList.add(eventCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("wordEventCountList", wordEventCountList);


    // Prepare chart data - NumberLearningEvents
    List<NumberLearningEvent> numberLearningEvents = numberLearningEventDao.readAll(student.getAndroidId());
    model.addAttribute("numberLearningEvents", numberLearningEvents);
    List<Integer> numberEventCountList = new ArrayList<>();
    if (!numberLearningEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      for (NumberLearningEvent event : numberLearningEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        numberEventCountList.add(eventCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("numberEventCountList", numberEventCountList);


    // Prepare chart data - StoryBookLearningEvents
    List<StoryBookLearningEvent> storyBookLearningEvents = storyBookLearningEventDao.readAll(student.getAndroidId());
    model.addAttribute("storyBookLearningEvents", storyBookLearningEvents);
    List<Integer> storyBookEventCountList = new ArrayList<>();
    if (!storyBookLearningEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      for (StoryBookLearningEvent event : storyBookLearningEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        storyBookEventCountList.add(eventCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("storyBookEventCountList", storyBookEventCountList);


    // Prepare chart data - VideoLearningEvents
    List<VideoLearningEvent> videoLearningEvents = videoLearningEventDao.readAll(student.getAndroidId());
    model.addAttribute("videoLearningEvents", videoLearningEvents);
    List<Integer> videoEventCountList = new ArrayList<>();
    if (!videoLearningEvents.isEmpty()) {
      Map<String, Integer> eventCountByWeekMap = new HashMap<>();
      for (VideoLearningEvent event : videoLearningEvents) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());
        eventCountByWeekMap.put(eventWeek, eventCountByWeekMap.getOrDefault(eventWeek, 0) + 1);
      }
      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        videoEventCountList.add(eventCountByWeekMap.getOrDefault(weekAsString, 0));
        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("videoEventCountList", videoEventCountList);


    student.setAndroidId(AnalyticsHelper.redactAndroidId(student.getAndroidId()));
    model.addAttribute("student", student);

    return "analytics/students/id";
  }
}
