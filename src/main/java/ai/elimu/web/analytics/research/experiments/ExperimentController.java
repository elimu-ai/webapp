package ai.elimu.web.analytics.research.experiments;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.entity.analytics.StoryBookLearningEvent;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.json.JSONObject;
import org.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/research/experiments/{researchExperiment}")
@RequiredArgsConstructor
@Slf4j
public class ExperimentController {

  private final LetterSoundAssessmentEventDao letterSoundAssessmentEventDao;
  private final LetterSoundLearningEventDao letterSoundLearningEventDao;

  private final WordAssessmentEventDao wordAssessmentEventDao;
  private final WordLearningEventDao wordLearningEventDao;

  // private final NumberAssessmentEventDao numberAssessmentEventDao;
  private final NumberLearningEventDao numberLearningEventDao;

  private final StoryBookLearningEventDao storyBookLearningEventDao;
  
  private final VideoLearningEventDao videoLearningEventDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @GetMapping
  public String handleRequest(@PathVariable ResearchExperiment researchExperiment, Model model) {
    log.info("handleRequest");

    log.info("researchExperiment: " + researchExperiment);
    
    model.addAttribute("researchExperiment", researchExperiment);

    
    model.addAttribute("letterSoundAssessmentEvents_CONTROL", letterSoundAssessmentEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("letterSoundAssessmentEvents_TREATMENT", letterSoundAssessmentEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    model.addAttribute("letterSoundLearningEvents_CONTROL", letterSoundLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("letterSoundLearningEvents_TREATMENT", letterSoundLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    
    model.addAttribute("wordAssessmentEvents_CONTROL", wordAssessmentEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("wordAssessmentEvents_TREATMENT", wordAssessmentEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    model.addAttribute("wordLearningEvents_CONTROL", wordLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("wordLearningEvents_TREATMENT", wordLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    
    // model.addAttribute("numberAssessmentEvents_CONTROL", numberAssessmentEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    // model.addAttribute("numberAssessmentEvents_TREATMENT", numberAssessmentEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    model.addAttribute("numberLearningEvents_CONTROL", numberLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("numberLearningEvents_TREATMENT", numberLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    List<StoryBookLearningEvent> storyBookLearningEvents_CONTROL = storyBookLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL);
    model.addAttribute("storyBookLearningEvents_CONTROL", storyBookLearningEvents_CONTROL);
    List<StoryBookLearningEvent> storyBookLearningEvents_TREATMENT = storyBookLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT);
    model.addAttribute("storyBookLearningEvents_TREATMENT", storyBookLearningEvents_TREATMENT);
    
    
    model.addAttribute("videoLearningEvents_CONTROL", videoLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("videoLearningEvents_TREATMENT", videoLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    
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

    // Prepare chart data - Reading speed (correct words per minute)
    List<Double> readingSpeedAvgList_CONTROL = new ArrayList<>();
    if (!storyBookLearningEvents_CONTROL.isEmpty()) {
      Map<String, Integer> wordCountByWeekMap = new HashMap<>();
      Map<String, Integer> secondsSpentByWeekMap = new HashMap<>();
      for (StoryBookLearningEvent event : storyBookLearningEvents_CONTROL) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());

        // Get total number of words in the storybook
        List<StoryBookParagraph> storyBookParagraphsInBook = storyBookParagraphDao.readAll(event.getStoryBook());
        int wordCountInBook = 0;
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphsInBook) {
          wordCountInBook += storyBookParagraph.getOriginalText().split(" ").length;
        }
        wordCountByWeekMap.put(eventWeek, wordCountByWeekMap.getOrDefault(eventWeek, 0) + wordCountInBook);

        // Get the total number of seconds spent in the storybook
        JSONObject additionalData = new JSONObject(event.getAdditionalData());
        JSONArray secondsSpentPerChapter = new JSONArray(additionalData.getString("seconds_spent_per_chapter"));
        int secondsSpentInBook = IntStream.range(0, secondsSpentPerChapter.length()).map(i -> secondsSpentPerChapter.getInt(i)).sum();
        secondsSpentByWeekMap.put(eventWeek, secondsSpentByWeekMap.getOrDefault(eventWeek, 0) + secondsSpentInBook);
      }

      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        log.info("weekAsString: " + weekAsString);
        
        Integer wordCount = wordCountByWeekMap.getOrDefault(weekAsString, 0);
        log.info("wordCount: " + wordCount);
        
        Integer secondsSpent = secondsSpentByWeekMap.getOrDefault(weekAsString, 0);
        log.info("secondsSpent: " + secondsSpent);

        Double timeSpentInMinutes = secondsSpent / 60D;
        log.info("timeSpentInMinutes: " + timeSpentInMinutes);

        Double wordsPerMinute = 0.00;
        if (timeSpentInMinutes > 0) {
          wordsPerMinute = wordCount / timeSpentInMinutes;
          log.info("wordsPerMinute: " + wordsPerMinute);
        }
        readingSpeedAvgList_CONTROL.add(wordsPerMinute);

        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("readingSpeedAvgList_CONTROL", readingSpeedAvgList_CONTROL);

    List<Double> readingSpeedAvgList_TREATMENT = new ArrayList<>();
    if (!storyBookLearningEvents_TREATMENT.isEmpty()) {
      Map<String, Integer> wordCountByWeekMap = new HashMap<>();
      Map<String, Integer> secondsSpentByWeekMap = new HashMap<>();
      for (StoryBookLearningEvent event : storyBookLearningEvents_TREATMENT) {
        String eventWeek = simpleDateFormat.format(event.getTimestamp().getTime());

        // Get total number of words in the storybook
        List<StoryBookParagraph> storyBookParagraphsInBook = storyBookParagraphDao.readAll(event.getStoryBook());
        int wordCountInBook = 0;
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphsInBook) {
          wordCountInBook += storyBookParagraph.getOriginalText().split(" ").length;
        }
        wordCountByWeekMap.put(eventWeek, wordCountByWeekMap.getOrDefault(eventWeek, 0) + wordCountInBook);

        // Get the total number of seconds spent in the storybook
        JSONObject additionalData = new JSONObject(event.getAdditionalData());
        JSONArray secondsSpentPerChapter = new JSONArray(additionalData.getString("seconds_spent_per_chapter"));
        int secondsSpentInBook = IntStream.range(0, secondsSpentPerChapter.length()).map(i -> secondsSpentPerChapter.getInt(i)).sum();
        secondsSpentByWeekMap.put(eventWeek, secondsSpentByWeekMap.getOrDefault(eventWeek, 0) + secondsSpentInBook);
      }

      week = (Calendar) calendar6MonthsAgo.clone();
      while (!week.after(calendarNow)) {
        String weekAsString = simpleDateFormat.format(week.getTime());
        log.info("weekAsString: " + weekAsString);
        
        Integer wordCount = wordCountByWeekMap.getOrDefault(weekAsString, 0);
        log.info("wordCount: " + wordCount);
        
        Integer secondsSpent = secondsSpentByWeekMap.getOrDefault(weekAsString, 0);
        log.info("secondsSpent: " + secondsSpent);

        Double timeSpentInMinutes = secondsSpent / 60D;
        log.info("timeSpentInMinutes: " + timeSpentInMinutes);

        Double wordsPerMinute = 0.00;
        if (timeSpentInMinutes > 0) {
          wordsPerMinute = wordCount / timeSpentInMinutes;
          log.info("wordsPerMinute: " + wordsPerMinute);
        }
        readingSpeedAvgList_TREATMENT.add(wordsPerMinute);

        week.add(Calendar.WEEK_OF_YEAR, 1);
      }
    }
    model.addAttribute("readingSpeedAvgList_TREATMENT", readingSpeedAvgList_TREATMENT);

    return "analytics/research/experiments/id";
  }
}
