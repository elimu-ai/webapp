package ai.elimu.web.content.storybook;

import ai.elimu.dao.EmojiDao;
import ai.elimu.dao.ImageDao;
import ai.elimu.dao.LetterDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookContributionEventDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.dao.StoryBookPeerReviewEventDao;
import ai.elimu.dao.WordDao;
import ai.elimu.entity.content.Emoji;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.entity.content.Word;
import ai.elimu.entity.content.multimedia.Image;
import ai.elimu.entity.contributor.Contributor;
import ai.elimu.entity.contributor.StoryBookContributionEvent;
import ai.elimu.entity.enums.ContentLicense;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.model.v2.enums.ReadingLevel;
import ai.elimu.rest.v2.service.StoryBooksJsonService;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.DiscordHelper;
import ai.elimu.util.DiscordHelper.Channel;
import ai.elimu.util.DomainHelper;
import ai.elimu.util.LetterFrequencyHelper;
import ai.elimu.util.WordFrequencyHelper;
import ai.elimu.util.ml.ReadingLevelUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content/storybook/edit/{id}")
@RequiredArgsConstructor
@Slf4j
public class StoryBookEditController {

  private final StoryBookDao storyBookDao;

  private final StoryBookContributionEventDao storyBookContributionEventDao;

  private final StoryBookPeerReviewEventDao storyBookPeerReviewEventDao;

  private final StoryBookChapterDao storyBookChapterDao;

  private final StoryBookParagraphDao storyBookParagraphDao;

  private final ImageDao imageDao;

  private final WordDao wordDao;

  private final EmojiDao emojiDao;

  private final LetterDao letterDao;

  private final StoryBooksJsonService storyBooksJsonService;

  @GetMapping
  public String handleRequest(Model model, @PathVariable Long id) {
    log.info("handleRequest");

    StoryBook storyBook = storyBookDao.read(id);
    model.addAttribute("storyBook", storyBook);

    model.addAttribute("contentLicenses", ContentLicense.values());

    List<Image> coverImages = imageDao.readAllOrdered();
    model.addAttribute("coverImages", coverImages);

    model.addAttribute("readingLevels", ReadingLevel.values());

    List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
    model.addAttribute("storyBookChapters", storyBookChapters);

    // Map<StoryBookChapter.id, List<StoryBookParagraph>>
    Map<Long, List<StoryBookParagraph>> paragraphsPerStoryBookChapterMap = new HashMap<>();
    for (StoryBookChapter storyBookChapter : storyBookChapters) {
      paragraphsPerStoryBookChapterMap.put(storyBookChapter.getId(), storyBookParagraphDao.readAll(storyBookChapter));
    }
    model.addAttribute("paragraphsPerStoryBookChapterMap", paragraphsPerStoryBookChapterMap);

    List<String> paragraphs = new ArrayList<>();
    for (StoryBookChapter storyBookChapter : storyBookChapters) {
      List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
      for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
        paragraphs.add(storyBookParagraph.getOriginalText());
      }
    }

    int wordCount = 0;
    for (String paragraph : paragraphs) {
      wordCount += paragraph.split(" ").length;
    }
    ReadingLevel predictedReadingLevel = ReadingLevelUtil.predictReadingLevel(storyBookChapters.size(), paragraphs.size(), wordCount);
    model.addAttribute("predictedReadingLevel", predictedReadingLevel);

