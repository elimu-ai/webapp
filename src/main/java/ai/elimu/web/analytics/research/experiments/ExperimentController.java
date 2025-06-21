package ai.elimu.web.analytics.research.experiments;

import ai.elimu.dao.LetterSoundAssessmentEventDao;
import ai.elimu.dao.LetterSoundLearningEventDao;
import ai.elimu.dao.NumberLearningEventDao;
import ai.elimu.dao.StoryBookLearningEventDao;
import ai.elimu.dao.VideoLearningEventDao;
import ai.elimu.dao.WordAssessmentEventDao;
import ai.elimu.dao.WordLearningEventDao;
import ai.elimu.model.v2.enums.analytics.research.ExperimentGroup;
import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    model.addAttribute("storyBookLearningEvents_CONTROL", storyBookLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("storyBookLearningEvents_TREATMENT", storyBookLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));
    
    model.addAttribute("videoLearningEvents_CONTROL", videoLearningEventDao.readAll(researchExperiment, ExperimentGroup.CONTROL));
    model.addAttribute("videoLearningEvents_TREATMENT", videoLearningEventDao.readAll(researchExperiment, ExperimentGroup.TREATMENT));

    return "analytics/research/experiments/id";
  }
}
