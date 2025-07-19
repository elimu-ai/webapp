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
 * Iterates all StoryBooks and calculates the frequency of each letter. Lower-case and 
 * upper-case variants are considered as two different letters, e.g. 'a' and 'A'.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LetterUsageCountScheduler {

  private final LetterDao letterDao;

  private final StoryBookDao storyBookDao;
  private final StoryBookChapterDao storyBookChapterDao;
  private final StoryBookParagraphDao storyBookParagraphDao;

  @Scheduled(cron = "00 15 06 * * *") // At 06:15 every day
  public synchronized void execute() {
    log.info("execute");

    log.info("Calculating usage count for Letters");

    Map<String, Integer> letterFrequencyMap = new HashMap<>();

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
      letterFrequencyMapForBook.keySet().forEach(letterText -> letterFrequencyMap.put(letterText, letterFrequencyMap.getOrDefault(letterText, 0) + letterFrequencyMapForBook.get(letterText)));
    }

    log.info("letterFrequencyMap: " + letterFrequencyMap);

    for (String letterText : letterFrequencyMap.keySet()) {
      Letter existingLetter = letterDao.readByText(letterText);
      if (existingLetter != null) {
        existingLetter.setUsageCount(letterFrequencyMap.get(letterText));
        letterDao.update(existingLetter);
      }
    }

    log.info("execute complete");
  }
}