    model.addAttribute("storyBookContributionEvents", storyBookContributionEventDao.readAll(storyBook));
    model.addAttribute("storyBookPeerReviewEvents", storyBookPeerReviewEventDao.readAll(storyBook));

    Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
    Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, language);
    model.addAttribute("wordFrequencyMap", wordFrequencyMap);
    Map<String, Word> wordMap = new HashMap<>();
    for (Word word : wordDao.readAllOrdered()) {
      wordMap.put(word.getText(), word);
    }
    model.addAttribute("wordMap", wordMap);
    model.addAttribute("emojisByWordId", getEmojisByWordId());

    Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
    model.addAttribute("letterFrequencyMap", letterFrequencyMap);
    Map<String, Letter> letterMap = new HashMap<>();
    for (Letter letter : letterDao.readAllOrdered()) {
      letterMap.put(letter.getText(), letter);
    }
    model.addAttribute("letterMap", letterMap);

    return "content/storybook/edit";
  }

  @PostMapping
  public String handleSubmit(
      @Valid StoryBook storyBook,
      BindingResult result,
      Model model,
      HttpServletRequest request,
      HttpSession session) {
    log.info("handleSubmit");

    StoryBook existingStoryBook = storyBookDao.readByTitle(storyBook.getTitle());
    if ((existingStoryBook != null) && !existingStoryBook.getId().equals(storyBook.getId())) {
      result.rejectValue("title", "NonUnique");
    }

    if (result.hasErrors()) {
      model.addAttribute("storyBook", storyBook);

      model.addAttribute("contentLicenses", ContentLicense.values());

      List<Image> coverImages = imageDao.readAllOrdered();
      model.addAttribute("coverImages", coverImages);

      model.addAttribute("readingLevels", ReadingLevel.values());

      List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
      model.addAttribute("storyBookChapters", storyBookChapters);

      // Map<StoryBookChapter.id, List<StoryBookParagraph>>
      Map<Long, List<StoryBookParagraph>> paragraphsPerStoryBookChapterMap = new HashMap<>();
      for (StoryBookChapter storyBookChapter : storyBookChapters) {
        paragraphsPerStoryBookChapterMap.put(storyBookChapter.getId(), storyBookParagraphDao.readAll(storyBookChapter));
      }
      model.addAttribute("paragraphsPerStoryBookChapterMap", paragraphsPerStoryBookChapterMap);

      List<String> paragraphs = new ArrayList<>();
      for (StoryBookChapter storyBookChapter : storyBookChapters) {
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
          paragraphs.add(storyBookParagraph.getOriginalText());
        }
      }

      model.addAttribute("storyBookContributionEvents", storyBookContributionEventDao.readAll(storyBook));
      model.addAttribute("storyBookPeerReviewEvents", storyBookPeerReviewEventDao.readAll(storyBook));

      Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));
      Map<String, Integer> wordFrequencyMap = WordFrequencyHelper.getWordFrequency(paragraphs, language);
      model.addAttribute("wordFrequencyMap", wordFrequencyMap);
      Map<String, Word> wordMap = new HashMap<>();
      for (Word word : wordDao.readAllOrdered()) {
        wordMap.put(word.getText(), word);
      }
      model.addAttribute("wordMap", wordMap);
      model.addAttribute("emojisByWordId", getEmojisByWordId());

      Map<String, Integer> letterFrequencyMap = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
      model.addAttribute("letterFrequencyMap", letterFrequencyMap);
      Map<String, Letter> letterMap = new HashMap<>();
      for (Letter letter : letterDao.readAllOrdered()) {
        letterMap.put(letter.getText(), letter);
      }
      model.addAttribute("letterMap", letterMap);

      return "content/storybook/edit";
    } else {
      storyBook.setRevisionNumber(storyBook.getRevisionNumber() + 1);
      storyBookDao.update(storyBook);

      StoryBookContributionEvent storyBookContributionEvent = new StoryBookContributionEvent();
      storyBookContributionEvent.setContributor((Contributor) session.getAttribute("contributor"));
      storyBookContributionEvent.setTimestamp(Calendar.getInstance());
      storyBookContributionEvent.setStoryBook(storyBook);
      storyBookContributionEvent.setRevisionNumber(storyBook.getRevisionNumber());
      storyBookContributionEvent.setComment(StringUtils.abbreviate(request.getParameter("contributionComment"), 1000));
      storyBookContributionEventDao.create(storyBookContributionEvent);

      String contentUrl = DomainHelper.getBaseUrl() + "/content/storybook/edit/" + storyBook.getId();
      String embedThumbnailUrl = null;
      if (storyBook.getCoverImage() != null) {
        embedThumbnailUrl = storyBook.getCoverImage().getUrl();
      }
      DiscordHelper.postToChannel(
          Channel.CONTENT,
          "Storybook edited: " + contentUrl,
          "\"" + storyBookContributionEvent.getStoryBook().getTitle() + "\"",
          "Comment: \"" + storyBookContributionEvent.getComment() + "\"",
          null,
          embedThumbnailUrl
      );

      // Refresh REST API cache
      storyBooksJsonService.refreshStoryBooksJSONArray();

      return "redirect:/content/storybook/list#" + storyBook.getId();
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
