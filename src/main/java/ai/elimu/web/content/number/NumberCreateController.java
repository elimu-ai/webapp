package ai.elimu.web.content.number;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.NumberContributionEventDao;
import ai.elimu.dao.NumberDao;
import ai.elimu.dao.WordDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Number;
import ai.elimu.model.content.Word;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.NumberContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/content/number/create")
@RequiredArgsConstructor
public class NumberCreateController {

  private final Logger logger = LogManager.getLogger();

  private final NumberDao numberDao;

  private final NumberContributionEventDao numberContributionEventDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  @RequestMapping(method = RequestMethod.GET)
  public String handleRequest(
      Model model) {
    logger.info("handleRequest");

    Number number = new Number();
    model.addAttribute("number", number);

    model.addAttribute("timeStart", System.currentTimeMillis());

    model.addAttribute("words", wordDao.readAllOrdered());
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/number/create";
  }

  @RequestMapping(method = RequestMethod.POST)
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Number number,
      BindingResult result,
      Model model) {
    logger.info("handleSubmit");

    Number existingNumber = numberDao.readByValue(number.getValue());
    if (existingNumber != null) {
      result.rejectValue("value", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("number", number);

      model.addAttribute("timeStart", request.getParameter("timeStart"));

      model.addAttribute("words", wordDao.readAllOrdered());
      model.addAttribute("emojisByWordId", getEmojisByWordId());

      return "content/number/create";
    } else {
      number.setTimeLastUpdate(Calendar.getInstance());
      numberDao.create(number);

      NumberContributionEvent numberContributionEvent = new NumberContributionEvent();
      numberContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      numberContributionEvent.setTimestamp(Calendar.getInstance());
      numberContributionEvent.setNumber(number);
      numberContributionEvent.setRevisionNumber(number.getRevisionNumber());
      numberContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      numberContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      numberContributionEventDao.create(numberContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/number/edit/" + number.getId();
        DiscordHelper.sendChannelMessage(
            "Number created: " + contentUrl,
            String.valueOf(numberContributionEvent.getNumber().getValue()),
            "Comment: \"" + numberContributionEvent.getComment() + "\"",
            null,
            null
        );
      }

      return "redirect:/content/number/list#" + number.getId();
    }
  }

  private Map<Long, String> getEmojisByWordId() {
    logger.info("getEmojisByWordId");

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
