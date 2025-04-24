package ai.elimu.web.content.word;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Word;

import java.util.Arrays;
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

  private final LetterSoundDao letterSoundDao;

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Word> words = wordDao.readAllOrderedByUsage();
    model.addAttribute("words", words);
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    List<LetterSound> letterSounds = letterSoundDao.readAllOrderedByUsage();
    if (!letterSounds.isEmpty()) {
      LetterSound mostUsedLetterSound = letterSounds.get(0);
      for (Word word : words) {
        log.info("word.getText(): " + word.getText());
        log.info("word.getLetterSounds(): " + word.getLetterSounds());
        if (word.getLetterSounds().isEmpty()) {
          // Store temporary letters
          word.setLetterSounds(Arrays.asList(mostUsedLetterSound, mostUsedLetterSound, mostUsedLetterSound, mostUsedLetterSound, mostUsedLetterSound, mostUsedLetterSound, mostUsedLetterSound, mostUsedLetterSound));
          wordDao.update(word);
          log.info("word updated: " + word.getId());
        }
      }
    }

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
