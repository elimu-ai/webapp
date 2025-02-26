package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiDao;
import ai.elimu.model.content.Emoji;
import jakarta.validation.Valid;
import java.util.Calendar;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/content/emoji/create")
@RequiredArgsConstructor
public class EmojiCreateController {

  private final Logger logger = LogManager.getLogger();

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(Model model) {
    logger.info("handleRequest");

    Emoji emoji = new Emoji();
    model.addAttribute("emoji", emoji);

    return "content/emoji/create";
  }

  @PostMapping
  public String handleSubmit(
      @Valid Emoji emoji,
      BindingResult result,
      Model model) {
    logger.info("handleSubmit");

    Emoji existingEmoji = emojiDao.readByGlyph(emoji.getGlyph());
    if (existingEmoji != null) {
      result.rejectValue("glyph", "NonUnique");
    }

    if (emoji.getUnicodeVersion() > 9) {
      result.rejectValue("glyph", "emoji.unicode.version");
    }

    if (result.hasErrors()) {
      model.addAttribute("emoji", emoji);

      return "content/emoji/create";
    } else {
      emoji.setTimeLastUpdate(Calendar.getInstance());
      emojiDao.create(emoji);

      return "redirect:/content/emoji/list#" + emoji.getId();
    }
  }
}
