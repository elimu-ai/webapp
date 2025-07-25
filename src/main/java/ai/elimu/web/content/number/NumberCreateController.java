package ai.elimu.web.content.number;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Number;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.NumberContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/number/create")
@RequiredArgsConstructor
@Slf4j
public class NumberCreateController {

  private final NumberDao numberDao;

  private final NumberContributionEventDao numberContributionEventDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @GetMapping
  public String handleRequest(
      Model model) {
    log.info("handleRequest");

    Number number = new Number();
    model.addAttribute("number", number);

    model.addAttribute("words", wordDao.readAllOrdered());
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/number/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Number number,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    Number existingNumber = numberDao.readByValue(number.getValue());
    if (existingNumber != null) {
      result.rejectValue("value", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("number", number);

      model.addAttribute("words", wordDao.readAllOrdered());
      model.addAttribute("emojisByWordId", getEmojisByWordId());

      return "content/number/create";
    } else {
      numberDao.create(number);

      NumberContributionEvent numberContributionEvent = new NumberContributionEvent();
      numberContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      numberContributionEvent.setTimestamp(Calendar.getInstance());
      numberContributionEvent.setNumber(number);
      numberContributionEvent.setRevisionNumber(number.getRevisionNumber());
      numberContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      numberContributionEventDao.create(numberContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/number/edit/" + number.getId();
      DiscordHelper.postToChannel(
          Channel.CONTENT,
          "Number created: " + contentUrl,
          String.valueOf(numberContributionEvent.getNumber().getValue()),
          "Comment: \"" + numberContributionEvent.getComment() + "\"",
          null,
          null
      );

      return "redirect:/content/number/list#" + number.getId();
    }
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
