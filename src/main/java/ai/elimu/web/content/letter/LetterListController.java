package ai.elimu.web.content.letter;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.LetterFrequencyHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/letter/list")
@RequiredArgsConstructor
@Slf4j
public class LetterListController {

  private final LetterDao letterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Letter> letters = letterDao.readAllOrderedByUsage();
    model.addAttribute("letters", letters);

    int maxUsageCount = 0;
    for (Letter letter : letters) {
      if (letter.getUsageCount() > maxUsageCount) {
        maxUsageCount = letter.getUsageCount();
      }
    }
    model.addAttribute("maxUsageCount", maxUsageCount);

    // Extract letter frequency distribution from storybook paragraphs
    List<String> paragraphs = new ArrayList<>();
    for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll()) {
      paragraphs.add(storyBookParagraph.getOriginalText());
    }
    Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
    Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
    model.addAttribute("letterFrequencyMap", letterFrequencyMap);

    return "content/letter/list";
  }
}
