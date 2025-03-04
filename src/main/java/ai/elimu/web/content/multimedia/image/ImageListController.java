package ai.elimu.web.content.multimedia.image;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Image;
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
@RequestMapping("/content/multimedia/image/list")
@RequiredArgsConstructor
@Slf4j
public class ImageListController {

  private final ImageDao imageDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Image> images = imageDao.readAllOrdered();
    model.addAttribute("images", images);

    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/multimedia/image/list";
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
