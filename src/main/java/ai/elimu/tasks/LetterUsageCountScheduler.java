package ai.elimu.tasks;

import ai.elimu.dao.LetterDao;
import ai.elimu.dao.StoryBookChapterDao;
import ai.elimu.dao.StoryBookDao;
import ai.elimu.dao.StoryBookParagraphDao;
import ai.elimu.entity.content.Letter;
import ai.elimu.entity.content.StoryBook;
import ai.elimu.entity.content.StoryBookChapter;
import ai.elimu.entity.content.StoryBookParagraph;
import ai.elimu.model.v2.enums.Language;
import ai.elimu.util.ConfigHelper;
import ai.elimu.util.LetterFrequencyHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Iterates all letter-sounds and calculates the frequency of each letter.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LetterUsageCountScheduler {

  private final LetterDao letterDao;

  private final StoryBookDao storyBookDao;
  private final StoryBookChapterDao storyBookChapterDao;
  private final StoryBookParagraphDao storyBookParagraphDao;

  @Scheduled(cron = "00 20 06 * * *") // At 06:20 every day
  public synchronized void execute() {
    log.info("execute");

    // <ID, frequency>
    Map<Long, Integer> frequencyMap = new HashMap<>();

    Language language = Language.valueOf(ConfigHelper.getProperty("content.language"));

    List<StoryBook> storyBooks = storyBookDao.readAllOrdered();
    log.info("storyBooks.size(): " + storyBooks.size());
    for (StoryBook storyBook : storyBooks) {
      log.debug("storyBook.getTitle(): " + storyBook.getTitle());

      List<String> paragraphs = new ArrayList<>();
      List<StoryBookChapter> storyBookChapters = storyBookChapterDao.readAll(storyBook);
      for (StoryBookChapter storyBookChapter : storyBookChapters) {
        List<StoryBookParagraph> storyBookParagraphs = storyBookParagraphDao.readAll(storyBookChapter);
        for (StoryBookParagraph storyBookParagraph : storyBookParagraphs) {
          paragraphs.add(storyBookParagraph.getOriginalText());
        }
      }

      Map<String, Integer> letterFrequencyMapForBook = LetterFrequencyHelper.getLetterFrequency(paragraphs, language);
      letterFrequencyMapForBook.keySet().forEach(letterText -> frequencyMap.put(letterText, frequencyMap.getOrDefault(letterText, 0) + letterFrequencyMapForBook.get(letterText)));
    }

    log.info("letterFrequencyMap: " + frequencyMap);

    for (String letterText : frequencyMap.keySet()) {
      Letter existingLetter = letterDao.readByText(letterText);
      if (existingLetter != null) {
        existingLetter.setUsageCount(frequencyMap.get(letterText));
        letterDao.update(existingLetter);
      }
    }

    log.info("execute complete");
  }
}
