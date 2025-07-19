package ai.elimu.web.content.word;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.Word;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.WordFrequencyHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/word/list")
@RequiredArgsConstructor
@Slf4j
public class WordListController {

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Word> words = wordDao.readAllOrderedByUsage();
    model.addAttribute("words", words);
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    int maxUsageCount = 0;
    for (Word word : words) {
      if (word.getUsageCount() > maxUsageCount) {
        maxUsageCount = word.getUsageCount();
      }
    }
    model.addAttribute("maxUsageCount", maxUsageCount);

    // Extract letter frequency distribution from storybook paragraphs
    List<String> wordsInParagraphs = new ArrayList<>();
    for (StoryBookParagraph storyBookParagraph : storyBookParagraphDao.readAll()) {
      for (String word : storyBookParagraph.getOriginalText().split(" ")) {
        wordsInParagraphs.add(word);
      }
    }
    if (StringUtils.isNotBlank(ConfigHelper.getProperty("content.language"))) {
      Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
      Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(wordsInParagraphs, language);
      model.addAttribute("wordFrequencyMap", wordFrequencyMap);
    }

    return "content/word/list";
  }

  private Map<Long, String> getEmojisByWordId() {
    log.info("getEmojisByWordId");

    Map<Long, String> emojisByWordId = new HashMap<>();

    for (Word word : wordDao.readAll()) {
      String emojiGlyphs = "";

      List<Emoji> emojis = emojiDao.readAllLabeled(word);
      for (Emoji emoji : emojis) {
        emojiGlyphs += emoji.getGlyph();
      }

      if (StringUtils.isNotBlank(emojiGlyphs)) {
        emojisByWordId.put(word.getId(), emojiGlyphs);
      }
    }

    return emojisByWordId;
  }
}
