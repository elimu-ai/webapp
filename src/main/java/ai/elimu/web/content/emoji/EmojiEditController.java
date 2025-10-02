package ai.elimu.web.content.emoji;

import ai.elimu.dao.EmojiContributionEventDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.EmojiContributionEvent;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/content/emoji/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class EmojiEditController {

  private final EmojiDao emojiDao;
  private final EmojiContributionEventDao emojiContributionEventDao;

  private final WordDao wordDao;

  @GetMapping
  public String handleRequest(
      Model model,
      @PathVariable Long id) {
    log.info("handleRequest");

    Emoji emoji = emojiDao.read(id);
    model.addAttribute("emoji", emoji);

    model.addAttribute("emojiContributionEvents", emojiContributionEventDao.readAll(emoji));

    List<Word> words = wordDao.readAllOrdered();
    model.addAttribute("words", words);
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    return "content/emoji/edit";
  }

  @PostMapping
  public String handleSubmit(
      HttpSession session,
      @Valid Emoji emoji,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    Emoji existingEmoji = emojiDao.readByGlyph(emoji.getGlyph());
    if ((existingEmoji != null) && !existingEmoji.getId().equals(emoji.getId())) {
      result.rejectValue("glyph", "NonUnique");
    }

    if (emoji.getUnicodeVersion() > 10) {
      result.rejectValue("glyph", "emoji.unicode.version");
    }

    if (result.hasErrors()) {
      model.addAttribute("emoji", emoji);

      model.addAttribute("emojiContributionEvents", emojiContributionEventDao.readAll(emoji));

      List<Word> words = wordDao.readAllOrdered();
      model.addAttribute("words", words);
      model.addAttribute("emojisByWordId", getEmojisByWordId());

      return "content/emoji/edit";
    } else {
      Emoji persistentEmoji = emojiDao.read(emoji.getId());
      persistentEmoji.setGlyph(emoji.getGlyph());
      persistentEmoji.setUnicodeVersion(emoji.getUnicodeVersion());
      persistentEmoji.setUnicodeEmojiVersion(emoji.getUnicodeEmojiVersion());
      persistentEmoji.setRevisionNumber(persistentEmoji.getRevisionNumber() + 1);

      emojiDao.update(persistentEmoji);

      EmojiContributionEvent emojiContributionEvent = new EmojiContributionEvent();
      emojiContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      emojiContributionEvent.setTimestamp(Calendar.getInstance());
      emojiContributionEvent.setEmoji(persistentEmoji);
      emojiContributionEvent.setRevisionNumber(persistentEmoji.getRevisionNumber());
      emojiContributionEventDao.create(emojiContributionEvent);

      DiscordHelper.postToChannel(Channel.CONTENT, "Emoji " + persistentEmoji.getGlyph() + " updated: " + DomainHelper.getBaseUrl() + "/content/emoji/edit/" + persistentEmoji.getId());

      return "redirect:/content/emoji/list#" + persistentEmoji.getId();
    }
  }

  @PostMapping(value = "/add-content-label")
  @ResponseBody
  public String handleAddContentLabelRequest(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpSession session,
      @PathVariable Long id) {
    log.info("handleAddContentLabelRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    if (contributor == null) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return "error";
    }

    log.info("id: " + id);
    Emoji emoji = emojiDao.read(id);

    String wordIdParameter = request.getParameter("wordId");
    log.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = emoji.getWords();
      if (!words.contains(word)) {
        words.add(word);
        emoji.setRevisionNumber(emoji.getRevisionNumber() + 1);
        emojiDao.update(emoji);

        EmojiContributionEvent emojiContributionEvent = new EmojiContributionEvent();
        emojiContributionEvent.setContributor(contributor);
        emojiContributionEvent.setTimestamp(Calendar.getInstance());
        emojiContributionEvent.setEmoji(emoji);
        emojiContributionEvent.setRevisionNumber(emoji.getRevisionNumber());
        emojiContributionEvent.setComment("Add word label: \"" + word.getText() + "\" (🤖 auto-generated comment)");
        emojiContributionEventDao.create(emojiContributionEvent);
      }
    }

    return "success";
  }

  @PostMapping(value = "/remove-content-label")
  @ResponseBody
  public String handleRemoveContentLabelRequest(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpSession session,
      @PathVariable Long id) {
    log.info("handleRemoveContentLabelRequest");

    Contributor contributor = (Contributor) session.getAttribute("contributor");
    if (contributor == null) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
      return "error";
    }

    log.info("id: " + id);
    Emoji emoji = emojiDao.read(id);

    String wordIdParameter = request.getParameter("wordId");
    log.info("wordIdParameter: " + wordIdParameter);
    if (StringUtils.isNotBlank(wordIdParameter)) {
      Long wordId = Long.valueOf(wordIdParameter);
      Word word = wordDao.read(wordId);
      Set<Word> words = emoji.getWords();
      Iterator<Word> iterator = words.iterator();
      while (iterator.hasNext()) {
        Word existingWord = iterator.next();
        if (existingWord.getId().equals(word.getId())) {
          iterator.remove();
        }
      }
      emoji.setRevisionNumber(emoji.getRevisionNumber() + 1);
      emojiDao.update(emoji);

      EmojiContributionEvent emojiContributionEvent = new EmojiContributionEvent();
      emojiContributionEvent.setContributor(contributor);
      emojiContributionEvent.setTimestamp(Calendar.getInstance());
      emojiContributionEvent.setEmoji(emoji);
      emojiContributionEvent.setRevisionNumber(emoji.getRevisionNumber());
      emojiContributionEvent.setComment("Remove word label: \"" + word.getText() + "\" (🤖 auto-generated comment)");
      emojiContributionEventDao.create(emojiContributionEvent);
    }

    return "success";
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
