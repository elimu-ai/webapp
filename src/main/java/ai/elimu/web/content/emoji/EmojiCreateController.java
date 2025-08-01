package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiContributionEventDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.EmojiContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/emoji/create")
@RequiredArgsConstructor
@Slf4j
public class EmojiCreateController {

  private final EmojiDao emojiDao;
  private final EmojiContributionEventDao emojiContributionEventDao;

  @GetMapping
  public String handleRequest(Model model) {
    log.info("handleRequest");

    Emoji emoji = new Emoji();
    model.addAttribute("emoji", emoji);

    return "content/emoji/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @Valid Emoji emoji,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    Emoji existingEmoji = emojiDao.readByGlyph(emoji.getGlyph());
    if (existingEmoji != null) {
      result.rejectValue("glyph", "NonUnique");
    }

    if (emoji.getUnicodeVersion() > 10) {
      result.rejectValue("glyph", "emoji.unicode.version");
    }

    if (result.hasErrors()) {
      model.addAttribute("emoji", emoji);

      return "content/emoji/create";
    } else {
      emojiDao.create(emoji);

      EmojiContributionEvent emojiContributionEvent = new EmojiContributionEvent();
      emojiContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      emojiContributionEvent.setTimestamp(Calendar.getInstance());
      emojiContributionEvent.setEmoji(emoji);
      emojiContributionEvent.setRevisionNumber(emoji.getRevisionNumber());
      emojiContributionEventDao.create(emojiContributionEvent);

      DiscordHelper.postToChannel(Channel.CONTENT, "Emoji " + emoji.getGlyph() + " created: " + DomainHelper.getBaseUrl() + "/content/emoji/edit/" + emoji.getId());

      return "redirect:/content/emoji/list#" + emoji.getId();
    }
  }
}
