package ai.elimu.web.content.word;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/word/list")
@RequiredArgsConstructor
@Slf4j
public class WordListController {

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @RequestMapping(method = RequestMethod.GET)
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
