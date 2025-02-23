package ai.elimu.web.content.syllable;

import ai.elimu.dao.SyllableDao;
import ai.elimu.model.content.Syllable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/syllable/list")
@RequiredArgsConstructor
@Slf4j
public class SyllableListController {

  private final SyllableDao syllableDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Syllable> syllables = syllableDao.readAllOrderedByUsage();
    log.info("syllables.size(): " + syllables.size());
    model.addAttribute("syllables", syllables);

    return "content/syllable/list";
  }
}
