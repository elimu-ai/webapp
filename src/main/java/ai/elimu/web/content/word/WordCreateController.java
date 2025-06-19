package ai.elimu.web.content.word;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.LetterSound;
import ai.elimu.entity.content.Syllable;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.WordContributionEvent;
import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DomainHelper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/content/word/create")
@RequiredArgsConstructor
@Slf4j
public class WordCreateController {

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  private final LetterSoundDao letterSoundDao;

  private final ImageDao imageDao;

  private final SyllableDao syllableDao;

  private final WordContributionEventDao wordContributionEventDao;

  @GetMapping
  public String handleRequest(Model model, @RequestParam(required = false) String autoFillText) {
    log.info("handleRequest");

    Word word = new Word();

    // Pre-fill the Word's text (if the user arrived from /content/storybook/edit/{id}/)
    if (StringUtils.isNotBlank(autoFillText)) {
      word.setText(autoFillText);

      autoSelectLetterSounds(word);
      // TODO: display information message to the Contributor that the letter-sound correspondences were auto-selected, and that they should be verified
    }

    model.addAttribute("word", word);
    model.addAttribute("letterSounds", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
    model.addAttribute("rootWords", wordDao.readAllOrdered());
    model.addAttribute("emojisByWordId", getEmojisByWordId());
    model.addAttribute("wordTypes", WordType.values());
    model.addAttribute("spellingConsistencies", SpellingConsistency.values());

    return "content/word/create";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Word word,
      BindingResult result,
      Model model) {
    log.info("handleSubmit");

    Word existingWord = wordDao.readByTextAndType(word.getText(), word.getWordType());
    if (existingWord != null) {
      result.rejectValue("text", "NonUnique");
    }

    if (StringUtils.containsAny(word.getText(), " ")) {
      result.rejectValue("text", "WordSpace");
    }

    if (result.hasErrors()) {
      model.addAttribute("word", word);
      model.addAttribute("letterSounds", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
      model.addAttribute("rootWords", wordDao.readAllOrdered());
      model.addAttribute("emojisByWordId", getEmojisByWordId());
      model.addAttribute("wordTypes", WordType.values());
      model.addAttribute("spellingConsistencies", SpellingConsistency.values());

      return "content/word/create";
    } else {
      wordDao.create(word);

      WordContributionEvent wordContributionEvent = new WordContributionEvent();
      wordContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      wordContributionEvent.setTimestamp(Calendar.getInstance());
      wordContributionEvent.setWord(word);
      wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
      wordContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      wordContributionEventDao.create(wordContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/word/edit/" + word.getId();
      DiscordHelper.sendChannelMessage(
          "Word created: " + contentUrl,
          "\"" + wordContributionEvent.getWord().getText() + "\"",
          "Comment: \"" + wordContributionEvent.getComment() + "\"",
          null,
          null
      );

      // Note: updating the list of Words in StoryBookParagraphs is handled by the ParagraphWordScheduler

      // Label Image with Word of matching title
      Image matchingImage = imageDao.read(word.getText());
      if (matchingImage != null) {
        Set<Word> labeledWords = matchingImage.getWords();
        if (!labeledWords.contains(word)) {
          labeledWords.add(word);
          matchingImage.setWords(labeledWords);
          imageDao.update(matchingImage);
        }
      }

      // Delete syllables that are actual words
      Syllable syllable = syllableDao.readByText(word.getText());
      if (syllable != null) {
        syllableDao.delete(syllable);
      }

      return "redirect:/content/word/list#" + word.getId();
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

  private void autoSelectLetterSounds(Word word) {
    log.info("autoSelectLetterSounds");

    String wordText = word.getText();

    List<LetterSound> letterSounds = new ArrayList<>();

    List<LetterSound> allLetterSoundsOrderedByLettersLength = letterSoundDao.readAllOrderedByLettersLength();
    while (StringUtils.isNotBlank(wordText)) {
      log.info("wordText: \"" + wordText + "\"");

      boolean isMatch = false;
      for (LetterSound letterSound : allLetterSoundsOrderedByLettersLength) {
        String letterSoundLetters = letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
        log.info("letterSoundLetters: \"" + letterSoundLetters + "\"");

        if (wordText.startsWith(letterSoundLetters)) {
          isMatch = true;
          log.info("Found match at the beginning of \"" + wordText + "\"");
          letterSounds.add(letterSound);

          // Remove the match from the word
          wordText = wordText.substring(letterSoundLetters.length());

          break;
        }
      }
      if (!isMatch) {
        // Skip auto-selection for the subsequent letters
        break;
      }
    }

    word.setLetterSounds(letterSounds);
  }
}
