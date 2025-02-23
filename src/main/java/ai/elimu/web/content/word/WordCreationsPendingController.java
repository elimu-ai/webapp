package ai.elimu.web.content.word;

import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordFrequencyHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/pending")
@RequiredArgsConstructor
@Slf4j
public class WordCreationsPendingController {

  private final WordDao wordDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<String> paragraphs = new ArrayList<>();
    for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll()) {
      paragraphs.add(storyBookParagraph.getOriginalText());
    }
    log.info("paragraphs.size(): " + paragraphs.size());

    Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
    Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, language);
    model.addAttribute("wordFrequencyMap", wordFrequencyMap);
    log.info("wordFrequencyMap.size(): " + wordFrequencyMap.size());

    // Remove Words that have already been added
    Iterator<String> wordTextIterator = wordFrequencyMap.keySet().iterator();
    while (wordTextIterator.hasNext()) {
      String wordText = wordTextIterator.next();
      Word existingWord = wordDao.readByText(wordText);
      if (existingWord != null) {
        wordTextIterator.remove();
      }
    }

    int maxUsageCount = 0;
    for (Integer usageCount : wordFrequencyMap.values()) {
      if (usageCount > maxUsageCount) {
        maxUsageCount = usageCount;
      }
    }
    model.addAttribute("maxUsageCount", maxUsageCount);

    return "content/word/pending";
  }
}
