package ai.elimu.web.analytics.research.experiments;

import ai.elimu.model.v2.enums.analytics.research.ResearchExperiment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/analytics/research/experiments")
@RequiredArgsConstructor
@Slf4j
public class ExperimentListController {

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");
    
    model.addAttribute("researchExperiments", ResearchExperiment.values());

    return "analytics/research/experiments/list";
  }
}
