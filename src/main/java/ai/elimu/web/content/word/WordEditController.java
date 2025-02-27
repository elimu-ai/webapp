package ai.elimu.web.content.word;

import ai.elimu.dao.AudioDao;
import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterSoundDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.SyllableDao;
import ai.elimu.dao.WordContributionEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.dao.WordPeerReviewEventDao;
import ai.elimu.model.content.Emoji;
import ai.elimu.model.content.Letter;
import ai.elimu.model.content.LetterSound;
import ai.elimu.model.content.StoryBookParagraph;
import ai.elimu.model.content.Syllable;
import ai.elimu.model.content.Word;
import ai.elimu.model.content.multimedia.Audio;
import ai.elimu.model.content.multimedia.Image;
import ai.elimu.model.contributor.Contributor;
import ai.elimu.model.contributor.WordContributionEvent;
import ai.elimu.model.v2.enums.content.SpellingConsistency;
import ai.elimu.model.v2.enums.content.WordType;
import ai.elimu.util.DiscordHelper;
import ai.elimu.web.context.EnvironmentContextLoaderListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/content/word/edit/{id}")
@RequiredArgsConstructor
public class WordEditController {

  private final Logger logger = LogManager.getLogger();

  private final WordDao wordDao;

  private final LetterSoundDao letterSoundDao;

  private final AudioDao audioDao;

  private final EmojiDao emojiDao;

  private final ImageDao imageDao;

  private final SyllableDao syllableDao;

  private final WordContributionEventDao wordContributionEventDao;

