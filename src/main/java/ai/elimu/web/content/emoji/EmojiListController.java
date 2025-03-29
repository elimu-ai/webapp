package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiDao;
import ai.elimu.entity.content.Emoji;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/emoji/list")
@RequiredArgsConstructor
@Slf4j
public class EmojiListController {

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    List<Emoji> emojis = emojiDao.readAllOrdered();
    model.addAttribute("emojis", emojis);

    return "content/emoji/list";
  }
}