  private final WordPeerReviewEventDao wordPeerReviewEventDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  @GetMapping
  public String handleRequest(
      HttpSession session,
      Model model,
      @PathVariable Long id
  ) {
    logger.info("handleRequest");

    Word word = wordDao.read(id);

    if (word.getLetterSounds().isEmpty()) {
      autoSelectLetterSounds(word);
      // TODO: display information message to the Contributor that the letter-sound correspondences were auto-selected, and that they should be verified
    }

    model.addAttribute("word", word);
    model.addAttribute("timeStart", System.currentTimeMillis());
    model.addAttribute("letterSounds", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
    model.addAttribute("rootWords", wordDao.readAllOrdered());
    model.addAttribute("emojisByWordId", getEmojisByWordId());
    model.addAttribute("wordTypes", WordType.values());
    model.addAttribute("spellingConsistencies", SpellingConsistency.values());

    model.addAttribute("wordContributionEvents", wordContributionEventDao.readAll(word));
    model.addAttribute("wordPeerReviewEvents", wordPeerReviewEventDao.readAll(word));

    List<Audio> audios = audioDao.readAll(word);
    model.addAttribute("audios", audios);

    // Look up variants of the same wordByTextMatch
    model.addAttribute("wordInflections", wordDao.readInflections(word));

    // Look up Multimedia content that has been labeled with this Word
    // TODO: labeled Audios
    List<Emoji> labeledEmojis = emojiDao.readAllLabeled(word);
    model.addAttribute("labeledEmojis", labeledEmojis);
    List<Image> labeledImages = imageDao.readAllLabeled(word);
    model.addAttribute("labeledImages", labeledImages);
    // TODO: labeled Videos

    // Look up StoryBook Paragraphs that contain this Word
    List<StoryBookParagraph> storyBookParagraphsContainingWord = storyBookParagraphDao.readAllContainingWord(word.getText());
    model.addAttribute("storyBookParagraphsContainingWord", storyBookParagraphsContainingWord);

    return "content/word/edit";
  }

  @PostMapping
  public String handleSubmit(
      HttpServletRequest request,
      HttpSession session,
      @Valid Word word,
      BindingResult result,
      Model model
  ) {
    logger.info("handleSubmit");

    Word existingWord = wordDao.readByTextAndType(word.getText(), word.getWordType());
    if ((existingWord != null) && !existingWord.getId().equals(word.getId())) {
      result.rejectValue("text", "NonUnique");
    }

    if (StringUtils.containsAny(word.getText(), " ")) {
      result.rejectValue("text", "WordSpace");
    }

    if (result.hasErrors()) {
      model.addAttribute("word", word);
      model.addAttribute("timeStart", request.getParameter("timeStart"));
      model.addAttribute("letterSounds", letterSoundDao.readAllOrderedByUsage()); // TODO: sort by letter(s) text
      model.addAttribute("rootWords", wordDao.readAllOrdered());
      model.addAttribute("emojisByWordId", getEmojisByWordId());
      model.addAttribute("wordTypes", WordType.values());
      model.addAttribute("spellingConsistencies", SpellingConsistency.values());

      model.addAttribute("wordContributionEvents", wordContributionEventDao.readAll(word));
      model.addAttribute("wordPeerReviewEvents", wordPeerReviewEventDao.readAll(word));

      model.addAttribute("audios", audioDao.readAll(word));

      // Look up variants of the same wordByTextMatch
      model.addAttribute("wordInflections", wordDao.readInflections(word));

      // Look up Multimedia content that has been labeled with this Word
      // TODO: labeled Audios
      List<Emoji> labeledEmojis = emojiDao.readAllLabeled(word);
      model.addAttribute("labeledEmojis", labeledEmojis);
      List<Image> labeledImages = imageDao.readAllLabeled(word);
      model.addAttribute("labeledImages", labeledImages);
      // TODO: labeled Videos

      return "content/word/edit";
    } else {
      word.setTimeLastUpdate(Calendar.getInstance());
      word.setRevisionNumber(word.getRevisionNumber() + 1);
      wordDao.update(word);

      WordContributionEvent wordContributionEvent = new WordContributionEvent();
      wordContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      wordContributionEvent.setTimestamp(Calendar.getInstance());
      wordContributionEvent.setWord(word);
      wordContributionEvent.setRevisionNumber(word.getRevisionNumber());
      wordContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      wordContributionEvent.setTimeSpentMs(System.currentTimeMillis() - Long.valueOf(request.getParameter("timeStart")));
      wordContributionEventDao.create(wordContributionEvent);

      if (!EnvironmentContextLoaderListener.PROPERTIES.isEmpty()) {
        String contentUrl = "https://" + EnvironmentContextLoaderListener.PROPERTIES.getProperty("content.language").toLowerCase() + ".elimu.ai/content/word/edit/" + word.getId();
        DiscordHelper.sendChannelMessage(
            "Word edited: " + contentUrl,
            "\"" + word.getText() + "\"",
            "Comment: \"" + wordContributionEvent.getComment() + "\"",
            null,
            null
        );
      }

      // Note: updating the list of Words in StoryBookParagraphs is handled by the ParagraphWordScheduler

      // Delete syllables that are actual words
      Syllable syllable = syllableDao.readByText(word.getText());
      if (syllable != null) {
        syllableDao.delete(syllable);
      }

      return "redirect:/content/word/list#" + word.getId();
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

  private void autoSelectLetterSounds(Word word) {
    logger.info("autoSelectLetterSounds");

    String wordText = word.getText();

    List<LetterSound> letterSounds = new ArrayList<>();

    List<LetterSound> allLetterSoundsOrderedByLettersLength = letterSoundDao.readAllOrderedByLettersLength();
    while (StringUtils.isNotBlank(wordText)) {
      logger.info("wordText: \"" + wordText + "\"");

      boolean isMatch = false;
      for (LetterSound letterSound : allLetterSoundsOrderedByLettersLength) {
        String letterSoundLetters = letterSound.getLetters().stream().map(Letter::getText).collect(Collectors.joining());
        logger.info("letterSoundLetters: \"" + letterSoundLetters + "\"");

        if (wordText.startsWith(letterSoundLetters)) {
          isMatch = true;
          logger.info("Found match at the beginning of \"" + wordText + "\"");
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
